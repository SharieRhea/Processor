public class Processor {
    private ALU alu = new ALU();
    // programCounter begins at 0
    private Word programCounter = new Word();
    private Word stackPointer = new Word();
    private Word currentInstruction;
    private Bit halted = new Bit();
    private Word[] registers = new Word[32];

    private Word source1;
    private Word source2;
    private Word immediate;
    private Word destination;

    // Commonly used masks
    private final Word INSTRUCTION_FORMAT_MASK = new Word();
    private final Word DEST_MASK = new Word();
    private final Word FUNCTION_MASK = new Word();
    private final Word OPERATION_MASK = new Word();

    public Processor() {
        // stackPointer should begin at 1023.
        stackPointer.setBit(21, new Bit (true));
        stackPointer.copy(stackPointer.decrement());
        // initialize registers to be empty
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Word();
        }
        
        // Initialize masks
        INSTRUCTION_FORMAT_MASK.setBit(30, new Bit(true));
        INSTRUCTION_FORMAT_MASK.setBit(31, new Bit(true));
        for (int i = 22; i < 27; i++) {
            DEST_MASK.setBit(i, new Bit(true));
        }
        for (int i = 18; i < 22; i++) {
            FUNCTION_MASK.setBit(i, new Bit(true));
        }
        for (int i = 27; i < 30; i++) {
            OPERATION_MASK.setBit(i, new Bit(true));
        }
    }

    public void run() {
        while (!halted.getValue()) {
            fetch();
            decode();
            execute();
            store();
        }
    }

    private void fetch() {
        currentInstruction = MainMemory.read(programCounter);
        programCounter = programCounter.increment();
    }

    private void decode() {
        // Use last two bits to determine instruction format
        Word instructionFormat = currentInstruction.and(INSTRUCTION_FORMAT_MASK);
        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue())
            decode2R();
        else if (instructionFormat.getBit(30).getValue())
            decode3R();
        else if (instructionFormat.getBit(31).getValue())
            decode1R();
        else
            decode0R();
    }

    private void execute() {
        // What to execute depends on both the 3 operation bits and the 2 instruction format bits
        Word instructionFormat = currentInstruction.and(INSTRUCTION_FORMAT_MASK);
        Word operation = currentInstruction.and(OPERATION_MASK).rightShift(2);
        if (!operation.getBit(29).getValue() && !operation.getBit(30).getValue() && !operation.getBit(31).getValue())
            executeMathOps(instructionFormat);
        else if (!operation.getBit(29).getValue() && !operation.getBit(30).getValue() && operation.getBit(31).getValue())
            executeBranchOps(instructionFormat);
        else if (!operation.getBit(29).getValue() && operation.getBit(30).getValue() && !operation.getBit(31).getValue())
            executeCallOps(instructionFormat);
        else if (!operation.getBit(29).getValue() && operation.getBit(30).getValue() && operation.getBit(31).getValue())
            executePushOps(instructionFormat);
        else if (operation.getBit(29).getValue() && !operation.getBit(30).getValue() && !operation.getBit(31).getValue())
            executeLoadOps(instructionFormat);
        else if (operation.getBit(29).getValue() && !operation.getBit(30).getValue() && operation.getBit(31).getValue())
            executeStoreOps(instructionFormat);
        else
            executePopInterruptOps(instructionFormat);
    }

    private void store() {
        Word instructionFormat = currentInstruction.and(INSTRUCTION_FORMAT_MASK);
        Word operation = currentInstruction.and(OPERATION_MASK).rightShift(2);
        // Math operations
        if (!operation.getBit(29).getValue() && !operation.getBit(30).getValue() && !operation.getBit(31).getValue()) {
            // Special case: check if this instruction was to halt
            if (!instructionFormat.getBit(30).getValue() && !instructionFormat.getBit(31).getValue())
                return;
            // Was a valid operation (not a halt), go ahead and store
            Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
            writeRegister(getRegister(dest), alu.result);
        }
        // Branch operations
        else if (!operation.getBit(29).getValue() && !operation.getBit(30).getValue() && operation.getBit(31).getValue()) {
            programCounter.copy(alu.result);
        }
        // Call operations
        else if (!operation.getBit(29).getValue() && operation.getBit(30).getValue() && !operation.getBit(31).getValue()) {
            programCounter.copy(alu.result);
        }
        // Push operations
        else if (!operation.getBit(29).getValue() && operation.getBit(30).getValue() && operation.getBit(31).getValue()) {
            // Special case: 0R is unused, don't store
            if (!instructionFormat.getBit(30).getValue() && !instructionFormat.getBit(31).getValue())
                return;
            MainMemory.write(stackPointer, alu.result);
            stackPointer.copy(stackPointer.decrement());
        }
        // Load operations
        else if (operation.getBit(29).getValue() && !operation.getBit(30).getValue() && !operation.getBit(31).getValue()) {
            // Special case: 0R is for return, don't store anything into Rd
            if (!instructionFormat.getBit(30).getValue() && !instructionFormat.getBit(31).getValue())
                return;
            Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
            writeRegister(getRegister(dest), MainMemory.read(alu.result));
        }
        // Store operations
        else if (operation.getBit(29).getValue() && !operation.getBit(30).getValue() && operation.getBit(31).getValue()) {
            if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue())
                MainMemory.write(alu.result, source1);
            else if (instructionFormat.getBit(30).getValue())
                MainMemory.write(alu.result, source2);
            else if (instructionFormat.getBit(31).getValue())
                MainMemory.write(destination, immediate);
            // 0R is unused, do nothing
        }
        // Peek/Pop operations
        else if (operation.getBit(29).getValue() && operation.getBit(30).getValue() && !operation.getBit(31).getValue()) {
            // 0R is interrupt, do nothing for now
            if (!instructionFormat.getBit(30).getValue() && !instructionFormat.getBit(31).getValue())
                return;
            Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
            writeRegister(getRegister(dest), MainMemory.read(alu.result));
        }
    }

    private void executeMathOps(Word instructionFormat) {
        Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
        Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };

        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // Rd <- Rd op Rs1
            alu.operand1 = destination;
            alu.operand2 = source1;
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // Rd <- Rs1 op Rs2
            alu.operand1 = source1;
            alu.operand2 = source2;
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(31).getValue()) {
            // Rd <- imm
            alu.result = immediate;
        }
        else {
            // Special case: set halted bit, don't copy result into destination
            halted.set(true);
            return;
        }

        destination.copy(alu.result);
    }

    private void executeBranchOps(Word instructionFormat) {
        Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
        Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };
        Bit[] addOp = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit() };

        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // pc <- Rs1 bop Rd ? pc + imm : pc
            if (performBooleanOp(op, source1, destination).getValue()) {
                alu.operand1 = programCounter;
                alu.operand2 = immediate;
                alu.doOperation(addOp);
                programCounter.copy(alu.result);
            }
            else
                alu.result = programCounter;
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // pc <- Rs1 bop Rs2 ? pc + imm : pc
            if (performBooleanOp(op, source1, source2).getValue()) {
                alu.operand1 = programCounter;
                alu.operand2 = immediate;
                alu.doOperation(addOp);
            }
            else
                alu.result = programCounter;
        }
        else if (instructionFormat.getBit(31).getValue()) {
            // jump: pc <- pc + imm
            alu.operand1 = programCounter;
            alu.operand2 = immediate;
            alu.doOperation(addOp);
        }
        else {
            // jump: pc <- imm
            alu.result = immediate;
        }
    }

    private void executeCallOps(Word instructionFormat) {
        Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
        Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };
        Bit[] addOp = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit() };

        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // pc <- Rs1 BOP Rd ? push pc; pc + imm : pc
            if (performBooleanOp(op, source1, destination).getValue()) {
                MainMemory.write(stackPointer, programCounter);
                stackPointer.copy(stackPointer.decrement());

                alu.operand1 = programCounter;
                alu.operand2 = immediate;
                alu.doOperation(addOp);
            }
            else
                alu.result = programCounter;
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // pc <- Rs1 bop Rs2 ? push pc; Rd + imm : pc
            if (performBooleanOp(op, source1, source2).getValue()) {
                MainMemory.write(stackPointer, programCounter);
                stackPointer.copy(stackPointer.decrement());

                alu.operand1 = destination;
                alu.operand2 = immediate;
                alu.doOperation(addOp);
            }
            else
                alu.result = programCounter;
        }
        else if (instructionFormat.getBit(31).getValue()) {
            // push pc; pc <- Rd + imm
            MainMemory.write(stackPointer, programCounter);
            stackPointer.copy(stackPointer.decrement());

            alu.operand1 = destination;
            alu.operand2 = immediate;
            alu.doOperation(addOp);
        }
        else {
            // push pc; pc <- imm
            MainMemory.write(stackPointer, programCounter);
            stackPointer.copy(stackPointer.decrement());

            alu.result = immediate;
        }
    }

    private void executePushOps(Word instructionFormat) {
        Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
        Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };

        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // mem[--sp] <- Rd MOP Rs1
            alu.operand1 = destination;
            alu.operand2 = source1;
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // mem[--sp] <- Rs1 MOP Rs2
            alu.operand1 = source1;
            alu.operand2 = source2;
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(31).getValue()) {
            // mem[--sp] <- Rd MOP imm
            alu.operand1 = destination;
            alu.operand2 = immediate;
            alu.doOperation(op);
        }
        // 0R is UNUSED
    }

    private void executeLoadOps(Word instructionFormat) {
        Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
        Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };

        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // Rd <- mem[Rs1 + imm]
            alu.operand1 = source1;
            alu.operand2 = immediate;
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // Rd <- mem[Rs1 + Rs2]
            alu.operand1 = source1;
            alu.operand2 = source2;
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(31).getValue()) {
            // Rd <- mem[Rd + imm]
            alu.operand1 = destination;
            alu.operand2 = immediate;
            alu.doOperation(op);
        }
        else {
            // return (pc <- pop)
            stackPointer.copy(stackPointer.increment());
            programCounter.copy(MainMemory.read(stackPointer));
        }
    }

    private void executeStoreOps(Word instructionFormat) {
        Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
        Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };

        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // mem[Rd + imm] <- Rs1
            alu.operand1 = destination;
            alu.operand2 = immediate;
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // mem[Rd + Rs1] <- Rs2
            alu.operand1 = destination;
            alu.operand2 = source1;
            alu.doOperation(op);
        }
        // 1R: mem[Rd] <- Rd
        // 0R is UNUSED
    }

    private void executePopInterruptOps(Word instructionFormat) {
        Bit[] additionOp = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit() };

        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // peek: Rd <- mem[sp + (Rs1 + imm)]
            alu.operand1 = source1;
            alu.operand2 = immediate;
            alu.doOperation(additionOp);
            alu.operand1 = stackPointer;
            alu.operand2 = alu.result;
            alu.doOperation(additionOp);
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // peek: Rd <- mem[sp + (Rs1 + Rs2)]
            alu.operand1 = source1;
            alu.operand2 = source2;
            alu.doOperation(additionOp);
            alu.operand1 = stackPointer;
            alu.operand2 = alu.result;
            alu.doOperation(additionOp);
        }
        else if (instructionFormat.getBit(31).getValue()) {
            // pop: Rd <- mem[sp++]
            alu.result = stackPointer.increment();
            stackPointer.copy(alu.result);
        }
        // todo: interrupt: push pc; pc <- intvec[imm]
    }

    private Bit performBooleanOp(Bit[] operation, Word operand1, Word operand2) {
        Bit[] subtractionOp = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit(true) };
        // Subtract the two operands to compare their values
        alu.operand1 = operand1;
        alu.operand2 = operand2;
        alu.doOperation(subtractionOp);
        Word result = new Word();
        result.copy(alu.result);

        if (!operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue()) {
            // equal
            for (int i = 0; i < 32; i++) {
                if (result.getBit(i).getValue())
                    return new Bit();
            }
            return new Bit(true);
        }
        else if (!operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && operation[3].getValue()) {
            // not equal
            for (int i = 0; i < 32; i++) {
                if (result.getBit(i).getValue())
                    return new Bit(true);
            }
            return new Bit();
        }
        else if (!operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && !operation[3].getValue()) {
            // less than
            return (result.getBit(0).getValue()) ? new Bit(true) : new Bit();
        }
        else if (!operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && operation[3].getValue()) {
            // greater than or equal
            return (result.getBit(0).getValue()) ? new Bit() : new Bit(true);
        }
        else if (!operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue()) {
            // greater than
            if (result.getBit(0).getValue())
                return new Bit();
            // check for 0
            for (int i = 0; i < 32; i++) {
                if (result.getBit(i).getValue())
                    return new Bit(true);
            }
            return new Bit();
        }
        else {
            // less than or equal
            if (result.getBit(0).getValue())
                return new Bit(true);
            // check for 0
            for (int i = 0; i < 32; i++) {
                if (result.getBit(i).getValue())
                    return new Bit();
            }
            return new Bit(true);
        }
    }

    private void decode3R() {
        // Need to get imm, Rs1, Rs2, Rd
        Word immMask = new Word();
        for (int i = 0; i < 8; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(24);
        // Need to account for negative numbers by extending sign
        if (currentInstruction.getBit(0).getValue()) {
            for (int i = 0; i < 24; i++) {
                immediate.setBit(i, new Bit(true));
            }
        }

        Word reg1Mask = new Word();
        for (int i = 8; i < 13; i++) {
            reg1Mask.setBit(i, new Bit(true));
        }
        Word reg1 = currentInstruction.and(reg1Mask).rightShift(19);
        source1 = readRegister(getRegister(reg1));
        Word reg2Mask = new Word();
        for (int i = 13; i < 18; i++) {
            reg2Mask.setBit(i, new Bit(true));
        }
        Word reg2 = currentInstruction.and(reg2Mask).rightShift(14);
        source2 = readRegister(getRegister(reg2));
        Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
        destination = readRegister(getRegister(dest));
    }

    private void decode2R() {
        // Need to get imm, Rs1, Rd
        Word immMask = new Word();
        for (int i = 0; i < 13; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(19);
        // Need to account for negative numbers by extending sign
        if (currentInstruction.getBit(0).getValue()) {
            for (int i = 0; i < 19; i++) {
                immediate.setBit(i, new Bit(true));
            }
        }

        Word reg1Mask = new Word();
        for (int i = 13; i < 18; i++) {
            reg1Mask.setBit(i, new Bit(true));
        }
        Word reg1 = currentInstruction.and(reg1Mask).rightShift(14);
        source1 = readRegister(getRegister(reg1));
        Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
        destination = readRegister(getRegister(dest));
    }

    private void decode1R() {
        // Need to get imm, Rd
        Word immMask = new Word();
        for (int i = 0; i < 18; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(14);
        // Need to account for negative numbers by extending sign
        if (currentInstruction.getBit(0).getValue()) {
            for (int i = 0; i < 14; i++) {
                immediate.setBit(i, new Bit(true));
            }
        }
        Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
        destination = readRegister(getRegister(dest));
    }

    private void decode0R() {
        // Need to get immediate
        Word immMask = new Word();
        for (int i = 0; i < 27; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(5);
        // Need to account for negative numbers by extending sign
        if (currentInstruction.getBit(0).getValue()) {
            for (int i = 0; i < 5; i++) {
                immediate.setBit(i, new Bit(true));
            }
        }
    }

    // Ensures register 0 is always read as 0
    private Word readRegister(int index) {
        if (index == 0)
            return new Word();
        return registers[index];
    }

    // Ensures register 0 cannot be overwritten
    private void writeRegister(int index, Word value) {
        if (index == 0)
            return;
        registers[index].copy(value);
    }

    // Helper function that gets a register's array index, manually checks bits (no math)
    private int getRegister(Word word) {
        Bit[] registerNumber = new Bit[] { word.getBit(27), word.getBit(28), word.getBit(29), word.getBit(30), word.getBit(31) };

        if (registerNumber[0].getValue()) { // 1XXXX
            if (registerNumber[1].getValue()) { // 11XXX
                if (registerNumber[2].getValue()) { // 111XX
                    if (registerNumber[3].getValue()) // 1111X
                        return (registerNumber[4].getValue()) ? 31 : 30;
                    else // 1110X
                        return (registerNumber[4].getValue()) ? 29 : 28;
                }
                else { // 110XX
                    if (registerNumber[3].getValue()) // 1101X
                        return (registerNumber[4].getValue()) ? 27 : 26;
                    else // 1100X
                        return (registerNumber[4].getValue()) ? 25 : 24;
                }
            }
            else { // 10XXX
                if (registerNumber[2].getValue()) { // 101XX
                    if (registerNumber[3].getValue()) // 1011X
                        return (registerNumber[4].getValue()) ? 23 : 22;
                    else // 1010X
                        return (registerNumber[4].getValue()) ? 21 : 20;
                }
                else { // 100XX
                    if (registerNumber[3].getValue()) // 1001X
                        return (registerNumber[4].getValue()) ? 19 : 18;
                    else // 1000X
                        return (registerNumber[4].getValue()) ? 17 : 16;
                }
            }
        }
        else { // 0XXXX
            if (registerNumber[1].getValue()) { // 01XXX
                if (registerNumber[2].getValue()) { // 011XX
                    if (registerNumber[3].getValue()) // 0111X
                        return (registerNumber[4].getValue()) ? 15 : 14;
                    else // 0110X
                        return (registerNumber[4].getValue()) ? 13 : 12;
                }
                else { // 010XX
                    if (registerNumber[3].getValue()) // 0101X
                        return (registerNumber[4].getValue()) ? 11 : 10;
                    else // 0100X
                        return (registerNumber[4].getValue()) ? 9 : 8;
                }
            }
            else { // 00XXX
                if (registerNumber[2].getValue()) { // 001XX
                    if (registerNumber[3].getValue()) // 0011X
                        return (registerNumber[4].getValue()) ? 7 : 6;
                    else // 0010X
                        return (registerNumber[4].getValue()) ? 5 : 4;
                }
                else { // 000XX
                    if (registerNumber[3].getValue()) // 0001X
                        return (registerNumber[4].getValue()) ? 3 : 2;
                    else // 0000X
                        return (registerNumber[4].getValue()) ? 1 : 0;
                }
            }
        }
    }

    // Used for testing
    public Word[] getRegisters() {
        return registers;
    }

    // Used for  testing
    public Word getStackPointer() {
        return stackPointer;
    }
}
