package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.*;

public record ExecutionParams(DataMemory dataMemory, ProgramStack programStack) {
}
