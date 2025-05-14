package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.events.ResetEvent;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WRegister extends IntBox {
    @EventListener(ResetEvent.class)
    public void handleReset() {
        set(0);
    }
}
