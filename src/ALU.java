public class ALU {
    public Word operand1;
    public Word operand2;
    public Word result;
    private Bit carryOut1;
    private Bit carryOut2;

    public void doOperation(Bit[] operation) {
        // check what the operation is, helper method?
        switch (getOperation(operation)) {
            case AND ->
                result.copy(operand1.and(operand2));
            case OR ->
                result.copy(operand1.or(operand2));
            case XOR ->
                result.copy(operand1.xor(operand2));
            case NOT ->
                result.copy(operand1.not());
            // TODO
            /*case LEFT_SHIFT ->
                result.copy(operand1.leftShift()); // TODO: need to get int value of lowest 5 bits of operand2 here));
            case RIGHT_SHIFT ->
                result.copy(operand1.rightShift()); // TODO: need to get int value of lowest 5 bits of operand2 here));
            case ADD ->
                // add
            case SUBTRACT ->
                // subtract
            case MULTIPLY ->
                // multiply*/
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
        carryOut1 =    ((bit1.xor(bit2)).and(bit3)).xor(bit1.and(bit2)).xor(bit1.xor(bit2).xor(bit3).and(bit4)).xor((bit1.xor(bit2).xor(bit3).xor(bit4)).and(carryIn));
        carryOut2 = ((bit1.xor(bit2)).and(bit3)).xor(bit1.and(bit2)).and(bit1.xor(bit2).xor(bit3).and(bit4)).or(((bit1.xor(bit2).xor(bit3).xor(bit4)).and(carryIn)).and(((bit1.xor(bit2)).and(bit3)).xor(bit1.and(bit2)).xor(bit1.xor(bit2).xor(bit3).and(bit4))));
        return sum;
    }

    private enum Operation {
        AND, OR, XOR, NOT, LEFT_SHIFT, RIGHT_SHIFT, ADD, SUBTRACT, MULTIPLY
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
}
