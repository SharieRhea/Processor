import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestsComputer2 {

    @Test
    public void write5inR0() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000010111100000000001", // math addimm 5 R0
                "00000000000000000000000000000000"  // halt
        });
        processor.run();
        // Do not allow writes over R0
        assertEquals(0, processor.getRegisters()[0].getUnsigned());
    }

    @Test
    public void generate25inR3_testAdd() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000010111100000100001", // math addimm 5 R1
                "00000000000010000111100001000011", // math add R1 R1 R2
                "00000000000000001011100001000010", // math add R2 R2
                "00000000000100000111100001100011", // math add R2 R1 R3
                // halt
        });
        processor.run();
        assertEquals(25, processor.getRegisters()[3].getUnsigned());
    }

    @Test
    public void generate512inR6_testMultiply() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000001011100001000001", // math addimm 2 R2
                "00000000000000011011100001100001", // math addimm 6 R3
                "00000000000100001111100010000011", // math add R2 R3 R4
                "00000000001000010001110010100011", // math mult R4 R4 R5
                "00000000001000010101110011000011", // math mult R4 R5 R6
                // halt

        });
        processor.run();
        assertEquals(512, processor.getRegisters()[6].getUnsigned());
    }

    @Test
    public void generate13inR13_testSubtract() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000110010001110110100001", // math addimm 100 R13
                "00000000000100011001111111000001", // math addimm 70 R30
                "00000000011011111011110110100011", // math sub R13 R30 R13
                "00000000000001000101111111100001", // math addimm 17 R31
                "00000000011011111111110110100011", // math sub R13 R31 R13
                // halt
        });
        processor.run();
        assertEquals(13, processor.getRegisters()[13].getUnsigned());
    }

    @Test
    public void generate41045inR20_testAnd() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "01111101000101010111100101000001", // math addimm 128085 R10
                "00101010001111011111100000100001", // math addimm 43255 R1
                "00000000000010101010001010000011", // math and R1 R10 R20
                // halt
        });
        processor.run();
        assertEquals(41045, processor.getRegisters()[20].getUnsigned());
    }

    @Test
    public void generate130295inR20_testOr() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "01111101000101010111100101000001", // math addimm 128085 R10
                "00101010001111011111100000100001", // math addimm 43255 R1
                "00000000000010101010011010000011", // math or R1 R10 R20
                // halt
        });
        processor.run();
        assertEquals(130295, processor.getRegisters()[20].getUnsigned());
    }

    @Test
    public void generate89250inR20_testXor() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "01111101000101010111100101000001", // math addimm 128085 R10
                "00101010001111011111100000100001", // math addimm 43255 R1
                "00000000000010101010101010000011", // math xor R1 R10 R20
                // halt
        });
        processor.run();
        assertEquals(89250, processor.getRegisters()[20].getUnsigned());
    }

    @Test
    public void generateNeg128086inR20_testNot() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "01111101000101010111101010000001", // math addimm 128085 R20
                "00000000000000000010111010000010", // math not R20
                // halt
        });
        processor.run();
        assertEquals(-128086, processor.getRegisters()[20].getSigned());
    }

    @Test
    public void generate1024680inR1_testLeftShift() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "01111101000101010111101010000001", // math addimm 128085 R20
                "00000000000000001111100001000001", // math addimm 3 R2
                "00000000101000001011000000100011", // math lshift R20 R2 R1
                // halt
        });
        processor.run();
        assertEquals(1024680, processor.getRegisters()[1].getUnsigned());
    }

    @Test
    public void generate16010inR1_testRightShift() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "01111101000101010111101010000001", // math addimm 128085 R20
                "00000000000000001111100001000001", // math addimm 3 R2
                "00000000101000001011010000100011", // math rshift R20 R2 R1
                // halt
        });
        processor.run();
        assertEquals(16010, processor.getRegisters()[1].getUnsigned());
    }
}
