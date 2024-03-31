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
}
