package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

import java.util.HashMap;

public class InstructionDecoder {

    private static final HashMap<Integer, Class<? extends Instruction>> OPCODES = new HashMap<>();

    static {
        OPCODES.put(0b11_1110_0000_0000, Addlw.class);
        OPCODES.put(0b11_1001_0000_0000, Andlw.class);
        OPCODES.put(0b10_1000_0000_0000, Goto.class);
        OPCODES.put(0b11_1000_0000_0000, Iorlw.class);
        OPCODES.put(0b11_0000_0000_0000, Movlw.class);
        OPCODES.put(0b11_1100_0000_0000, Sublw.class);
        OPCODES.put(0b11_1010_0000_0000, Xorlw.class);
    }

    public static Instruction decode(String instruction) throws ReflectiveOperationException {
        var hexOpCode = IntUtils.tryParse(instruction, 16);

        if (hexOpCode == null) {
            return null;
        }

        for (var kvp : OPCODES.entrySet()) {
            if ((hexOpCode & kvp.getKey()) != kvp.getKey()) {
                continue;
            }

            return kvp.getValue()
                    .getDeclaredConstructor(int.class)
                    .newInstance(hexOpCode);
        }

        throw new NoSuchMethodException("No instruction found for " + instruction);
    }

//    public Object decode(Instruction instruction) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        CLASS_MAP.put(0b001, Object.class);
//
//        var instructionClass = CLASS_MAP.get(0b101);
//
//        var fuck = (IInstruction)instructionClass.getDeclaredConstructor(int.class, int.class).newInstance(12);
//
//        for(var num : asdfsadf) {
//            fuck.execute();
//        }
//    }
}
