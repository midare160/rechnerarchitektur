package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PclathRegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x0A;
    }

    @Override
    public boolean isMirrored() {
        return true;
    }

    @EventListener
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.getResetType()) {
            case POWERON, WATCHDOG -> "---00000";
            case WAKEUP -> "---uuuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }
}
