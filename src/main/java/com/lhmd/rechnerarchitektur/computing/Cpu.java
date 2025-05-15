package com.lhmd.rechnerarchitektur.computing;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.events.ActionEvent;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import com.lhmd.rechnerarchitektur.memory.*;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Cpu extends Thread implements AutoCloseable {
    private final ProgramCounter programCounter;
    private final Set<Integer> breakpointAddresses;

    private final ActionEvent onBreakpointReached;
    private final ActionEvent onNextInstruction;

    private volatile ProgramMemory programMemory;
    private volatile int lastBreakpointAddress;

    private volatile boolean isRunning;
    private volatile boolean isPaused;

    public Cpu(ProgramCounter programCounter) {
        this.programCounter = programCounter;
        this.breakpointAddresses = new HashSet<>();

        this.onBreakpointReached = new ActionEvent();
        this.onNextInstruction = new ActionEvent();

        this.lastBreakpointAddress = -1;
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

    @Override
    public void start() {
        isRunning = true;
        super.start();
    }

    // Implementation as described in https://docs.oracle.com/javase/7/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html
    @Override
    public void run() {
        while (isRunning) {
            // TODO calculate speed from MHz
            Runner.unchecked(() -> sleep(200));
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

        if (currentInstruction.isTwoCycle()) {
            // TODO add additional 4 cycles
        }
    }

    private void pauseOnBreakpoint() {
        var currentAddress = programCounter.get();

        if (currentAddress == lastBreakpointAddress || !breakpointAddresses.contains(currentAddress)) {
            return;
        }

        isPaused = true;
        lastBreakpointAddress = currentAddress;
        onBreakpointReached.fire();
    }
}
