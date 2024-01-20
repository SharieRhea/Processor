import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTestsBitsAndWords {

    @Test
    public void bitCreateTrue() {
        Bit bit = new Bit(true);
        assertTrue(bit.getValue());
    }

    @Test
    public void bitCreateFalse() {
        Bit bit = new Bit(false);
        assertFalse(bit.getValue());
    }

    @Test
    public void bitSetValueTrue() {
        Bit bit = new Bit(false);
        bit.set(true);
        assertTrue(bit.getValue());
    }

    @Test
    public void bitSetValueFalse() {
        Bit bit = new Bit(true);
        bit.set(false);
        assertFalse(bit.getValue());
    }

    @Test
    public void bitSet() {
        Bit bit = new Bit(false);
        bit.set();
    }

    @Test
    public void bitToggleTrue() {
        Bit bit = new Bit(true);
        bit.toggle();
        assertFalse(bit.getValue());
    }

    @Test
    public void bitToggleFalse() {
        Bit bit = new Bit(false);
        bit.toggle();
        assertTrue(bit.getValue());
    }

    @Test
    public void bitClear() {
        Bit bit = new Bit(true);
        bit.clear();
        assertFalse(false);
    }

    @Test
    public void bitAndFalseFalse() {
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(false);
        assertFalse(bit1.and(bit2).getValue());
    }

    @Test
    public void bitAndTrueFalse() {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        assertFalse(bit1.and(bit2).getValue());
    }

    @Test
    public void bitAndFalseTrue() {
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(true);
        assertFalse(bit1.and(bit2).getValue());
    }

    @Test
    public void bitAndTrueTrue() {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        assertTrue(bit1.and(bit2).getValue());
    }

    @Test
    public void bitOrFalseFalse() {
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(false);
        assertFalse(bit1.or(bit2).getValue());
    }

    @Test
    public void bitOrTrueFalse() {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        assertTrue(bit1.or(bit2).getValue());
    }

    @Test
    public void bitOrFalseTrue() {
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(true);
        assertTrue(bit1.or(bit2).getValue());
    }

    @Test
    public void bitOrTrueTrue() {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        assertTrue(bit1.or(bit2).getValue());
    }

    @Test
    public void bitXorFalseFalse() {
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(false);
        assertFalse(bit1.xor(bit2).getValue());
    }

    @Test
    public void bitXorTrueFalse() {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        assertTrue(bit1.xor(bit2).getValue());
    }

    @Test
    public void bitXorFalseTrue() {
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(true);
        assertTrue(bit1.xor(bit2).getValue());
    }

    @Test
    public void bitXorTrueTrue() {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        assertFalse(bit1.xor(bit2).getValue());
    }
    
    @Test
    public void bitNotTrue() {
        Bit bit = new Bit(true);
        assertFalse(bit.not().getValue());
    }
    
    @Test
    public void bitNotFalse() {
        Bit bit = new Bit(false);
        assertTrue(bit.not().getValue());
    }
    
    @Test
    public void bitToStringTrue() {
        Bit bit = new Bit(true);
        assertEquals("t", bit.toString());
    }

    @Test
    public void bitToStringFalse() {
        Bit bit = new Bit(false);
        assertEquals("f", bit.toString());
    }

    @Test
    public void wordGetBitFalse() {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            assertFalse(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordGetBitMixed() {
        Word word = new Word();
        word.setBit(3, new Bit(true));
        word.setBit(13, new Bit(true));

        assertFalse(word.getBit(0).getValue());
        assertTrue(word.getBit(3).getValue());
        assertFalse(word.getBit(5).getValue());
        assertTrue(word.getBit(13).getValue());
        assertFalse(word.getBit(14).getValue());
    }

    @Test
    public void wordGetBitTrue() {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true));
            assertTrue(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordSetBit() {
        Word word = new Word();
        word.setBit(0, new Bit(true));
        word.setBit(5, new Bit(true));
        word.setBit(31, new Bit(true));

        assertTrue(word.getBit(0).getValue());
        assertTrue(word.getBit(5).getValue());
        assertTrue(word.getBit(31).getValue());
    }

    @Test
    public void wordAndFalseFalse() {
        Word word1 = new Word();
        Word word2 = new Word();

        Word result = word1.and(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), false);
        }
    }

    @Test
    public void wordAndMixedTrue() {
        Word word1 = new Word();
        word1.setBit(19, new Bit(true));
        word1.setBit(23, new Bit(true));
        word1.setBit(29, new Bit(true));
        word1.setBit(30, new Bit(true));
        Word word2 = new Word();
        for (int i = 0; i < 32; i++) {
            word2.setBit(i, new Bit(true));
        }

        Word result = word1.and(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), word1.getBit(i).getValue());
        }
    }

    @Test
    public void wordAndMixedFalse() {
        Word word1 = new Word();
        word1.setBit(19, new Bit(true));
        word1.setBit(23, new Bit(true));
        word1.setBit(29, new Bit(true));
        word1.setBit(30, new Bit(true));
        Word word2 = new Word();

        Word result = word1.and(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), false);
        }
    }

    @Test
    public void wordAndTrueTrue() {
        Word word1 = new Word();
        Word word2 = new Word();
        for (int i = 0; i < 32; i++) {
            word1.setBit(i, new Bit(true));
            word2.setBit(i, new Bit(true));
        }

        Word result = word1.and(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), true);
        }
    }

    @Test
    public void wordOrFalseFalse() {
        Word word1 = new Word();
        Word word2 = new Word();

        Word result = word1.or(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), false);
        }
    }

    @Test
    public void wordOrMixedTrue() {
        Word word1 = new Word();
        word1.setBit(19, new Bit(true));
        word1.setBit(23, new Bit(true));
        word1.setBit(29, new Bit(true));
        word1.setBit(30, new Bit(true));
        Word word2 = new Word();
        for (int i = 0; i < 32; i++) {
            word2.setBit(i, new Bit(true));
        }

        Word result = word1.or(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), true);
        }
    }

    @Test
    public void wordOrMixedFalse() {
        Word word1 = new Word();
        word1.setBit(19, new Bit(true));
        word1.setBit(23, new Bit(true));
        word1.setBit(29, new Bit(true));
        word1.setBit(30, new Bit(true));
        Word word2 = new Word();

        Word result = word1.or(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), word1.getBit(i).getValue());
        }
    }

    @Test
    public void wordOrTrueTrue() {
        Word word1 = new Word();
        Word word2 = new Word();
        for (int i = 0; i < 32; i++) {
            word1.setBit(i, new Bit(true));
            word2.setBit(i, new Bit(true));
        }

        Word result = word1.or(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), true);
        }
    }

    @Test
    public void wordXorFalseFalse() {
        Word word1 = new Word();
        Word word2 = new Word();

        Word result = word1.xor(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), false);
        }
    }

    @Test
    public void wordXorMixedTrue() {
        Word word1 = new Word();
        word1.setBit(19, new Bit(true));
        word1.setBit(23, new Bit(true));
        word1.setBit(29, new Bit(true));
        word1.setBit(30, new Bit(true));
        Word word2 = new Word();
        for (int i = 0; i < 32; i++) {
            word2.setBit(i, new Bit(true));
        }

        Word result = word1.xor(word2);
        for (int i = 0; i < 32; i++) {
            assertNotEquals(result.getBit(i).getValue(), word1.getBit(i).getValue());
        }
    }

    @Test
    public void wordXorMixedFalse() {
        Word word1 = new Word();
        word1.setBit(19, new Bit(true));
        word1.setBit(23, new Bit(true));
        word1.setBit(29, new Bit(true));
        word1.setBit(30, new Bit(true));
        Word word2 = new Word();

        Word result = word1.xor(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), word1.getBit(i).getValue());
        }
    }

    @Test
    public void wordXorTrueTrue() {
        Word word1 = new Word();
        Word word2 = new Word();
        for (int i = 0; i < 32; i++) {
            word1.setBit(i, new Bit(true));
            word2.setBit(i, new Bit(true));
        }

        Word result = word1.xor(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(result.getBit(i).getValue(), false);
        }
    }

    @Test
    public void wordNotFalse() {
        Word word = new Word();
        word = word.not();
        for (int i = 0; i < 32; i++) {
            assertTrue(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordNotTrue() {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true));
        }
        word = word.not();
        for (int i = 0; i < 32; i++) {
            assertFalse(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordNotMixed() {
        Word word = new Word();
        word.setBit(0, new Bit(true));
        word.setBit(5, new Bit(true));
        word.setBit(6, new Bit(true));
        word.setBit(30, new Bit(true));
        word = word.not();
        for (int i = 0; i < 32; i++) {
            if (i == 0 || i == 5 || i == 6 || i == 30)
                assertFalse(word.getBit(i).getValue());
            else
                assertTrue(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordToStringFalse() {
        Word word = new Word();
        assertEquals("f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f", word.toString());
    }

    @Test
    public void wordToStringTrue() {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true));
        }
        assertEquals("t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t", word.toString());
    }

    @Test
    public void wordToStringMixed() {
        Word word = new Word();
        word.setBit(0, new Bit(true));
        word.setBit(5, new Bit(true));
        word.setBit(6, new Bit(true));
        word.setBit(30, new Bit(true));
        assertEquals("t, f, f, f, f, t, t, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f", word.toString());
    }

    //todo left and right shift

    @Test
    public void wordGetUnsignedMax() {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true));
        }
        assertEquals(4294967295L, word.getUnsigned());
    }

    @Test
    public void wordGetUnsignedMin() {
        Word word = new Word();
        assertEquals(0, word.getUnsigned());
    }

    @Test
    public void wordGetUnsigned33558546() {
        Word word = new Word();
        word.setBit(6, new Bit(true));
        word.setBit(19, new Bit(true));
        word.setBit(27, new Bit(true));
        word.setBit(30, new Bit(true));
        assertEquals(33558546L, word.getUnsigned());
    }

    @Test
    public void wordGetUnsigned13() {
        Word word = new Word();
        word.setBit(28, new Bit(true));
        word.setBit(29, new Bit(true));
        word.setBit(31, new Bit(true));
        assertEquals(13L, word.getUnsigned());
    }

    @Test
    public void getSignedMax() {
        Word word = new Word();
        for (int i = 1; i < 32; i++) {
            word.setBit(i, new Bit(true));
        }
        assertEquals(2147483647, word.getSigned());
    }

    @Test
    public void getSignedMin() {
        Word word = new Word();
        word.setBit(0, new Bit(true));
        assertEquals(-2147483648, word.getSigned());
    }

    @Test
    public void wordGetSigned33558546() {
        Word word = new Word();
        word.setBit(6, new Bit(true));
        word.setBit(19, new Bit(true));
        word.setBit(27, new Bit(true));
        word.setBit(30, new Bit(true));
        assertEquals(33558546, word.getSigned());
    }

    @Test
    public void wordGetSignedNeg33558546() {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true));
        }
        word.setBit(6, new Bit(false));
        word.setBit(19, new Bit(false));
        word.setBit(27, new Bit(false));
        word.setBit(31, new Bit(false));
        assertEquals(-33558546, word.getSigned());
    }

    @Test
    public void wordGetSignedNeg13() {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true));
        }
        word.setBit(28, new Bit(false));
        word.setBit(29, new Bit(false));
        assertEquals(-13, word.getSigned());
    }

    @Test
    public void wordCopyTrue() {
        Word word1 = new Word();
        Word word2 = new Word();
        for (int i = 0; i < 32; i++) {
            word2.setBit(i, new Bit(true));
        }
        word1.copy(word2);
        for (int i = 0; i < 32; i++) {
            assertTrue(word1.getBit(i).getValue());
        }
    }

    @Test
    public void wordCopyMixed() {
        Word word1 = new Word();
        Word word2 = new Word();
        word2.setBit(0, new Bit(true));
        word2.setBit(2, new Bit(true));
        word2.setBit(3, new Bit(true));
        word2.setBit(7, new Bit(true));
        word2.setBit(21, new Bit(true));
        word2.setBit(27, new Bit(true));

        word1.copy(word2);
        for (int i = 0; i < 32; i++) {
            assertEquals(word1.getBit(i).getValue(), word2.getBit(i).getValue());
        }
    }

    @Test
    public void wordSetMax() {
        Word word = new Word();
        word.set(2147483647);
        for (int i = 1; i < 32; i++) {
            assertTrue(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordSetMin() {
        Word word = new Word();
        word.set(-2147483648);
        for (int i = 0; i < 32; i++) {
            assertTrue(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordSet33558546() {
        Word word = new Word();
        word.set(33558546);
        for (int i = 0; i < 32; i++) {
            if (i == 6 || i == 19 || i == 27 || i == 30)
                assertTrue(word.getBit(i).getValue());
            else
                assertFalse(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordSetNeg33558546() {
        Word word = new Word();
        word.set(-33558546);

        for (int i = 0; i < 32; i++) {
            if (i == 6 || i == 19 || i == 27 || i == 31)
                assertFalse(word.getBit(i).getValue());
            else
                assertTrue(word.getBit(i).getValue());
        }
    }

    @Test
    public void wordSetNeg13() {
        Word word = new Word();
        word.set(-13);
        for (int i = 0; i < 32; i++) {
            if (i == 28 || i == 29)
                assertFalse(word.getBit(i).getValue());
            else
                assertTrue(word.getBit(i).getValue());
        }
    }
}
