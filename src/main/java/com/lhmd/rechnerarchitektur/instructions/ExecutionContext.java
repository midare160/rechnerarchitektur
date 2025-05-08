package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.*;

public record ExecutionContext(DataMemory dataMemory, ProgramStack programStack) {
}
