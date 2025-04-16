package com.lhmd.rechnerarchitektur.instructions;

public class Return extends Instruction {
    public Return(int instruction) {
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
