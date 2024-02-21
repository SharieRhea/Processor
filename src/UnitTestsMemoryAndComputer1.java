import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestsMemoryAndComputer1 {

    @Test
    public void increment0() {
        Word word = new Word();
        assertEquals(1, word.increment().getUnsigned());
    }

    @Test
    public void increment13() {
        Word word = new Word();
        word.set(13);
        assertEquals(14, word.increment().getUnsigned());
    }

    @Test
    public void increment25() {
        Word word = new Word();
        word.set(25);
        assertEquals(26, word.increment().getUnsigned());
    }

    @Test
    public void increment1() {
        Word word = new Word();
        word.set(1);
        assertEquals(2, word.increment().getUnsigned());
    }

    @Test
    public void increment678() {
        Word word = new Word();
        word.set(678);
        assertEquals(679, word.increment().getUnsigned());
    }

    @Test
    public void increment333() {
        Word word = new Word();
        word.set(333);
        assertEquals(334, word.increment().getUnsigned());
    }

    @Test
    public void increment31() {
        Word word = new Word();
        word.set(31);
        assertEquals(32, word.increment().getUnsigned());
    }

    @Test
    public void increment1024() {
        Word word = new Word();
        word.set(1024);
        assertEquals(1025, word.increment().getUnsigned());
    }

    @Test
    public void loadReadBasic() {
        MainMemory.load(new String[]{"00000000000000100000000010000010", "00000000000000000000000000001011", "00100101000000000000000000000000"});
        Word address = new Word();
        assertEquals(131202, MainMemory.read(address).getUnsigned());
        address = address.increment();
        assertEquals(11, MainMemory.read(address).getUnsigned());
        address = address.increment();
        assertEquals(620756992, MainMemory.read(address).getUnsigned());
    }

    @Test
    public void loadReadWrite() {
        MainMemory.load(new String[]{"00000000000000100000000010000010", "00000000000000000000000000001011", "00100101000000000000000000000000"});
        Word address = new Word();
        assertEquals(131202, MainMemory.read(address).getUnsigned());
        Word value = new Word();
        value.set(1024);
        MainMemory.write(address, value);
        assertEquals(1024, MainMemory.read(address).getUnsigned());
    }

    @Test
    public void writeRead() {
        Word address = new Word();
        address.set(513);
        // Memory is empty if nothing has been written there yet
        assertEquals(0, MainMemory.read(address).getUnsigned());
        Word value = new Word();
        value.set(1928465);
        MainMemory.write(address, value);
        assertEquals(1928465, MainMemory.read(address).getUnsigned());
    }

    @Test
    public void writeReadMultipleTimes() {
        Word address = new Word();
        address.set(14);
        assertEquals(0, MainMemory.read(address).getUnsigned());
        Word value = new Word();
        value.set(1928465);
        MainMemory.write(address, value);
        assertEquals(1928465, MainMemory.read(address).getUnsigned());
        value.set(-127364);
        MainMemory.write(address, value);
        assertEquals(-127364, MainMemory.read(address).getSigned());
    }

    @Test
    public void memoryEmptyPastLoad() {
        MainMemory.load(new String[]{"00000000000000100000000010000010", "00000000000000000000000000001011", "00100101000000000000000000000000",
                "00000000000000100000000010000010", "00000000000000100000000010000010", "00000000000000100000000010000010", "00000000000000100000000010000010"});
        Word address = new Word();
        assertEquals(131202, MainMemory.read(address).getUnsigned());
        Word value = new Word();
        value.set(1024);
        MainMemory.write(address, value);
        assertEquals(1024, MainMemory.read(address).getUnsigned());
        address.set(7);
        // Memory after load should be unset/empty
        assertEquals(0, MainMemory.read(address).getUnsigned());
    }
}
