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
        if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
            // 11 = 3R
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
        }
        else if (instructionFormat.getBit(30).getValue()) {
            // 10 = 2R
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
        }
        else if (instructionFormat.getBit(31).getValue()) {
            // 01 = 1R
            Word immMask = new Word();
            for (int i = 0; i < 18; i++) {
                immMask.setBit(i, new Bit(true));
            }
            immediate = currentInstruction.and(immMask).rightShift(14);
            Word dest = currentInstruction.and(DEST_MASK).rightShift(5);
            destination = readRegister(getUnsigned(dest));
        }
        else {
            // 0R
            Word immMask = new Word();
            for (int i = 0; i < 27; i++) {
                immMask.setBit(i, new Bit(true));
            }
            immediate = currentInstruction.and(immMask).rightShift(5);
        }
    }

    private void execute() {
        Word instructionFormat = currentInstruction.and(INSTRUCTION_FORMAT_MASK);
        Word operation = currentInstruction.and(OPERATION_MASK).rightShift(2);
        if (!operation.getBit(29).getValue() && !operation.getBit(30).getValue() && !operation.getBit(31).getValue()) {
            // math op
            Word function = currentInstruction.and(FUNCTION_MASK).rightShift(10);
            Bit[] op = new Bit[] { function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31) };
            System.out.printf("DEBUG-- R1 %d R2 %d R3 %d%n", registers[1].getUnsigned(), registers[2].getUnsigned(), registers[3].getUnsigned());
            if (instructionFormat.getBit(30).getValue() && instructionFormat.getBit(31).getValue()) {
                alu.operand1 = source1;
                alu.operand2 = source2;
                System.out.printf("DEBUG-- %d op %d%n", source1.getUnsigned(), source2.getUnsigned());
                alu.doOperation(op);
            }
            else if (instructionFormat.getBit(30).getValue()) {
                alu.operand1 = destination;
                alu.operand2 = source1;
                System.out.printf("DEBUG-- %d op %d%n", destination.getUnsigned(), source1.getUnsigned());
                alu.doOperation(op);
            }
            else if (instructionFormat.getBit(31).getValue()) {
                alu.result = immediate;
            }
            else
                halted.set(true);

            destination.copy(alu.result);
        }
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

    // Used for testing
    public Word[] getRegisters() {
        return registers;
    }
}
