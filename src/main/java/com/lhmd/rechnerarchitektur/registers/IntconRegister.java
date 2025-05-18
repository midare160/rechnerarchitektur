package com.lhmd.rechnerarchitektur.registers;

import org.springframework.stereotype.Component;

@Component
public class IntconRegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x0B;
    }

    @Override
    public boolean isMirrored() {
        return true;
    }
}
