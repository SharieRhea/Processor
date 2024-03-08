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
        // stackPointer should begin at 1024.
        stackPointer.setBit(22, new Bit (true));
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
        Word instructionFormat = currentInstruction.and(INSTRUCTION_FORMAT_MASK);
        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue())
            decode3R();
        else if (instructionFormat.getBit(30).getValue())
            decode2R();
        else if (instructionFormat.getBit(31).getValue())
            decode1R();
        else
            decode0R();
    }

    private void execute() {
        Word instructionFormat = currentInstruction.and(INSTRUCTION_FORMAT_MASK);
        Word operation = currentInstruction.and(OPERATION_MASK).rightShift(2);
        if (!operation.getBit(29).getValue() && !operation.getBit(30).getValue() && !operation.getBit(31).getValue())
            executeMathOps(instructionFormat);
    }

    private void store() {
        Word instructionFormat = currentInstruction.and(INSTRUCTION_FORMAT_MASK);
        Word operation = currentInstruction.and(OPERATION_MASK).rightShift(2);
        if (!operation.getBit(29).getValue() && !operation.getBit(30).getValue() && !operation.getBit(31).getValue()) {
            if (instructionFormat.getBit(30).getValue() || instructionFormat.getBit(31).getValue()) {
                Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
                System.out.printf("DEBUG-- writing %d to register %d\n", destination.getUnsigned(), dest.getUnsigned());
                writeRegister(getUnsigned(dest), alu.result);
            }
        }
    }

    private int getUnsigned(Word word) {
        int returnValue = 0;
        for (int i = 0; i < 32; i++) {
            if (word.getBit(i).getValue())
                returnValue += (int) Math.pow(2, 32 - 1 - i);
        }
        return returnValue;
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

    private void decode3R() {
        // Need to get imm, Rs1, Rs2, Rd
        Word immMask = new Word();
        for (int i = 0; i < 8; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(24);
        Word reg1Mask = new Word();
        for (int i = 8; i < 13; i++) {
            reg1Mask.setBit(i, new Bit(true));
        }
        Word reg1 = currentInstruction.and(reg1Mask).rightShift(19);
        source1 = readRegister(getUnsigned(reg1));
        Word reg2Mask = new Word();
        for (int i = 13; i < 18; i++) {
            reg2Mask.setBit(i, new Bit(true));
        }
        Word reg2 = currentInstruction.and(reg2Mask).rightShift(14);
        source2 = readRegister(getUnsigned(reg2));
        Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
        destination = readRegister(getUnsigned(dest));
        System.out.printf("DEBUG-- 3R: imm=%d rs1=R%d=%d rs2=R%d=%d rd=R%d=%d%n", immediate.getUnsigned(), reg1.getUnsigned(), source1.getUnsigned(), reg2.getUnsigned(), source2.getUnsigned(), dest.getUnsigned(), destination.getUnsigned());
    }

    private void decode2R() {
        // Need to get imm, Rs1, Rd
        Word immMask = new Word();
        for (int i = 0; i < 13; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(19);
        Word reg1Mask = new Word();
        for (int i = 13; i < 18; i++) {
            reg1Mask.setBit(i, new Bit(true));
        }
        Word reg1 = currentInstruction.and(reg1Mask).rightShift(14);
        source1 = readRegister(getUnsigned(reg1));
        Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
        destination = readRegister(getUnsigned(dest));
        System.out.printf("DEBUG-- 2R: imm=%d rs1=R%d=%d rd=R%d=%d%n", immediate.getUnsigned(), reg1.getUnsigned(), source1.getUnsigned(), dest.getUnsigned(), destination.getUnsigned());
    }

    private void decode1R() {
        // Need to get imm, Rd
        Word immMask = new Word();
        for (int i = 0; i < 18; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(14);
        Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
        destination = readRegister(getUnsigned(dest));
        System.out.printf("DEBUG-- 1R: imm=%d rd=R%d=%d%n", immediate.getUnsigned(), dest.getUnsigned(), destination.getUnsigned());
    }

    private void decode0R() {
        // Need to get immediate
        Word immMask = new Word();
        for (int i = 0; i < 27; i++) {
            immMask.setBit(i, new Bit(true));
        }
        immediate = currentInstruction.and(immMask).rightShift(5);
        System.out.printf("DEBUG-- 0R: imm=%d%n", immediate.getUnsigned());
    }

    private void executeMathOps(Word instructionFormat) {
        Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
        Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };
        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // Rd <- Rs1 op Rs2
            alu.operand1 = source1;
            alu.operand2 = source2;
            System.out.printf("DEBUG-- %d op %d%n", source1.getUnsigned(), source2.getUnsigned());
            alu.doOperation(op);
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // Rd <- Rd op Rs1
            alu.operand1 = destination;
            alu.operand2 = source1;
            System.out.printf("DEBUG-- %d op %d%n", destination.getUnsigned(), source1.getUnsigned());
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

    // Used for testing
    public Word[] getRegisters() {
        return registers;
    }
}
