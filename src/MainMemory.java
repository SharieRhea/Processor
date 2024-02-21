public class MainMemory {
    private static Word[] memory = new Word[1024];

    public static Word read(Word address) {
        int index = (int) address.getUnsigned();
        Word returnVal = new Word();
        // Memory may not have been initialized, in that case return an empty word
        if (memory[index] != null)
            returnVal.copy(memory[index]);
        return returnVal;
    }

    public static void write(Word address, Word value) {
        int index = (int) address.getUnsigned();
        // Initialize a new word if necessary (first time writing to this address)
        if (memory[index] == null)
            memory[index] = new Word();
        memory[index].copy(value);
    }

    public static void load(String[] data) {
        int address = 0;
        for (String line : data) {
            Word word = new Word();
            // Only need to update true bits
            for (int i = 0; i < 32; i++) {
                if (line.charAt(i) == '1')
                    word.setBit(i, new Bit(true));
            }
            memory[address] = word;
            address++;
        }
    }
}
