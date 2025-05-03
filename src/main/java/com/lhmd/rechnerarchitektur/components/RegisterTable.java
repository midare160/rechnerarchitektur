package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.memory.DataMemory;

public class RegisterTable extends BitPointerTable {
    private DataMemory dataMemory;

    public RegisterTable() {
        super(8);
    }

    public void setData(DataMemory dataMemory){
        this.dataMemory = dataMemory;

        var registers = dataMemory.registers();

        for (var i = 0; i < registers.size(); i++) {
            addRow(registers.get(i), "0x%04X".formatted(i));
        }
    }
}
