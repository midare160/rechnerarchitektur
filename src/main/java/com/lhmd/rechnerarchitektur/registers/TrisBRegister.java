package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.*;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class TrisBRegister extends SpecialRegister {
    @Override
    public int getAddress() {
        return 0x86;
    }

    @EventListener
    @Order(EventOrders.DATA)
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.resetType()) {
            case POWERON, WATCHDOG -> "11111111";
            case WAKEUP_WATCHDOG, WAKEUP_INTERRUPT -> "uuuuuuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }
}
