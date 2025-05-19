package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EECon2Register extends SpecialRegister {
    private static final int RD_INDEX = 0;
    private static final int WR_INDEX = 1;
    private static final int WREN_INDEX = 2;
    private static final int WRERR_INDEX = 3;
    private static final int EEIF_INDEX = 4;

    @Override
    public int getAddress() {
        return 0x89;
    }

    @EventListener
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.getResetType()) {
            case POWERON -> "---0x000";
            case WATCHDOG -> "---0%c000".formatted(getWR() ? '1' : '0');
            case WAKEUP -> "---0uuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }

    public boolean getWR() {
        return isBitSet(WR_INDEX);
    }
}
