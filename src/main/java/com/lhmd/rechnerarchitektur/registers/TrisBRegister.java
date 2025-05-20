package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TrisBRegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x86;
    }

    @EventListener
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.getResetType()) {
            case POWERON, WATCHDOG -> "11111111";
            case WAKEUP -> "uuuuuuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }
}
