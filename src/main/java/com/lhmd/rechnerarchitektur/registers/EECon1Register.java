package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.*;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class EECon1Register extends SpecialRegister {
    private static final int RD_INDEX = 0;
    private static final int WR_INDEX = 1;
    private static final int WREN_INDEX = 2;
    private static final int WRERR_INDEX = 3;
    private static final int EEIF_INDEX = 4;

    @Override
    public int getAddress() {
        return 0x88;
    }

    @EventListener
    @Order(EventOrders.DATA)
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.resetType()) {
            case POWERON -> "---0x000";
            case WATCHDOG -> "---0%c000".formatted(getWR() ? '1' : '0');
            case WAKEUP_WATCHDOG, WAKEUP_INTERRUPT -> "---0uuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }

    public boolean getRD() {
        return isBitSet(RD_INDEX);
    }

    public void setRD(boolean value) {
        setBit(RD_INDEX, value);
    }

    public boolean getWR() {
        return isBitSet(WR_INDEX);
    }

    public void setWR(boolean value) {
        setBit(WR_INDEX, value);
    }

    public boolean getWREN() {
        return isBitSet(WREN_INDEX);
    }

    public void setWREN(boolean value) {
        setBit(WREN_INDEX, value);
    }

    public boolean getWRERR() {
        return isBitSet(WRERR_INDEX);
    }

    public void setWRERR(boolean value) {
        setBit(WRERR_INDEX, value);
    }

    public boolean getEEIF() {
        return isBitSet(EEIF_INDEX);
    }

    public void setEEIF(boolean value) {
        setBit(EEIF_INDEX, value);
    }
}
