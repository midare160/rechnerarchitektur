package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.events.ResetEvent;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class WRegister extends IntBox implements ApplicationListener<ResetEvent> {
    @Override
    public void onApplicationEvent(@NonNull ResetEvent event) {
        set(0);
    }
}
