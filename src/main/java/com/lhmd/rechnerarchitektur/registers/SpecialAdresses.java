package com.lhmd.rechnerarchitektur.registers;

import java.util.List;

public final class SpecialAdresses {
    private SpecialAdresses() {
    }

    public static final int INDF = 0x00;
    public static final int PCL = 0x02;
    public static final int STATUS = 0x03;
    public static final int FSR = 0x04;
    public static final int PCLATH = 0x0A;
    public static final int INTCON = 0x0B;

    public static final List<Integer> MIRRORED = List.of(INDF, PCL, STATUS, FSR, PCLATH, INTCON);

    public static final int TMR0 = 0x01;
    public static final int OPTION = 0x81;
}
