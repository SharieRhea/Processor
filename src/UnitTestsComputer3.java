import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestsComputer3 {

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
                "00000000000000010111100000100001", // math copy 5 R1
                "00000000000000000111100001000001", // math copy 1 R2
                "00000001000100000100110000000110", // branch ge R2 R1 R2 1
                "00000000000000000111100001000011", // math add R1 R2
        });
        processor.run();
        assertEquals(6, processor.getRegisters()[2].getUnsigned());
    }
}
