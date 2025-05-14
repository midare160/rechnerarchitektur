package com.lhmd.rechnerarchitektur.instructions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * No operation.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Nop extends InstructionBase {
    public static final Nop DEFAULT = new Nop();

    @Override
    public void execute() {
        // Does nothing
    }
}
