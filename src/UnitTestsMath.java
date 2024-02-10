import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestsMath {

    @Test
    public void add2_000() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(), new Bit(), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
    }

    @Test
    public void add2_001() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(), new Bit(), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
    }

    @Test
    public void add2_010() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(), new Bit(true), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
    }

    @Test
    public void add2_011() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(), new Bit(true), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
    }

    @Test
    public void add2_100() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(true), new Bit(), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
    }

    @Test
    public void add2_101() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(true), new Bit(), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
    }

    @Test
    public void add2_110() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(true), new Bit(true), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
    }

    @Test
    public void add2_111() {
        ALU alu = new ALU();
        var sum = alu.add2(new Bit(true), new Bit(true), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
    }

    @Test
    public void add4_00000() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(), new Bit(), new Bit(), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_00001() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(false), new Bit(), new Bit(), new Bit(), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_00010() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(), new Bit(), new Bit(true), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_00011() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(), new Bit(), new Bit(true), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_00100() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(), new Bit(true), new Bit(), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_00101() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(), new Bit(true), new Bit(), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_00110() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(), new Bit(true), new Bit(true), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_00111() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(), new Bit(true), new Bit(true), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01000() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(), new Bit(), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01001() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(), new Bit(), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01010() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(), new Bit(true), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01011() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(), new Bit(true), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01100() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(true), new Bit(), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01101() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(true), new Bit(), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01110() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(true), new Bit(true), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_01111() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(), new Bit(true), new Bit(true), new Bit(true), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(true, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10000() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(), new Bit(), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10001() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(), new Bit(), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10010() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(), new Bit(true), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10011() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(), new Bit(true), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10100() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(true), new Bit(), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10101() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(true), new Bit(), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10110() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(true), new Bit(true), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_10111() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(), new Bit(true), new Bit(true), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(true, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11000() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(), new Bit(), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11001() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(), new Bit(), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11010() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(), new Bit(true), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11011() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(), new Bit(true), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(true, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11100() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(true), new Bit(), new Bit());
        assertEquals(true, sum.getValue());
        assertEquals(true, alu.getCarryOut1().getValue());
        assertEquals(false, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11101() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(true), new Bit(), new Bit(true));
        assertEquals(false, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(true, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11110() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(true), new Bit(true), new Bit());
        assertEquals(false, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(true, alu.getCarryOut2().getValue());
    }

    @Test
    public void add4_11111() {
        ALU alu = new ALU();
        var sum = alu.add4(new Bit(true), new Bit(true), new Bit(true), new Bit(true), new Bit(true));
        assertEquals(true, sum.getValue());
        assertEquals(false, alu.getCarryOut1().getValue());
        assertEquals(true, alu.getCarryOut2().getValue());
    }
}
