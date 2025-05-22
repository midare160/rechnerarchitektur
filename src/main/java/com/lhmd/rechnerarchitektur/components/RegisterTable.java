package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.components.common.BitPointerTable;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import org.springframework.beans.factory.BeanFactory;

public class RegisterTable extends BitPointerTable {
    public RegisterTable() {
        super(DataMemory.REGISTER_WIDTH);
    }

    public void initialize(BeanFactory beanFactory){
        var dataMemory = beanFactory.getBean(DataMemory.class);
        var registers = dataMemory.registers();

        for (var i = 0; i < registers.size(); i++) {
            addRow(registers.get(i), "0x%02X".formatted(i));
        }
    }
}
