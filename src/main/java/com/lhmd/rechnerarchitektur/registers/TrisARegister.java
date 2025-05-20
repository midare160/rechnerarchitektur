package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TrisARegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x85;
    }

    @EventListener
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.getResetType()) {
            case POWERON, WATCHDOG -> "---11111";
            case WAKEUP -> "---uuuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }
}
