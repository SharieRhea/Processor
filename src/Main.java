import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException, Parser.SyntaxErrorException {
        String contents = new String(Files.readAllBytes(Paths.get(args[0])));
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());

        Processor processor = new Processor();
        MainMemory.load(parser.parse().toArray(new String[0]));
        processor.run();
    }
}
