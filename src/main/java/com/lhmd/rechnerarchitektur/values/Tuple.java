package com.lhmd.rechnerarchitektur.values;

public record Tuple<T1, T2>(T1 item1, T2 item2) {
    public static <T1, T2> Tuple<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple<>(t1, t2);
    }
}
