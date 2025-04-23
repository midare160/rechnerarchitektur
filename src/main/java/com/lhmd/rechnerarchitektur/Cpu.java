package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.memory.*;

public class Cpu {
    private final ProgramMemory programMemory;
    private final DataMemory dataMemory;
    private final ProgramStack programStack;
    private final CpuThread cpuThread;

    public Cpu(ProgramMemory programMemory, DataMemory dataMemory, ProgramStack programStack) {
        this.programMemory = programMemory;
        this.programStack = programStack;
        this.dataMemory = dataMemory;
        this.cpuThread = new CpuThread(programMemory, dataMemory, programStack);
    }

    public void run() {
        if (!cpuThread.isAlive()) {
            cpuThread.setRunning(true);
            cpuThread.start();
        }

        cpuThread.setBreaking(false);
    }

    // TODO make it possible to start in break mode
    public void pause() {
        cpuThread.setBreaking(true);
    }

    public void next() {
        if (!cpuThread.isBreaking()) {
            throw new IllegalStateException("CPU thread must be breaking to call next()");
        }

        cpuThread.nextInstruction();
    }

    public void stop() {
        cpuThread.setRunning(false);
    }

    public void reset() {
        programMemory.reset();
        programStack.reset();
        dataMemory.reset();
    }
}
