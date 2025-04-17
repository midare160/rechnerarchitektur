package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.instructions.*;

import java.util.*;

public class InstructionParser {
    public static Map<Integer, Instruction> parseRawInstructions(Collection<? extends RawInstruction> instructions) {
        var instructionMap = new HashMap<Integer, Instruction>();

        for (var instruction : instructions) {
            var parsedAddress = IntUtils.tryParse(instruction.getAddress(), 16);
            var parsedInstruction = IntUtils.tryParse(instruction.getInstruction(), 16);

            if (parsedAddress == null || parsedInstruction == null) {
                continue;
            }

            instructionMap.put(parsedAddress, InstructionDecoder.decode(parsedInstruction));
        }

        return instructionMap;
    }
}
