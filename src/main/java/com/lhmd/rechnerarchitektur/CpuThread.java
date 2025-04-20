package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.instructions.ExecutionParams;
import com.lhmd.rechnerarchitektur.memory.*;

public class CpuThread extends Thread {
    private final ProgramMemory programMemory;
    private final DataMemory dataMemory;
    private final ExecutionParams executionParams;

    private boolean isRunning;
    private boolean isBreaking;

    public CpuThread(ProgramMemory programMemory, DataMemory dataMemory, ProgramStack programStack) {
        this.programMemory = programMemory;
        this.dataMemory = dataMemory;
        this.executionParams = new ExecutionParams(dataMemory, programStack);
    }

    @Override
    public void run() {
        while (isRunning && !isInterrupted()) {
            while (!isBreaking && !isInterrupted()) {
                nextInstruction();

                // TODO remove
                Runner.unchecked(() -> Thread.sleep(500));
            }

            Runner.unchecked(() -> Thread.sleep(100));
        }
    }

    public void nextInstruction() {
        var address = dataMemory.programCounter().get();
        dataMemory.programCounter().increment();

        var currentInstruction = programMemory.get(address);
        currentInstruction.execute(executionParams);

        if (currentInstruction.isTwoCycle()) {
            dataMemory.programCounter().increment();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean value) {
        isRunning = value;
    }

    public boolean isBreaking() {
        return isBreaking;
    }

    public void setBreaking(boolean value) {
        isBreaking = value;
    }
}
