package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.computing.Alu;
import com.lhmd.rechnerarchitektur.memory.*;

public record ExecutionContext(
        DataMemory dataMemory,
        ProgramStack programStack,
        Alu alu) {
}
