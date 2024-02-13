public class ALU {
    public Word operand1;
    public Word operand2;
    public Word result;
    private Bit carryOut1;
    private Bit carryOut2;

    public ALU() {
        operand1 = new Word();
        operand2 = new Word();
        result = new Word();
        carryOut1 = new Bit();
        carryOut2 = new Bit();
    }

    public void doOperation(Bit[] operation) {
        switch (getOperation(operation)) {
            case AND -> result.copy(operand1.and(operand2));
            case OR -> result.copy(operand1.or(operand2));
            case XOR -> result.copy(operand1.xor(operand2));
            case NOT -> result.copy(operand1.not());
            case LEFT_SHIFT -> result.copy(operand1.leftShift(get5BitUnsigned(operand2)));
            case RIGHT_SHIFT -> result.copy(operand1.rightShift(get5BitUnsigned(operand2)));
            case ADD -> result.copy(add2Words(operand1, operand2));
            case SUBTRACT -> result.copy(subtract(operand1, operand2));
            case MULTIPLY -> result.copy(multiply(operand1, operand2));
        }
    }

    public Bit add2(Bit bit1, Bit bit2, Bit carryIn) {
        // Sum = X xor Y xor CarryOut
        var sum = bit1.xor(bit2).xor(carryIn);
        // CarryOut = X and Y or ((X xor Y) and CarryIn)
        carryOut1 = bit1.and(bit2).or((bit1.xor(bit2)).and(carryIn));
        return sum;
    }

    public Bit add4(Bit bit1, Bit bit2, Bit bit3, Bit bit4, Bit carryIn) {
        // xor each bit to find the sum
        var sum = bit1.xor(bit2).xor(bit3).xor(bit4).xor(carryIn);
        // For a graphical representation of the logic behind finding carry values, see https://drive.google.com/file/d/1P2lRv29yRQq-qQhy7j8viq3t04ESPN9T/view?usp=sharing
        carryOut1 = ((bit1.xor(bit2)).and(bit3)).xor(bit1.and(bit2)).xor(bit1.xor(bit2).xor(bit3).and(bit4)).xor((bit1.xor(bit2).xor(bit3).xor(bit4)).and(carryIn));
        carryOut2 = ((bit1.xor(bit2)).and(bit3)).xor(bit1.and(bit2)).and(bit1.xor(bit2).xor(bit3).and(bit4)).or(((bit1.xor(bit2).xor(bit3).xor(bit4)).and(carryIn)).and(((bit1.xor(bit2)).and(bit3)).xor(bit1.and(bit2)).xor(bit1.xor(bit2).xor(bit3).and(bit4))));
        return sum;
    }

    private Word add2Words(Word operand1, Word operand2) {
        // Reset carryOut to ensure it's empty
        carryOut1 = new Bit();
        Word returnValue = new Word();
        for (int i = 31; i >= 0; i--) {
            returnValue.setBit(i, add2(operand1.getBit(i), operand2.getBit(i), carryOut1));
        }
        return returnValue;
    }

    private Word add4Words(Word operand1, Word operand2, Word operand3, Word operand4) {
        // Reset carryOut to ensure it's empty
        carryOut1 = new Bit();
        carryOut2 = new Bit();
        Word returnValue = new Word();
        for (int i = 31; i >= 0; i--) {
            // Hold onto the carry2 for adding later
            Bit storeCarry = carryOut2;

            // Do the addition
            returnValue.setBit(i, add4(operand1.getBit(i), operand2.getBit(i), operand3.getBit(i), operand4.getBit(i), carryOut1));

            // Move each carry over to prepare for next step, adding in any carry from previous steps if needed
            Bit finalCarry1 = add2(carryOut1, storeCarry, new Bit());
            Bit finalCarry2 = add2(carryOut2, carryOut1, new Bit());
            carryOut1 = finalCarry1;
            carryOut2 = finalCarry2;
        }
        return returnValue;
    }

    private Word subtract(Word operand1, Word operand2) {
        // Reset carry
        carryOut1 = new Bit();
        // convert operand2 to be negative using two's complement
        Word one = new Word();
        one.setBit(31, new Bit(true));
        Word negativeOperand2 = add2Words(operand2.not(), one);

        // a - b = a + -b
        return add2Words(operand1, negativeOperand2);
    }

    private Word multiply(Word operand1, Word operand2) {
        Word[] round1Results = new Word[8];
        // Round 1: use shift and bit values to determine the 32 numbers to add together, summing 4 at a time as we go
        for (int i = 31; i >= 0; i -= 4) {
            Word number1 = operand1.getBit(i).getValue() ? operand2.leftShift(31 - i) : new Word();
            Word number2 = operand1.getBit(i - 1).getValue() ? operand2.leftShift(31 - i + 1) : new Word();
            Word number3 = operand1.getBit(i - 2).getValue() ? operand2.leftShift(31 - i + 2) : new Word();
            Word number4 = operand1.getBit(i - 3).getValue() ? operand2.leftShift(31 - i + 3) : new Word();

            round1Results[((i + 1) / 4) - 1] = add4Words(number1, number2, number3, number4);
        }

        Word round2One = add4Words(round1Results[0], round1Results[1], round1Results[2], round1Results[3]);
        Word round2Two = add4Words(round1Results[4], round1Results[5], round1Results[6], round1Results[7]);

        return add2Words(round2One, round2Two);
    }

    // Used for shifting, only account for 5 bits as 2^5 = 32, the length of our word
    private int get5BitUnsigned(Word operand) {
        int returnValue = 0;
        for (int i = 27; i < 32; i++) {
            if (operand.getBit(i).getValue())
                returnValue += (int) Math.pow(2, 32 - 1 - i);
        }
        return returnValue;
    }

    private Operation getOperation(Bit[] operation) {
        if (operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue())
            return Operation.AND;
        else if (operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && operation[3].getValue())
            return Operation.OR;
        else if (operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && !operation[3].getValue())
            return Operation.XOR;
        else if (operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && operation[3].getValue())
            return Operation.NOT;
        else if (operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue())
            return Operation.LEFT_SHIFT;
        else if (operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && operation[3].getValue())
            return Operation.RIGHT_SHIFT;
        else if (operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && !operation[3].getValue())
            return Operation.ADD;
        else if (operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && operation[3].getValue())
            return Operation.SUBTRACT;
        else
            return Operation.MULTIPLY;
    }

    // Used for testing
    public Bit getCarryOut1() {
        return carryOut1;
    }

    // Used for testing
    public Bit getCarryOut2() {
        return carryOut2;
    }

    private enum Operation {
        AND, OR, XOR, NOT, LEFT_SHIFT, RIGHT_SHIFT, ADD, SUBTRACT, MULTIPLY
    }
}
