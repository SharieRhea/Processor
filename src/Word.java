public class Word {
    private Bit[] array;

    public Word() {
        array = new Bit[32];
    }

    public Bit getBit(int index) {
        return new Bit(array[index].getValue());
    }

    public void setBit(int index, Bit bit) {
        array[index].set(bit.getValue());
    }

    public Word and(Word other) {
        Word returnValue = new Word();
        for (int i = 0; i < 32; i++) {
            returnValue.setBit(i, array[i].and(other.getBit(i)));
        }
        return returnValue;
    }

    public Word or(Word other) {
        Word returnValue = new Word();
        for (int i = 0; i < 32; i++) {
            returnValue.setBit(i, array[i].or(other.getBit(i)));
        }
        return returnValue;
    }

    public Word xor(Word other) {
        Word returnValue = new Word();
        for (int i = 0; i < 32; i++) {
            returnValue.setBit(i, array[i].xor(other.getBit(i)));
        }
        return returnValue;
    }

    public Word not() {
        Word returnValue = new Word();
        for (int i = 0; i < 32; i++) {
            returnValue.setBit(i, array[i].not());
        }
        return returnValue;
    }

    public Word rightShift(int amount) {
        Word returnValue = new Word();
        for (int i = 0; i < amount; i ++) {
            returnValue.setBit(i, new Bit(false));
        }
        for (int i = amount; i < 32; i++) {
            returnValue.setBit(i, array[i - amount]);
        }
        return returnValue;
    }

    public Word leftShift(int amount) {
        Word returnValue = new Word();
        for (int i = 32 - amount; i < 32; i ++) {
            returnValue.setBit(i, new Bit(false));
        }
        for (int i = 0; i < amount; i++) {
            returnValue.setBit(i, array[i + amount]);
        }
        return returnValue;
    }

    // Used for testing.
    public long getUnsigned() {
        long returnValue = 0;
        for (int i = 0; i < 32; i++) {
            if (array[i].getValue())
                returnValue += (long) Math.pow(2, 32 - 1 - i);
        }
        return returnValue;
    }

    // Used for testing.
    public int getSigned() {
         int returnValue = 0;
         boolean negative = false;
         // Negative number
         if (array[0].getValue()) {
             Word temporary = this.not();
             negative = true;
         }
         for (int i = 0; i < 32; i++) {
             if (array[i].getValue())
                 returnValue += (int) Math.pow(2, 32 - 1 - i);
         }
         if (negative)
             return (returnValue + 1) * -1;
         return returnValue;
    }

    public void copy(Word other) {
        for (int i = 0; i < 32; i++) {
            array[i] = other.getBit(i);
        }
    }

    // Used for testing.
    public void set(int value) {
        for (int i = 31; i >= 0; i--) {
            if (value / (int) Math.pow(2, i) == 1) {
                value -= (int) Math.pow(2, i);
                array[31 - i] = new Bit(true);
            }
            else {
                array[31 - i] = new Bit(false);
            }
        }
    }

    @Override
    public String toString() {
        String[] bits = new String[32];
        for (int i = 0; i < 32; i ++) {
            bits[i] = array[i].toString();
        }
        return String.join(", ", bits);
    }
}
