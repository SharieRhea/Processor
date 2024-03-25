import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestsComputer3 {

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
}
