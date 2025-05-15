package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The eight bit literal ’k’ is loaded into W register.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Movlw extends InstructionBase {
    private final WRegister wRegister;

    private int literal;

    public Movlw(WRegister wRegister) {
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        wRegister.set(literal);
    }

    @Override
    protected void onInitialized() {
        literal = IntUtils.bitRange(getInstruction(), 0, 7);
    }
}
