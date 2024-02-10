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

    @Test
    public void add_neg1_1() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit() };
        alu.operand1.set(-1);
        alu.operand2.set(1);
        alu.doOperation(addOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void add_0_13() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit() };
        alu.operand1.set(0);
        alu.operand2.set(13);
        alu.doOperation(addOperation);

        assertEquals(13, alu.result.getSigned());
    }

    @Test
    public void add_7_13() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit() };
        alu.operand1.set(7);
        alu.operand2.set(13);
        alu.doOperation(addOperation);

        assertEquals(20, alu.result.getSigned());
    }

    @Test
    public void subtract_20_3() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[] { new Bit(true), new Bit(true), new Bit(true), new Bit(true) };
        alu.operand1.set(20);
        alu.operand2.set(3);
        alu.doOperation(subtractOperation);

        assertEquals(17, alu.result.getSigned());
    }

    @Test
    public void add__4_5_6() {
        ALU alu = new ALU();
        Word one = new Word(), two = new Word(), three = new Word(), four = new Word();
        one.set(17);
        two.set(888);
        three.set(64);
        four.set(1);

        var result = alu.add4Way(one, two, three, four);

        assertEquals(970, result.getSigned());
    }

    @Test
    public void multiply_123_456() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[] { new Bit(), new Bit(true), new Bit(true), new Bit(true) };
        alu.operand1.set(123);
        alu.operand2.set(456);
        alu.doOperation(multiplyOperation);

        assertEquals(56088, alu.result.getSigned());
    }
}
