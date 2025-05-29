package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.execution.Alu;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of the W register are added to the eight bit literal ’k’ and the result is placed in the W register.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Addlw extends InstructionBase {
    private final Alu alu;
    private final WRegister wRegister;

    private int literal;

    public Addlw(Alu alu, WRegister wRegister) {
        this.alu = alu;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var result = alu.add(wRegister.get(), literal);
        wRegister.set(result);
    }

    @Override
    protected void onInitialized() {
        literal = IntUtils.bitRange(getInstruction(), 0, 7);
    }
}
