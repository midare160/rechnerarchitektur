package com.lhmd.rechnerarchitektur.Instructions;

import java.util.UUID;

public record ParsedInstruction(UUID id, int programCounter, int instruction, String rawText) {
}
