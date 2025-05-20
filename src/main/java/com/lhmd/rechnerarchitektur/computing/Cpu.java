package com.lhmd.rechnerarchitektur.computing;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.memory.*;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Cpu extends Thread implements AutoCloseable {
    private final UserConfig userConfig;
    private final RuntimeManager runtimeManager;
    private final ProgramCounter programCounter;
    private final Set<Integer> breakpointAddresses;

    private final ActionEvent onBreakpointReached;
    private final ActionEvent onNextInstruction;

    private volatile ProgramMemory programMemory;
    private volatile int lastBreakpointAddress;

    private volatile boolean isRunning;
    private volatile boolean isPaused;

    public Cpu(UserConfigService userConfigService, RuntimeManager runtimeManager, ProgramCounter programCounter) {
        this.userConfig = userConfigService.config();
        this.runtimeManager = runtimeManager;
        this.programCounter = programCounter;
        this.breakpointAddresses = new HashSet<>();

        this.onBreakpointReached = new ActionEvent();
        this.onNextInstruction = new ActionEvent();

        this.lastBreakpointAddress = -1;
        this.isRunning = true;
        this.isPaused = true;
    }

    public void setProgramMemory(ProgramMemory programMemory) {
        this.programMemory = programMemory;
    }

    public ActionEvent onBreakpointReached() {
        return onBreakpointReached;
    }

    public ActionEvent onNextInstruction() {
        return onNextInstruction;
    }

    public void addBreakpoint(int address) {
        breakpointAddresses.add(address);
    }

    public void removeBreakpoint(int address) {
        breakpointAddresses.remove(address);
    }

    public void clearBreakpoints() {
        breakpointAddresses.clear();
    }

    // Implementation as described in https://docs.oracle.com/javase/7/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html
    @Override
    public void run() {
        while (isRunning) {
            Runner.unchecked(() -> sleep(userConfig.getExecutionInterval()));
            pauseOnBreakpoint();

            synchronized (this) {
                while (isPaused && isRunning) {
                    Runner.unchecked(() -> wait());
                }
            }

            nextInstruction();
        }
    }

    @Override
    public synchronized void close() {
        isRunning = false;
        notify();
    }

    @EventListener(ResetEvent.class)
    public void handleReset() {
        lastBreakpointAddress = -1;
    }

    public synchronized void setPaused(boolean value) {
        isPaused = value;

        if (!isPaused) {
            notify();
        }
    }

    public void nextInstruction() {
        if (!isRunning) return;

        onNextInstruction.fire();

        var address = programCounter.get();
        programCounter.increment();

        var currentInstruction = programMemory.get(address);
        currentInstruction.execute();
        runtimeManager.addCycle();

        if (currentInstruction.isTwoCycle()) {
            runtimeManager.addCycle();
        }
    }

    private void pauseOnBreakpoint() {
        var currentAddress = Math.floorMod(programCounter.get(), ProgramMemory.MAX_SIZE);

        if (currentAddress == lastBreakpointAddress || !breakpointAddresses.contains(currentAddress)) {
            return;
        }

        isPaused = true;
        lastBreakpointAddress = currentAddress;
        onBreakpointReached.fire();
    }
}
