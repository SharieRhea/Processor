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

    // Add tests

    @Test
    public void add_neg2147483647_2147483647() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(-2147483647);
        alu.operand2.set(2147483647);
        alu.doOperation(addOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void add_neg2147483647_63() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(-2147483647);
        alu.operand2.set(63);
        alu.doOperation(addOperation);

        assertEquals(-2147483584, alu.result.getSigned());
    }

    @Test
    public void add_neg580458384_neg29834756() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(-580458384);
        alu.operand2.set(29834756);
        alu.doOperation(addOperation);

        assertEquals(-550623628, alu.result.getSigned());
    }

    @Test
    public void add_neg13_neg22() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(-13);
        alu.operand2.set(-22);
        alu.doOperation(addOperation);

        assertEquals(-35, alu.result.getSigned());
    }

    @Test
    public void add_neg1_0() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(-1);
        alu.operand2.set(0);
        alu.doOperation(addOperation);

        assertEquals(-1, alu.result.getSigned());
    }

    @Test
    public void add_0_0() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(0);
        alu.operand2.set(0);
        alu.doOperation(addOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void add_0_13() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(0);
        alu.operand2.set(13);
        alu.doOperation(addOperation);

        assertEquals(13, alu.result.getSigned());
    }

    @Test
    public void add_7_13() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(7);
        alu.operand2.set(13);
        alu.doOperation(addOperation);

        assertEquals(20, alu.result.getSigned());
    }

    @Test
    public void add_34587_12376() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(34587);
        alu.operand2.set(12376);
        alu.doOperation(addOperation);

        assertEquals(46963, alu.result.getSigned());
    }

    @Test
    public void add_2147483647_1() {
        ALU alu = new ALU();
        Bit[] addOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit()};
        alu.operand1.set(2147483647);
        alu.operand2.set(1);
        alu.doOperation(addOperation);

        // Overflow expected
        assertEquals(-2147483648, alu.result.getSigned());
    }

    // Subtract tests

    @Test
    public void subtract_0_0() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(0);
        alu.operand2.set(0);
        alu.doOperation(subtractOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void subtract_10_0() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(10);
        alu.operand2.set(0);
        alu.doOperation(subtractOperation);

        assertEquals(10, alu.result.getSigned());
    }

    @Test
    public void subtract_20_3() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(20);
        alu.operand2.set(3);
        alu.doOperation(subtractOperation);

        assertEquals(17, alu.result.getSigned());
    }

    @Test
    public void subtract_2_1() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(2);
        alu.operand2.set(1);
        alu.doOperation(subtractOperation);

        assertEquals(1, alu.result.getSigned());
    }

    @Test
    public void subtract_0_234213() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(0);
        alu.operand2.set(234213);
        alu.doOperation(subtractOperation);

        assertEquals(-234213, alu.result.getSigned());
    }

    @Test
    public void subtract_neg18723647_18324871() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(-18723647);
        alu.operand2.set(18324871);
        alu.doOperation(subtractOperation);

        assertEquals(-37048518, alu.result.getSigned());
    }

    @Test
    public void subtract_18723647_neg18324871() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(18723647);
        alu.operand2.set(-18324871);
        alu.doOperation(subtractOperation);

        assertEquals(37048518, alu.result.getSigned());
    }

    @Test
    public void subtract_neg2147483648_1() {
        ALU alu = new ALU();
        Bit[] subtractOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(-2147483648);
        alu.operand2.set(1);
        alu.doOperation(subtractOperation);

        // Overflow expected
        assertEquals(2147483647, alu.result.getSigned());
    }

    // Multiply tests

    @Test
    public void multiply_2_4() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(2);
        alu.operand2.set(4);
        alu.doOperation(multiplyOperation);

        assertEquals(8, alu.result.getSigned());
    }

    @Test
    public void multiply_32_64() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(32);
        alu.operand2.set(64);
        alu.doOperation(multiplyOperation);

        assertEquals(2048, alu.result.getSigned());
    }

    @Test
    public void multiply_14221746_151() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(14221746);
        alu.operand2.set(151);
        alu.doOperation(multiplyOperation);

        assertEquals(2147483646, alu.result.getSigned());
    }

    @Test
    public void multiply_14221746_152() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(14221746);
        alu.operand2.set(152);
        alu.doOperation(multiplyOperation);

        // Overflow expected
        assertEquals(-2133261904, alu.result.getSigned());
    }

    @Test
    public void multiply_neg1_3029485() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(-1);
        alu.operand2.set(3029485);
        alu.doOperation(multiplyOperation);

        assertEquals(-3029485, alu.result.getSigned());
    }

    @Test
    public void multiply_0_291932874() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(0);
        alu.operand2.set(291932874);
        alu.doOperation(multiplyOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void multiply_123_456() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(123);
        alu.operand2.set(456);
        alu.doOperation(multiplyOperation);

        assertEquals(56088, alu.result.getSigned());
    }

    @Test
    public void multiply_32_neg64() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(32);
        alu.operand2.set(-64);
        alu.doOperation(multiplyOperation);

        assertEquals(-2048, alu.result.getSigned());
    }

    @Test
    public void multiply_neg32_neg64() {
        ALU alu = new ALU();
        Bit[] multiplyOperation = new Bit[]{new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        alu.operand1.set(-32);
        alu.operand2.set(-64);
        alu.doOperation(multiplyOperation);

        assertEquals(2048, alu.result.getSigned());
    }

    // Shift tests

    @Test
    public void leftShift_1_3() {
        ALU alu = new ALU();
        Bit[] leftShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit()};
        alu.operand1.set(1);
        alu.operand2.set(3);
        alu.doOperation(leftShiftOperation);

        assertEquals(8, alu.result.getSigned());
    }

    @Test
    public void leftShift_23_3() {
        ALU alu = new ALU();
        Bit[] leftShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit()};
        alu.operand1.set(23);
        alu.operand2.set(3);
        alu.doOperation(leftShiftOperation);

        assertEquals(184, alu.result.getSigned());
    }

    @Test
    public void leftShift_neg23_3() {
        ALU alu = new ALU();
        Bit[] leftShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit()};
        alu.operand1.set(-23);
        alu.operand2.set(3);
        alu.doOperation(leftShiftOperation);

        assertEquals(-184, alu.result.getSigned());
    }

    @Test
    public void leftShift_1298457_20() {
        ALU alu = new ALU();
        Bit[] leftShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit()};
        alu.operand1.set(129);
        alu.operand2.set(20);
        alu.doOperation(leftShiftOperation);

        assertEquals(135266304, alu.result.getSigned());
    }

    @Test
    public void leftShift_0_2() {
        ALU alu = new ALU();
        Bit[] leftShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit()};
        alu.operand1.set(0);
        alu.operand2.set(2);
        alu.doOperation(leftShiftOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void leftShift_2048_20() {
        ALU alu = new ALU();
        Bit[] leftShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit()};
        alu.operand1.set(2048);
        alu.operand2.set(20);
        alu.doOperation(leftShiftOperation);

        // Overflow expected
        assertEquals(-2147483648, alu.result.getSigned());
    }

    @Test
    public void leftShift_neg13_5() {
        ALU alu = new ALU();
        Bit[] leftShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit()};
        alu.operand1.set(-13);
        alu.operand2.set(5);
        alu.doOperation(leftShiftOperation);

        assertEquals(-416, alu.result.getSigned());
    }

    @Test
    public void rightShift_1_3() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(1);
        alu.operand2.set(3);
        alu.doOperation(rightShiftOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void rightShift_23_3() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(23);
        alu.operand2.set(3);
        alu.doOperation(rightShiftOperation);

        assertEquals(2, alu.result.getSigned());
    }

    @Test
    public void rightShift_1298457_20() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(129);
        alu.operand2.set(20);
        alu.doOperation(rightShiftOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void rightShift_0_2() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(0);
        alu.operand2.set(2);
        alu.doOperation(rightShiftOperation);

        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void rightShift_2048_20() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(2048);
        alu.operand2.set(20);
        alu.doOperation(rightShiftOperation);

        // Overflow expected
        assertEquals(0, alu.result.getSigned());
    }

    @Test
    public void rightShift_neg13_5() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(-13);
        alu.operand2.set(5);
        alu.doOperation(rightShiftOperation);

        // Logical shift, not arithmetic, so sign is not preserved
        assertEquals(134217727, alu.result.getSigned());
    }

    @Test
    public void rightShift_8_3() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(8);
        alu.operand2.set(3);
        alu.doOperation(rightShiftOperation);

        assertEquals(1, alu.result.getSigned());
    }

    @Test
    public void rightShift_91827582_31() {
        ALU alu = new ALU();
        Bit[] rightShiftOperation = new Bit[]{new Bit(true), new Bit(true), new Bit(), new Bit(true)};
        alu.operand1.set(91827582);
        alu.operand2.set(31);
        alu.doOperation(rightShiftOperation);

        assertEquals(0, alu.result.getSigned());
    }
}
