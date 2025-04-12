package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.common.IntBox;

public class WRegister {
    private static final IntBox instance = new IntBox();

    public static IntBox instance() {
        return instance;
    }

    public static void reset() {
        instance.set(0);
    }
}
