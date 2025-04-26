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

        this.lastBreakpointAddress = -1;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean value) {
        isPaused = value;
    }

    @Override
    public void start() {
        isRunning = true;
        super.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            while (!isPaused) {
                // TODO calculate speed from MHz
                Runner.runUnchecked(() -> Thread.sleep(200));

                if (pauseOnBreakpoint()) {
                    break;
                }

                nextInstruction();
            }

            Runner.runUnchecked(() -> Thread.sleep(100));
        }
    }

    public void nextInstruction() {
        var address = dataMemory.programCounter().get();
        dataMemory.programCounter().increment();

        var currentInstruction = programMemory.get(address);
        currentInstruction.execute(executionParams);

        if (currentInstruction.isTwoCycle()) {
            // TODO add additional 4 cycles
        }
    }

    public void addBreakpoint(int address) {
        breakpointAddresses.add(address);
    }

    public void removeBreakpoint(int address) {
        breakpointAddresses.remove(address);
    }

    public ActionEvent onBreakpointReached() {
        return onBreakpointReached;
    }

    public void shutdown() {
        isPaused = true;
        isRunning = false;
    }

    public void reset() {
        lastBreakpointAddress = -1;

        dataMemory.reset();
        programStack.reset();
    }

    private boolean pauseOnBreakpoint() {
        var currentAddress = dataMemory.programCounter().get();

        if (currentAddress == lastBreakpointAddress || !breakpointAddresses.contains(currentAddress)) {
            return false;
        }

        isPaused = true;
        lastBreakpointAddress = currentAddress;
        onBreakpointReached.fire();

        return true;
    }
}
