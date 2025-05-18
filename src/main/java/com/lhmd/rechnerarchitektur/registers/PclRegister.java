package com.lhmd.rechnerarchitektur.registers;

import org.springframework.stereotype.Component;

@Component
public class PclRegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x02;
    }

    @Override
    public boolean isMirrored() {
        return true;
    }
}
