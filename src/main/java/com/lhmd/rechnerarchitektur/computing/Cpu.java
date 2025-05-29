package com.lhmd.rechnerarchitektur.computing;

import com.lhmd.rechnerarchitektur.*;
import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.memory.*;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import com.lhmd.rechnerarchitektur.time.RuntimeManager;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Cpu extends Thread implements AutoCloseable {
    private final UserConfig userConfig;
    private final RuntimeManager runtimeManager;
    private final InterruptManager interruptManager;
    private final SleepManager sleepManager;
    private final ProgramCounter programCounter;
    private final Set<Integer> breakpointAddresses;

    private final ActionEvent onBreakpointReached;
    private final ActionEvent onBeforeInstruction;
    private final ActionEvent onAfterInstruction;

    private volatile ProgramMemory programMemory;

    private volatile boolean isRunning;
    private volatile boolean isPaused;

    public Cpu(UserConfigService userConfigService, RuntimeManager runtimeManager, InterruptManager interruptManager, SleepManager sleepManager, ProgramCounter programCounter) {
        this.userConfig = userConfigService.config();
        this.runtimeManager = runtimeManager;
        this.interruptManager = interruptManager;
        this.sleepManager = sleepManager;
        this.programCounter = programCounter;
        this.breakpointAddresses = new HashSet<>();

        this.onBreakpointReached = new ActionEvent();
        this.onBeforeInstruction = new ActionEvent();
        this.onAfterInstruction = new ActionEvent();

        this.isRunning = true;
        this.isPaused = true;
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

    public void setProgramMemory(ProgramMemory programMemory) {
        this.programMemory = programMemory;
    }

    public ActionEvent onBreakpointReached() {
        return onBreakpointReached;
    }

    public ActionEvent onBeforeInstruction() {
        return onBeforeInstruction;
    }

    public ActionEvent onAfterInstruction() {
        return onAfterInstruction;
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

    public synchronized void setPaused(boolean value) {
        isPaused = value;

        if (!isPaused) {
            notify();
        }

        sleepManager.wakeup();
    }

    public void nextInstruction() {
        if (!isRunning) return;

        onBeforeInstruction.fire();

        var address = programCounter.get();
        var currentInstruction = programMemory.get(address);

        programCounter.increment();

        currentInstruction.execute();
        runtimeManager.addCycle();

        if (currentInstruction.isTwoCycle()) {
            runtimeManager.addCycle();
        }

        interruptManager.handleInterrupt();

        onAfterInstruction.fire();
    }

    private void pauseOnBreakpoint() {
        var currentAddress = Math.floorMod(programCounter.get(), ProgramMemory.MAX_SIZE);

        if (!breakpointAddresses.contains(currentAddress)) {
            return;
        }

        isPaused = true;
        onBreakpointReached.fire();
    }
}
