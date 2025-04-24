package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.instructions.ExecutionParams;
import com.lhmd.rechnerarchitektur.memory.*;

public class CpuThread extends Thread {
    private final ProgramMemory programMemory;
    private final DataMemory dataMemory;
    private final ExecutionParams executionParams;

    private boolean isPaused;

    public CpuThread(ProgramMemory programMemory, DataMemory dataMemory, ProgramStack programStack) {
        this.programMemory = programMemory;
        this.dataMemory = dataMemory;
        this.executionParams = new ExecutionParams(dataMemory, programStack);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            while (!isPaused) {
                // TODO remove
                Runner.runUnchecked(() -> Thread.sleep(500));

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

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean value) {
        isPaused = value;
    }
}
