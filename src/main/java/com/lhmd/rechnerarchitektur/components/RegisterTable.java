package com.lhmd.rechnerarchitektur.components;

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
            addRow(registers.get(i), "0x%04X".formatted(i));
        }
    }
}
