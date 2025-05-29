package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class StatusRegister extends SpecialRegister {
    private static final int C_INDEX = 0;
    private static final int DC_INDEX = 1;
    private static final int Z_INDEX = 2;
    private static final int PD_INDEX = 3;
    private static final int TO_INDEX = 4;
    private static final int RP0_INDEX = 5;

    @Override
    public int getAddress() {
        return 0x03;
    }

    @Override
    public boolean isMirrored() {
        return true;
    }

    @EventListener
    @Order(EventOrders.DATA)
    public void handleReset(ResetEvent event) {
        var pattern = switch (event.resetType()) {
            case POWERON -> "00011xxx";
            case WATCHDOG -> "00001uuu";
            case WAKEUP -> "uuu00uuu"; // TODO interrupt wakeup
        };

        set(IntUtils.changeBits(get(), pattern));
    }

    public boolean getC() {
        return isBitSet(C_INDEX);
    }

    public void setC(boolean value) {
        setBit(C_INDEX, value);
    }

    public boolean getDC() {
        return isBitSet(DC_INDEX);
    }

    public void setDC(boolean value) {
        setBit(DC_INDEX, value);
    }

    public boolean getZ() {
        return isBitSet(Z_INDEX);
    }

    public void setZ(boolean value) {
        setBit(Z_INDEX, value);
    }

    public boolean getPD() {
        return isBitSet(PD_INDEX);
    }

    public void setPD(boolean value) {
        setBit(PD_INDEX, value);
    }

    public boolean getTO() {
        return isBitSet(TO_INDEX);
    }

    public void setTO(boolean value) {
        setBit(TO_INDEX, value);
    }

    public boolean getRP0() {
        return isBitSet(RP0_INDEX);
    }

    public void setRP0(boolean value) {
        setBit(RP0_INDEX, value);
    }

    public void updateC_Add(int a, int b) {
        setC(a + b >= DataMemory.REGISTER_MAX_SIZE);
    }

    public void updateC_Sub(int a, int b) {
        setC(b > a);
    }

    public void updateDC_Add(int a, int b) {
        // Mask to get lower 4 bits (nibble)
        var lowerNibbleSum = (a & 0x0F) + (b & 0x0F);
        var carryOccured = lowerNibbleSum > 0x0F;

        // DC = true if carry from bit 3
        setDC(carryOccured);
    }

    public void updateDC_Sub(int a, int b) {
        // For subtraction, polarity is reversed
        var noBorrowOccured = (a & 0x0F) >= (b & 0x0F);

        // DC = true if no borrow from bit 3
        setDC(noBorrowOccured);
    }

    public void updateZ(int result) {
        setZ(result == 0);
    }
}
