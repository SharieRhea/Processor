import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestsComputer3 {

    // Decrement Tests
    @Test
    public void decrement0() {
        Word word = new Word();
        assertEquals(-1, word.decrement().getSigned());
    }

    @Test
    public void decrement13() {
        Word word = new Word();
        word.set(13);
        assertEquals(12, word.decrement().getUnsigned());
    }

    @Test
    public void decrement25() {
        Word word = new Word();
        word.set(25);
        assertEquals(24, word.decrement().getUnsigned());
    }

    @Test
    public void decrement1() {
        Word word = new Word();
        word.set(1);
        assertEquals(0, word.decrement().getUnsigned());
    }

    @Test
    public void decrement678() {
        Word word = new Word();
        word.set(678);
        assertEquals(677, word.decrement().getUnsigned());
    }

    @Test
    public void decrement333() {
        Word word = new Word();
        word.set(333);
        assertEquals(332, word.decrement().getUnsigned());
    }

    @Test
    public void decrement31() {
        Word word = new Word();
        word.set(31);
        assertEquals(30, word.decrement().getUnsigned());
    }

    @Test
    public void decrement1024() {
        Word word = new Word();
        word.set(1024);
        assertEquals(1023, word.decrement().getUnsigned());
    }

    @Test
    public void decrementNeg1024() {
        Word word = new Word();
        word.set(-1024);
        assertEquals(-1025, word.decrement().getSigned());
    }

    // Boolean Op tests, branch 3R
    @Test
    public void branchEQ() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000001010011100001000001", // math copy 20 R2
                "00000001000100000100000000000110", // branch eq R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(20, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void dontBranchEQ() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000010000100011100000100001", // math copy 264 R1
                "00000000000001010011100001000001", // math copy 20 R2
                "00000001000100000100000000000010", // branch eq R2 R0 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(284, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void branchNEQ() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000001010011100001000001", // math copy 20 R2
                "00000001000100000100010000000110", // branch neq R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(40, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void dontBranchNEQ() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000110011011100001000001", // math copy 102 R2
                "00000001000100000100010000000110", // branch neq R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(102, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void branchLT() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000001001111100001000001", // math copy 19 R2
                "00000001000100000100100000000110", // branch lt R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(19, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void dontBranchLT() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000110111100000100001", // math copy 13 R1
                "00000000000001001111100001000001", // math copy 19 R2
                "00000001000100000100100000000110", // branch lt R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(32, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void branchGE() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000010111100000100001", // math copy 5 R1
                "00000000000000011111100001000001", // math copy 7 R2
                "00000001000100000100110000000110", // branch ge R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(7, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void branchGEEqual() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000010111100000100001", // math copy 5 R1
                "00000000000000010111100001000001", // math copy 7 R2
                "00000001000100000100110000000110", // branch ge R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(5, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void dontBranchGE() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "01000110001011010011100000100001", // math copy 71860 R1
                "00000000000000000111100001000001", // math copy 1 R2
                "00000001000100000100110000000110", // branch ge R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(71861, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void branchGT() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000010111100000100001", // math copy 5 R1
                "01111111111110110011100001000001", // math copy 131052 R2
                "00000001000100000101000000000110", // branch gt R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(131052, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void dontBranchGT() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000010111100000100001", // math copy 5 R1
                "11111111111111011011100001000001", // math copy -10 R2
                "00000001000100000101000000000110", // branch gt R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(-5, processor.getRegisters()[2].getSigned());
    }

    @Test
    public void branchLE() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000001001111100001000001", // math copy 19 R2
                "00000001000100001010100000000110", // branch le R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(19, processor.getRegisters()[2].getUnsigned());
    }
    @Test
    public void branchLEEqual() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000001010011100001000001", // math copy 20 R2
                "00000001000100001010100000000110", // branch le R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(20, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void dontBranchLE() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000110111100000100001", // math copy 13 R1
                "00000000000001001111100001000001", // math copy 19 R2
                "00000001000100000101010000000110", // branch le R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(32, processor.getRegisters()[2].getUnsigned());
    }

    // Branch 2R, 1R, and 0R
    @Test
    public void branchLT2R() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000001001111100001000001", // math copy 19 R2
                "00000000000010001000100000100111", // branch lt R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(19, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void dontBranchGT2R() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100000100001", // math copy 20 R1
                "00000000000001001111100001000001", // math copy 19 R2
                "00000000000010001000110000100111", // branch gt R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(39, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void branch1R5Lines() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100100000001", // math copy 20 R8
                "00000000000000010100000000000101", // branch jump 5
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000100001110100000011", // math mult R8 R8
        });
        processor.run();
        assertEquals(400, processor.getRegisters()[8].getUnsigned());
    }

    @Test
    public void branch0R5() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000001010011100100000001", // math copy 20 R8
                "00000000000000000000000010100100", // branch jumpto 5
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000100001110100000011", // math mult R8 R8
        });
        processor.run();
        assertEquals(400, processor.getRegisters()[8].getUnsigned());
    }

    // Call tests
    @Test
    public void call2R() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "11111111111110101100001111100001", // math copy -21 R31
                "00000000000111111100010000001011", // call neq R0 R31 3
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000111111111111111001000010", // math sub R31 R31 R18
        });
        processor.run();
        // Make sure pc was pushed onto the stack
        assertEquals(2, MainMemory.read(new Word(1023)).getSigned());
        // Make sure stack pointer was decremented
        assertEquals(1022, processor.getStackPointer().getSigned());
        assertEquals(0, processor.getRegisters()[18].getSigned());
    }

    @Test
    public void call2RDont() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "11111111111110101100001111100001", // math copy -21 R31
                "00000000000111111100000000001011", // call eq R0 R31 3
                "00000000111111111111111001000010", // math sub R31 R31 R18
        });
        processor.run();
        // Make sure pc wasn't pushed onto the stack
        assertEquals(0, MainMemory.read(new Word(1023)).getSigned());
        // Make sure stack pointer wasn't decremented
        assertEquals(1023, processor.getStackPointer().getSigned());
        assertEquals(0, processor.getRegisters()[18].getSigned());
    }

    @Test
    public void call3R() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000100010100000000111100001", // math copy 552 R15
                "00000000000000001000001100000001", // math copy 2 R24
                "00000110000000111100100000001010", // call lt R0 R15 6
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000111111101100000011", // math add R24 R15
        });
        processor.run();
        assertEquals(3, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(1022, processor.getStackPointer().getSigned());
        assertEquals(554, processor.getRegisters()[24].getSigned());
    }

    @Test
    public void call3RDont() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000100010100000000111100001", // math copy 552 R15
                "00000000000000001000001100000001", // math copy 2 R24
                "00000110011110000000100000001010", // call lt R15 R0 6
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000000000000000000000",
                "00000000000000111111101100000011", // math add R24 R15
        });
        processor.run();
        assertEquals(0, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(1023, processor.getStackPointer().getSigned());
        assertEquals(2, processor.getRegisters()[24].getSigned());
    }

    @Test
    public void call1R() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000001000001101101001", // call R27 2
                "00000000000000000000000000000000",
                "00000000000000001000001100000001", // math copy 2 R24
        });
        processor.run();
        assertEquals(1, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(1022, processor.getStackPointer().getSigned());
        assertEquals(2, processor.getRegisters()[24].getSigned());
    }

    @Test
    public void call0R() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000000000000001001000", // call 2
                "00000000000000000000000000000000",
                "00000000000000001000001100000001", // math copy 2 R24
        });
        processor.run();
        assertEquals(1, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(1022, processor.getStackPointer().getSigned());
        assertEquals(2, processor.getRegisters()[24].getSigned());
    }

    // Push tests
    @Test
    public void push2R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "11111111111101100000000110100001", // math copy -40 R13
                "00000000000000101000000111000001", // math copy 10 R14
                "00000000000000111001110110101111", // push mult R13 R14
                "00000000000000111001110110101111", // push mult R13 R14
                "00000000000000111001110110101111", // push mult R13 R14
        });
        processor.run();
        assertEquals(-400, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(-400, MainMemory.read(new Word(1022)).getSigned());
        assertEquals(-400, MainMemory.read(new Word(1021)).getSigned());
        assertEquals(1020, processor.getStackPointer().getSigned());
    }

    @Test
    public void push3R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000010110110100000110100001", // math copy 365 R13
                "00000000000000101000000111000001", // math copy 10 R14
                "00000000011010111001110000001110", // push mult R13 R14
                "00000000011010111001110000001110", // push mult R13 R14
        });
        processor.run();
        assertEquals(3650, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(3650, MainMemory.read(new Word(1022)).getSigned());
        assertEquals(1021, processor.getStackPointer().getSigned());
    }

    @Test
    public void push1R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000000101100111100000001101", // push add R0 89
                "11111111111101100000000110100001", // math copy -40 R13
                "00000000000101100111100110101101", // push add R13 89
        });
        processor.run();
        assertEquals(89, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(49, MainMemory.read(new Word(1022)).getSigned());
        assertEquals(1021, processor.getStackPointer().getSigned());
    }

    // Load tests
    @Test
    public void load2R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000000100110111100000001101", // push add R0 77
                "00000000000000011100000000100001", // math copy R1 7
                "00011111110000000111100001010011", // load add R2 R1 1016
        });
        processor.run();
        assertEquals(77, processor.getRegisters()[2].getSigned());
        assertEquals(1022, processor.getStackPointer().getSigned());
    }

    @Test
    public void load3R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000000100110111100000001101", // push add R0 77
                "00000000000000011100000000100001", // math copy R1 7
                "00000000111111100000000001000001", // math copy R2 1016
                "00000000000010001011100001110010", // load add R3 R1 R2
        });
        processor.run();
        assertEquals(77, processor.getRegisters()[3].getSigned());
        assertEquals(1022, processor.getStackPointer().getSigned());
    }

    @Test
    public void load1R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000000100110111100000001101", // push add R0 77
                "00000000000000011100000000100001", // math copy R1 7
                "00000000111111100011100000110001", // load add R1 1016
        });
        processor.run();
        assertEquals(77, processor.getRegisters()[1].getSigned());
        assertEquals(1022, processor.getStackPointer().getSigned());
    }

    @Test
    public void load0R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000000010000000000111000001", // math copy R14 32
                "00000000000100000000000111100001", // math copy R15 64
                "00000000000000000000000011001000", // call 6
                "00000000000000111111101100000010", // math add R0 R15 R24
                "00000000000000000000000000000000", //
                "00000000000000000000000000000000", //
                "00000000000000111111100111000011", // math add R14 R15
                "00000000000000000000000000010000", // load return

        });
        processor.run();
        assertEquals(64, processor.getRegisters()[24].getSigned());
        assertEquals(96, processor.getRegisters()[14].getSigned());
    }

    // Store tests
    @Test
    public void store2R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000111110100000000010100001", // math copy 1000 R5
                "11111111110010100000000011000001", // math copy -216 R6
                "00000000010100011011100010110111", // store add R6 R5 10

        });
        processor.run();
        assertEquals(-216, MainMemory.read(new Word(1010)).getSigned());
    }

    @Test
    public void store3R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000111110100000000010100001", // math copy 1000 R5
                "11111111110010100000000011000001", // math copy -216 R6
                "11111111110010100000000011100001", // math copy -216 R7
                "00000000001010011011100011110110", // store add R6 R5 R7

        });
        processor.run();
        assertEquals(-216, MainMemory.read(new Word(784)).getSigned());
    }

    @Test
    public void store1R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000111110100000000010100001", // math copy 1000 R5
                "00000010010101111000000010110101", // store R5 2398

        });
        processor.run();
        assertEquals(2398, MainMemory.read(new Word(1000)).getSigned());
    }

    // Pop/Interrupt tests
    @Test
    public void peek2R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000111111110100000010100001", // math copy 1021 R5
                "00000010010101111000000010110101", // store R5 2398
                "00000000000100000000000101011011", // peek R10 R0 2

        });
        processor.run();
        assertEquals(2398, MainMemory.read(new Word(1021)).getSigned());
        assertEquals(2398, processor.getRegisters()[10].getSigned());
    }

    @Test
    public void peek3R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000111111110100000010100001", // math copy 1021 R5
                "00000000000000001000000010000001", // math copy 2 R4
                "00000010010101111000000010110101", // store R5 2398
                "00000000000000010000000101011010", // peek R10 R0 R4

        });
        processor.run();
        assertEquals(2398, MainMemory.read(new Word(1021)).getSigned());
        assertEquals(2398, processor.getRegisters()[10].getSigned());
    }

    @Test
    public void pop1R() {
        Processor processor = new Processor();
        MainMemory.load(new String[]{
                "00000000000100110111100000001101", // push add R0 77
                "00000000000000000000000000111001", // pop R1
        });
        processor.run();
        assertEquals(77, MainMemory.read(new Word(1023)).getSigned());
        assertEquals(1023, processor.getStackPointer().getSigned());
        assertEquals(77, processor.getRegisters()[1].getSigned());
    }
}