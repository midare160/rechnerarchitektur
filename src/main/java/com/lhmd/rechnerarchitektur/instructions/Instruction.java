package com.lhmd.rechnerarchitektur.instructions;

public interface Instruction {
    Integer getAddress();
    void setAddress(Integer address);

    Integer getInstruction();
    void setInstruction(Integer instruction);

    int getLineNumber();
    void setLineNumber(int lineNumber);

    String getComment();
    void setComment(String comment);
}
