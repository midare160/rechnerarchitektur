package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.components.common.BitPointerTable;
import com.lhmd.rechnerarchitektur.memory.EEMemory;
import org.springframework.beans.factory.BeanFactory;

public class EEMemoryTable extends BitPointerTable {
    public EEMemoryTable() {
        super(EEMemory.REGISTER_WIDTH);
    }

    public void initialize(BeanFactory beanFactory){
        var eeMemory = beanFactory.getBean(EEMemory.class);
        var registers = eeMemory.registers();

        for (var i = 0; i < registers.size(); i++) {
            addRow(registers.get(i), "0x%02X".formatted(i));
        }
    }
}
