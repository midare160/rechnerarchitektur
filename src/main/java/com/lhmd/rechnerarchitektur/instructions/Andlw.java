package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.computing.Alu;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of W register are AND’ed with the eight bit literal 'k'. The result is placed in the W register.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Andlw extends InstructionBase {
    private final Alu alu;
    private final WRegister wRegister;

    private int literal;

    public Andlw(Alu alu, WRegister wRegister) {
        this.alu = alu;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var result = alu.and(wRegister.get(), literal);
        wRegister.set(result);
    }

    @Override
    protected void onInitialized() {
        literal = IntUtils.bitRange(getInstruction(), 0, 7);
    }
}
