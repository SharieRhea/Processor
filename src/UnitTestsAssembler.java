import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class UnitTestsAssembler {

    @Test
    public void test() throws Exception {
        String contents = new String(Files.readAllBytes(Paths.get("test.txt")));
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        System.out.println(parser.parse());
    }
}
