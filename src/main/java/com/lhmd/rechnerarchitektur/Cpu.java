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
            cpuThread.start();
        }

        cpuThread.setPaused(false);
    }

    public void pause() {
        cpuThread.setPaused(true);
    }

    public void next() {
        if (!cpuThread.isPaused()) {
            throw new IllegalStateException("CPU thread must be breaking to call next()");
        }

        cpuThread.nextInstruction();
    }

    public void reset() {
        dataMemory.reset();
        programStack.reset();
    }
}
