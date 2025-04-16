package com.lhmd.rechnerarchitektur.instructions;

public class Retlw extends Instruction {
    public Retlw(int instruction) {
        super(instruction);
    }

    @Override
    public void execute(ExecutionParams params) {

    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }
}
