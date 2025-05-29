package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.memory.ProgramStack;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.time.RuntimeManager;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Supplier;

@Component
public class InterruptManager {
    public static final int INTERRUPT_ADDRESS = 0x04;

    private final RuntimeManager runtimeManager;
    private final ProgramStack programStack;
    private final ProgramCounter programCounter;
    private final IntconRegister intconRegister;
    private final EECon1Register eeCon1Register;
    private final Map<Supplier<Boolean>, Supplier<Boolean>> interruptMap;

    public InterruptManager(
            RuntimeManager runtimeManager,
            ProgramStack programStack,
            ProgramCounter programCounter,
            IntconRegister intconRegister,
            EECon1Register eeCon1Register) {
        this.runtimeManager = runtimeManager;
        this.programStack = programStack;
        this.programCounter = programCounter;
        this.intconRegister = intconRegister;
        this.eeCon1Register = eeCon1Register;

        this.interruptMap = getInterruptMap();
    }

    public void checkForInterrupt() {
        if (!intconRegister.getGIE()) {
            return;
        }

        for (var entry : interruptMap.entrySet()) {
            if (!entry.getKey().get() || !entry.getValue().get()) {
                continue;
            }

            handleInterrupt();
            break;
        }
    }

    private Map<Supplier<Boolean>, Supplier<Boolean>> getInterruptMap() {
        return Map.of(
                intconRegister::getRBIE, intconRegister::getRBIF,
                intconRegister::getINTE, intconRegister::getINTF,
                intconRegister::getT0IE, intconRegister::getT0IF,
                intconRegister::getEEIE, eeCon1Register::getEEIF);
    }

    private void handleInterrupt() {
        intconRegister.setGIE(false);

        programStack.push(programCounter.get());
        programCounter.set(INTERRUPT_ADDRESS);

        runtimeManager.addCycle();
        runtimeManager.addCycle();
        runtimeManager.addCycle();
    }
}
