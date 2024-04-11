import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class UnitTestsAssembler {

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
}
