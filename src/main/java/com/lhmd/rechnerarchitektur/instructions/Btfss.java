package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * If bit ’b’ in register ’f’ is ’0’ then the next instruction is executed.
 * If bit ’b’ in register ’f’ is ’1’, then the next instruction is discarded,
 * and a NOP is executed instead, making this a 2 cycle instruction.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Btfss extends InstructionBase {
    private final DataMemory dataMemory;
    private final ProgramCounter programCounter;

    private int address;
    private int bit;
    private boolean isTwoCycle;

    public Btfss(DataMemory dataMemory, ProgramCounter programCounter) {
        this.dataMemory = dataMemory;
        this.programCounter = programCounter;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);

        isTwoCycle = IntUtils.isBitSet(register.get(), bit);

        if (isTwoCycle) {
            programCounter.increment();
        }
    }

    @Override
    public boolean isTwoCycle() {
        return isTwoCycle;
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        bit = IntUtils.bitRange(getInstruction(), 7, 9);
    }
}
