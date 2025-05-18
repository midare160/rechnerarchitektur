package com.lhmd.rechnerarchitektur.registers;

import org.springframework.stereotype.Component;

@Component
public class FsrRegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x04;
    }

    @Override
    public boolean isMirrored() {
        return true;
    }
}
