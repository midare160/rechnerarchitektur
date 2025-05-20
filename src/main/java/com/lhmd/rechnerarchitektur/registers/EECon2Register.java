package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EECon2Register extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x89;
    }
}
