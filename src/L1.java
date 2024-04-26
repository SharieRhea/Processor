public class L1 {
    private static Word startAddress;
    private static Word[] words = new Word[8];

    public static Word read(Word address) {
        // filling cache for the first time
        if (startAddress == null)
            L2.readAndFill(address);

        int offset = (int) (address.getUnsigned() - startAddress.getUnsigned());
        if (offset >= 0 && offset < 8) {
            // cache hit!
            Processor.addClockCycles(10);
            return words[offset];
        }
        // cache miss
        return L2.readAndFill(address);
    }

    private static void fillCache(Word address) {
        Processor.addClockCycles(350);

        // if cache won't be filled completely because we are close to the end
        // of memory, back up to fully utilize cache
        startAddress = address;
        Bit endOfMemory = new Bit(true);
        for (int i = 0; i < 29; i++) {
            if (!address.getBit(i).getValue())
                endOfMemory.set(false);
        }
        if (endOfMemory.getValue()) {
            startAddress.setBit(29, new Bit(false));
            startAddress.setBit(30, new Bit(false));
            startAddress.setBit(31, new Bit(false));
        }
        Word fillMemory = new Word();
        fillMemory.copy(startAddress);

        for (int i = 0; i < words.length; i++) {
            words[i] = MainMemory.read(fillMemory);
            fillMemory.increment();
        }
    }

    public static void setStartAddress(Word startAddress) {
        L1.startAddress = startAddress;
    }

    public static void setWords(Word[] words) {
        L1.words = words;
    }
}

/*if (address.getBit(29).getValue() && blockAddress.getBit(29).getValue()) {
            // 1xx 1xx
            if (address.getBit(30).getValue() && blockAddress.getBit(30).getValue()) {
                // 11x 11x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 0; // 111 111
                else if (address.getBit(31).getValue())
                    return 1; // 111 110
                else if (blockAddress.getBit(31).getValue())
                    return -1; // 110 111
                else
                    return 0; // 110 110
            }
            else if (address.getBit(30).getValue()) {
                // 11x 10x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 2; // 111 101
                else if (address.getBit(31).getValue())
                    return 3; // 111 100
                else if (blockAddress.getBit(31).getValue())
                    return 1; // 110 101
                else
                    return 2; // 110 100
            }
            else if (blockAddress.getBit(30).getValue()) {
                // 10x 11x
                return -1;
            }
            else {
                // 10x 10x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 0; // 101 101
                else if (address.getBit(31).getValue())
                    return 1; // 101 100
                else if (blockAddress.getBit(31).getValue())
                    return -1; // 100 101
                else
                    return 0; // 100 100
            }
        }
        else if (address.getBit(29).getValue()) {
            // 1xx 0xx
            if (address.getBit(30).getValue() && blockAddress.getBit(30).getValue()) {
                // 11x 01x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 4; // 111 011
                else if (address.getBit(31).getValue())
                    return 5; // 111 010
                else if (blockAddress.getBit(31).getValue())
                    return 3; // 110 011
                else
                    return 4; // 110 010
            }
            else if (address.getBit(30).getValue()) {
                // 11x 00x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 6; // 111 001
                else if (address.getBit(31).getValue())
                    return 7; // 111 000
                else if (blockAddress.getBit(31).getValue())
                    return 5; // 110 001
                else
                    return 6; // 110 000
            }
            else if (blockAddress.getBit(30).getValue()) {
                // 10x 01x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 2; // 101 011
                else if (address.getBit(31).getValue())
                    return 3; // 101 010
                else if (blockAddress.getBit(31).getValue())
                    return 1; // 100 011
                else
                    return 2; // 100 010
            }
            else {
                // 10x 00x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 4; // 101 001
                else if (address.getBit(31).getValue())
                    return 5; // 101 000
                else if (blockAddress.getBit(31).getValue())
                    return 3; // 100 001
                else
                    return 4; // 100 000
            }
        }
        else if (blockAddress.getBit(29).getValue()) {
            // 0xx 1xx
           return -1;
        }
        else {
            // 0xx 0xx
            if (address.getBit(30).getValue() && blockAddress.getBit(30).getValue()) {
                // 01x 01x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 0; // 011 011
                else if (address.getBit(31).getValue())
                    return 1; // 011 010
                else if (blockAddress.getBit(31).getValue())
                    return -1; // 010 011
                else
                    return 0; // 010 010
            }
            else if (address.getBit(30).getValue()) {
                // 01x 00x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 2; // 011 001
                else if (address.getBit(31).getValue())
                    return 3; // 011 000
                else if (blockAddress.getBit(31).getValue())
                    return 1; // 010 001
                else
                    return 2; // 010 000
            }
            else if (blockAddress.getBit(30).getValue()) {
                // 00x 01x
                return -1;
            }
            else {
                // 00x 00x
                if (address.getBit(31).getValue() && blockAddress.getBit(31).getValue())
                    return 0; // 001 001
                else if (address.getBit(31).getValue())
                    return 1; // 001 000
                else if (blockAddress.getBit(31).getValue())
                    return -1; // 000 001
                else
                    return 0; // 000 000
            }
        }
    }*/
