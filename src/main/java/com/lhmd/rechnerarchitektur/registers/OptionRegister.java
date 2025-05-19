package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OptionRegister extends SpecialRegister {
    private static final int PSA_INDEX = 3;
    private static final int T0SE_INDEX = 4;
    private static final int T0CS_INDEX = 5;
    private static final int INTEDG_INDEX = 6;
    private static final int RBPU_INDEX = 7;

    public int getPs02() {
        return IntUtils.bitRange(get(), 0, 2);
    }

    @Override
    public int getAddress() {
        return 0x81;
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
