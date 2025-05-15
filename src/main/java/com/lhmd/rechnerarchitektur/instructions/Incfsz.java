package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of register ’f’ are incremented.
 * If ’d’ is 0 the result is placed in the W register.
 * If ’d’ is 1 the result is placed back in register ’f’.
 * If the result is not 0, the next instruction is executed.
 * If the result is 0, then a NOP is executed instead making it a 2TCY instruction.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Incfsz extends InstructionBase {
    private final DataMemory dataMemory;
    private final ProgramCounter programCounter;
    private final WRegister wRegister;

    private int address;
    private boolean destination;
    private boolean isTwoCycle;

    public Incfsz(DataMemory dataMemory, ProgramCounter programCounter, WRegister wRegister) {
        this.dataMemory = dataMemory;
        this.programCounter = programCounter;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        var target = destination ? register : wRegister;

        var result = Math.floorMod(register.get() + 1, DataMemory.REGISTER_SIZE);
        target.set(result);

        isTwoCycle = result == 0;

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
        destination = IntUtils.isBitSet(getInstruction(), 7);
    }
}
