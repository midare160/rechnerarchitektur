package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.*;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class IntconRegister extends SpecialRegister {
    private static final int RBIF_INDEX = 0;
    private static final int INTF_INDEX = 1;
    private static final int T0IF_INDEX = 2;
    private static final int RBIE_INDEX = 3;
    private static final int INTE_INDEX = 4;
    private static final int T0IE_INDEX = 5;
    private static final int EEIE_INDEX = 6;
    private static final int GIE_INDEX = 7;

    @Override
    public int getAddress() {
        return 0x0B;
    }

    @Override
    public boolean isMirrored() {
        return true;
    }

    @EventListener
    @Order(EventOrders.DATA)
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.getResetType()) {
            case POWERON, WATCHDOG -> "0000000x";
            case WAKEUP -> "---uuuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }

    public boolean getRBIF() {
        return isBitSet(RBIF_INDEX);
    }

    public void setRBIF(boolean value) {
        setBit(RBIF_INDEX, value);
    }

    public boolean getINTF() {
        return isBitSet(INTF_INDEX);
    }

    public void setINTF(boolean value) {
        setBit(INTF_INDEX, value);
    }

    public boolean getT0IF() {
        return isBitSet(T0IF_INDEX);
    }

    public void setT0IF(boolean value) {
        setBit(T0IF_INDEX, value);
    }

    public boolean getRBIE() {
        return isBitSet(RBIE_INDEX);
    }

    public void setRBIE(boolean value) {
        setBit(RBIE_INDEX, value);
    }

    public boolean getINTE() {
        return isBitSet(INTE_INDEX);
    }

    public void setINTE(boolean value) {
        setBit(INTE_INDEX, value);
    }

    public boolean getT0IE() {
        return isBitSet(T0IE_INDEX);
    }

    public void setT0IE(boolean value) {
        setBit(T0IE_INDEX, value);
    }

    public boolean getEEIE() {
        return isBitSet(EEIE_INDEX);
    }

    public void setEEIE(boolean value) {
        setBit(EEIE_INDEX, value);
    }

    public boolean getGIE() {
        return isBitSet(GIE_INDEX);
    }

    public void setGIE(boolean value) {
        setBit(GIE_INDEX, value);
    }
}
