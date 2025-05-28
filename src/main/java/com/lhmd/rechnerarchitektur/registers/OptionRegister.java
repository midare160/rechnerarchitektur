package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.*;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class OptionRegister extends SpecialRegister {
    private static final int PSA_INDEX = 3;
    private static final int T0SE_INDEX = 4;
    private static final int T0CS_INDEX = 5;
    private static final int INTEDG_INDEX = 6;
    private static final int RBPU_INDEX = 7;

    @Override
    public int getAddress() {
        return 0x81;
    }

    @EventListener
    @Order(EventOrders.DATA)
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.resetType()) {
            case POWERON, WATCHDOG -> "11111111";
            case WAKEUP -> "uuuuuuuu";
        };

        set(IntUtils.changeBits(get(), pattern));
    }

    public int getPS02() {
        return IntUtils.bitRange(get(), 0, 2);
    }

    public boolean getPSA() {
        return isBitSet(PSA_INDEX);
    }

    public void setPSA(boolean value) {
        setBit(PSA_INDEX, value);
    }

    public boolean getT0SE() {
        return isBitSet(T0SE_INDEX);
    }

    public void setT0SE(boolean value) {
        setBit(T0SE_INDEX, value);
    }

    public boolean getT0CS() {
        return isBitSet(T0CS_INDEX);
    }

    public void setT0CS(boolean value) {
        setBit(T0CS_INDEX, value);
    }

    public boolean getINTEDG() {
        return isBitSet(INTEDG_INDEX);
    }

    public void setINTEDG(boolean value) {
        setBit(INTEDG_INDEX, value);
    }

    public boolean getRBPU() {
        return isBitSet(RBPU_INDEX);
    }

    public void setRBPU(boolean value) {
        setBit(RBPU_INDEX, value);
    }
}
