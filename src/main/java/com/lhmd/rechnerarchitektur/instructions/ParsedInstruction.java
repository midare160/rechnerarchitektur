package com.lhmd.rechnerarchitektur.instructions;

import java.util.UUID;

public record ParsedInstruction(UUID id, int programCounter, int instruction, String rawText) {
}
