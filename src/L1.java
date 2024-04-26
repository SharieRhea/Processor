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

        // determine the start address that this address falls in
        int start = (int) (address.getUnsigned() - (address.getUnsigned() % 8));
        startAddress = new Word(start);

        Word fillMemory = new Word();
        fillMemory.copy(startAddress);

        for (int i = 0; i < words.length; i++) {
            if (words[i] == null)
                words[i] = new Word();
            words[i].copy(MainMemory.read(fillMemory));
            fillMemory.increment();
        }
    }

    public static void setStartAddress(Word startAddress) {
        if (L1.startAddress == null)
            L1.startAddress = new Word();
        L1.startAddress.copy(startAddress);
    }

    public static void setWords(Word[] words) {
        for (int i = 0; i < words.length; i++) {
            if (L1.words[i] == null)
                L1.words[i] = new Word();
            L1.words[i].copy(words[i]);
        }
    }
}