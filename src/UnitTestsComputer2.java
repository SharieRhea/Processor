import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestsComputer2 {

    @Test
    public void generate25inR3() {
        Processor processor = new Processor();
        MainMemory.load(new String[] {
                "00000000000000010111100000100001", // MATH DestOnly 5 R1
                "00000000000010000111100001000011", // MATH ADD R1 R1 R2
                "00000000000000001011100001000010", // MATH ADD R2 R2
                "00000000000100000111100001100011", // MATH ADD R2 R1 R3
                "00000000000000000000000000000000"  // HALT
        });
        processor.run();
        assertEquals(25, processor.getRegisters()[3].getUnsigned());
    }
}
