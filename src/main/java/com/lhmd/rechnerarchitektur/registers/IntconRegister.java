package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import org.springframework.context.event.EventListener;
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

    @EventListener
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.getResetType()) {
            case POWERON, WATCHDOG -> "0000000x";
            case WAKEUP -> "---uuuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }
}
