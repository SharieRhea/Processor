public class L2 {
    private static Word[] addresses = new Word[4];
    private static Word[][] groups = new Word[4][8];
    private static int[] lastAccessed = new int[4];

    public static Word read(Word address) {
        // assume a cache hit at first
        Processor.addClockCycles(50);

        for (int i = 0; i < 4; i++) {
            if (addresses[i] != null) {
                int offset = (int) (address.getUnsigned() - addresses[i].getUnsigned());
                if (offset >= 0 && offset < 8) {
                    lastAccessed[i] = Processor.getCurrentClockCycle();
                    return groups[i][offset];
                }
            }
        }

        // cache miss, remove our optimistic hit clock cycles
        Processor.addClockCycles(-50);

        // fill L2
        int group = pickGroup();
        fillGroup(address, group);

        int offset = (int) (address.getUnsigned() - addresses[group].getUnsigned());
        lastAccessed[group] = Processor.getCurrentClockCycle();
        return groups[group][offset];
    }

    public static Word readAndFill(Word address) {
        // pay 50 to fill L1
        Processor.addClockCycles(50);

        Word returnValue = null;
        for (int i = 0; i < 4; i++) {
            if (addresses[i] != null) {
                int offset = (int) (address.getUnsigned() - addresses[i].getUnsigned());
                if (offset >= 0 && offset < 8) {
                    returnValue = groups[i][offset];
                    L1.setStartAddress(addresses[i]);
                    L1.setWords(groups[i]);
                    lastAccessed[i] = Processor.getCurrentClockCycle();
                }
            }
        }

        if (returnValue != null)
            return returnValue;

        // fill L2
        int group = pickGroup();
        fillGroup(address, group);

        // fill L1
        L1.setStartAddress(addresses[group]);
        L1.setWords(groups[group]);

        int offset = (int) (address.getUnsigned() - addresses[group].getUnsigned());
        lastAccessed[group] = Processor.getCurrentClockCycle();
        return groups[group][offset];
    }

    public static void write(Word address, Word value) {
        // write-through to main memory
        MainMemory.write(address, value);
        Processor.addClockCycles(300);

        // write to cache costs 50
        Processor.addClockCycles(50);

        for (int i = 0; i < 4; i++) {
            if (addresses[i] != null) {
                int offset = (int) (address.getUnsigned() - addresses[i].getUnsigned());
                if (offset >= 0 && offset < 8) {
                    groups[i][offset].copy(value);
                    lastAccessed[i] = Processor.getCurrentClockCycle();
                    return;
                }
            }
        }
    }

    private static int pickGroup() {
        // overwrite the block that hasn't been touched for the longest time
        int group = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < lastAccessed.length; i++) {
            if (lastAccessed[i] < min) {
                min = lastAccessed[i];
                group = i;
            }
        }
        return group;
    }

    private static void fillGroup(Word address, int groupNumber) {
        Processor.addClockCycles(350);

        // if cache won't be filled completely because we are close to the end
        // of memory, back up to fully utilize cache
        if (addresses[groupNumber] == null)
            addresses[groupNumber] = new Word();
        addresses[groupNumber].copy(address);
        Bit endOfMemory = new Bit(true);
        for (int i = 22; i < 29; i++) {
            if (!address.getBit(i).getValue())
                endOfMemory.set(false);
        }
        if (endOfMemory.getValue()) {
            addresses[groupNumber].setBit(29, new Bit());
            addresses[groupNumber].setBit(30, new Bit());
            addresses[groupNumber].setBit(31, new Bit());
        }

        Word fillMemory = new Word();
        fillMemory.copy(addresses[groupNumber]);
        for (int i = 0; i < groups[groupNumber].length; i++) {
            groups[groupNumber][i] = MainMemory.read(fillMemory);
            fillMemory = fillMemory.increment();
        }

        // check to see if another block in cache has some of this data, if so,
        // get rid of it to maintain cache coherency
        for (int i = 0; i < 4; i++) {
            if (i != groupNumber && addresses[i] != null) {
                int offset = (int) (address.getUnsigned() - addresses[i].getUnsigned());
                if (offset > -8 && offset < 8) { // todo check
                    // this block contains one or more of the same addresses, need to flush it
                    addresses[i] = null;
                    lastAccessed[i] = 0;
                }
            }
        }
    }
}
