package com.lhmd.rechnerarchitektur.registers;

import org.springframework.stereotype.Component;

@Component
public class PortBRegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x06;
    }
}
