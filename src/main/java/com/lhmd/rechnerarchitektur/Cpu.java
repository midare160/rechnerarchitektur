package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.events.ActionEvent;
import com.lhmd.rechnerarchitektur.instructions.ExecutionParams;
import com.lhmd.rechnerarchitektur.memory.*;

import java.util.HashSet;
import java.util.Set;

public class Cpu extends Thread {
    private final ProgramMemory programMemory;
    private final DataMemory dataMemory;
    private final ProgramStack programStack;
    private final ExecutionParams executionParams;
    private final Set<Integer> breakpointAddresses;

    private final ActionEvent onBreakpointReached;
    private final ActionEvent onNextInstruction;

    private int lastBreakpointAddress;

    private volatile boolean isRunning;
    private volatile boolean isPaused;

    public Cpu(ProgramMemory programMemory, DataMemory dataMemory, ProgramStack programStack) {
        this.programMemory = programMemory;
        this.dataMemory = dataMemory;
        this.programStack = programStack;
        this.executionParams = new ExecutionParams(dataMemory, programStack);
        this.breakpointAddresses = new HashSet<>();

        this.onBreakpointReached = new ActionEvent();
        this.onNextInstruction = new ActionEvent();

        this.lastBreakpointAddress = -1;
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
            Runner.unchecked(() -> Thread.sleep(200));
            pauseOnBreakpoint();

            synchronized (this) {
                while (isPaused && isRunning) {
                    Runner.unchecked(() -> wait());
                }
            }

            nextInstruction();
        }
    }

    public synchronized void setPaused(boolean value) {
        isPaused = value;

        if (!isPaused) {
            notify();
        }
    }

    public synchronized void shutdown() {
        isRunning = false;
        notify();
    }

    public void nextInstruction() {
        onNextInstruction.fire();

        var address = dataMemory.programCounter().get();
        dataMemory.programCounter().increment();

        var currentInstruction = programMemory.get(address);
        currentInstruction.execute(executionParams);

        if (currentInstruction.isTwoCycle()) {
            // TODO add additional 4 cycles
        }
    }

    public void reset() {
        lastBreakpointAddress = -1;

        dataMemory.reset();
        programStack.reset();
    }

    private void pauseOnBreakpoint() {
        var currentAddress = dataMemory.programCounter().get();

        if (currentAddress == lastBreakpointAddress || !breakpointAddresses.contains(currentAddress)) {
            return;
        }

        isPaused = true;
        lastBreakpointAddress = currentAddress;
        onBreakpointReached.fire();
    }
}
