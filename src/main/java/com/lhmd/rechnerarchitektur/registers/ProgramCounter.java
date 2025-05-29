package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.changes.*;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ProgramCounter extends IntBox {
    public static final int WIDTH = 13;
    public static final int MAX_SIZE = (int) Math.pow(2, WIDTH);

    private final PclRegister pclRegister;
    private final PclathRegister pclathRegister;
    private final ChangeManager changeManager;

    public ProgramCounter(PclRegister pclRegister, PclathRegister pclathRegister) {
        this.pclRegister = pclRegister;
        this.pclathRegister = pclathRegister;
        this.changeManager = new ChangeManager();

        onChanged().addListener(this::onPcChanged);
        pclRegister.onChanged().addListener(this::onPclChanged);
    }

    /**
     * If {@code value} exceeds {@code MAX_SIZE}, PC is wrapped around.
     */
    @Override
    public void setValue(Integer value) {
        super.setValue(Math.floorMod(value, MAX_SIZE));
    }

    @EventListener
    @Order(EventOrders.DATA)
    public void handleReset(ResetEvent event) {
        var newValue = switch (event.resetType()) {
            case POWERON, WATCHDOG -> 0;
            case WAKEUP_WATCHDOG, WAKEUP_INTERRUPT -> get() + 1;
        };

        set(newValue);
    }

    /**
     * The eleven bit immediate value is loaded into PC bits <10:0>.
     * The upper bits of PC are loaded from PCLATH<4:3>.
     *
     * @param value the eleven bit address
     */
    public void fromJump(int value) {
        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 3, 4);
        var jumpPart = IntUtils.bitRange(value, 0, 10);
        var combined = IntUtils.concatBits(pclathPart, jumpPart, 11);

        set(combined);
    }

    private void onPcChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        try (var ignored = changeManager.beginChange()) {
            pclRegister.set(IntUtils.bitRange(newValue, 0, 7));
        }
    }

    private void onPclChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 0, 4);
        var pclPart = IntUtils.bitRange(newValue, 0, 7);
        var combined = IntUtils.concatBits(pclathPart, pclPart, 8);

        try (var ignored = changeManager.beginChange()) {
            set(combined);
        }
    }
}
