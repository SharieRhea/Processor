import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class UnitTestsCache {

    @Test
    public void sumNumbersArray() throws Exception {
        String contents = new String(Files.readAllBytes(Paths.get("sumNumbersArray.asm")));
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());

        Processor processor = new Processor();
        MainMemory.load(parser.parse().toArray(new String[0]));
        populateArrayValues();
        processor.run();
        assertEquals(508, processor.getRegisters()[4].getUnsigned());
    }

    @Test
    public void sumNumbersLinkedList() throws Exception {
        String contents = new String(Files.readAllBytes(Paths.get("sumNumbersLinkedList.asm")));
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());

        Processor processor = new Processor();
        MainMemory.load(parser.parse().toArray(new String[0]));
        populateLinkedListValues();
        processor.run();
        assertEquals(508, processor.getRegisters()[3].getSigned());
    }

    @Test
    public void sumNumbersArrayBackwards() throws Exception {
        String contents = new String(Files.readAllBytes(Paths.get("sumNumbersArrayBackwards.asm")));
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());

        Processor processor = new Processor();
        MainMemory.load(parser.parse().toArray(new String[0]));
        populateArrayValues();
        processor.run();
        assertEquals(508, processor.getRegisters()[4].getUnsigned());
    }

    @Test
    public void factorial() throws Exception {
        String contents = new String(Files.readAllBytes(Paths.get("factorial.asm")));
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        Processor processor = new Processor();
        MainMemory.load(parser.parse().toArray(new String[0]));
        processor.run();
        assertEquals(5040, processor.getRegisters()[2].getUnsigned());
    }

    @Test
    public void fibonacci() throws Exception {
        String contents = new String(Files.readAllBytes(Paths.get("fibonacci.asm")));
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        Processor processor = new Processor();
        MainMemory.load(parser.parse().toArray(new String[0]));
        processor.run();
        assertEquals(987, processor.getRegisters()[5].getUnsigned());
    }

    private void populateArrayValues() {
        MainMemory.write(new Word(200), new Word(-39));
        MainMemory.write(new Word(201), new Word(-85));
        MainMemory.write(new Word(202), new Word(17));
        MainMemory.write(new Word(203), new Word(91));
        MainMemory.write(new Word(204), new Word(79));
        MainMemory.write(new Word(205), new Word(50));
        MainMemory.write(new Word(206), new Word(97));
        MainMemory.write(new Word(207), new Word(79));
        MainMemory.write(new Word(208), new Word(4));
        MainMemory.write(new Word(209), new Word(51));
        MainMemory.write(new Word(210), new Word(66));
        MainMemory.write(new Word(211), new Word(16));
        MainMemory.write(new Word(212), new Word(-5));
        MainMemory.write(new Word(213), new Word(-60));
        MainMemory.write(new Word(214), new Word(89));
        MainMemory.write(new Word(215), new Word(-33));
        MainMemory.write(new Word(216), new Word(12));
        MainMemory.write(new Word(217), new Word(100));
        MainMemory.write(new Word(218), new Word(-28));
        MainMemory.write(new Word(219), new Word(7));
    }

    private void populateLinkedListValues() {
        MainMemory.write(new Word(711), new Word(-39));
        MainMemory.write(new Word(712), new Word(209));
        MainMemory.write(new Word(209), new Word(-85));
        MainMemory.write(new Word(210), new Word(222));
        MainMemory.write(new Word(222), new Word(17));
        MainMemory.write(new Word(223), new Word(638));
        MainMemory.write(new Word(638), new Word(91));
        MainMemory.write(new Word(639), new Word(724));
        MainMemory.write(new Word(724), new Word(79));
        MainMemory.write(new Word(725), new Word(548));
        MainMemory.write(new Word(548), new Word(50));
        MainMemory.write(new Word(549), new Word(346));
        MainMemory.write(new Word(346), new Word(97));
        MainMemory.write(new Word(347), new Word(797));
        MainMemory.write(new Word(797), new Word(79));
        MainMemory.write(new Word(798), new Word(532));
        MainMemory.write(new Word(532), new Word(4));
        MainMemory.write(new Word(533), new Word(661));
        MainMemory.write(new Word(661), new Word(51));
        MainMemory.write(new Word(662), new Word(355));
        MainMemory.write(new Word(355), new Word(66));
        MainMemory.write(new Word(356), new Word(366));
        MainMemory.write(new Word(366), new Word(16));
        MainMemory.write(new Word(367), new Word(726));
        MainMemory.write(new Word(726), new Word(-5));
        MainMemory.write(new Word(727), new Word(570));
        MainMemory.write(new Word(570), new Word(-60));
        MainMemory.write(new Word(571), new Word(528));
        MainMemory.write(new Word(528), new Word(89));
        MainMemory.write(new Word(529), new Word(574));
        MainMemory.write(new Word(574), new Word(-33));
        MainMemory.write(new Word(575), new Word(384));
        MainMemory.write(new Word(384), new Word(12));
        MainMemory.write(new Word(385), new Word(299));
        MainMemory.write(new Word(299), new Word(100));
        MainMemory.write(new Word(300), new Word(265));
        MainMemory.write(new Word(265), new Word(-28));
        MainMemory.write(new Word(266), new Word(504));
        MainMemory.write(new Word(504), new Word(7));
    }
}
