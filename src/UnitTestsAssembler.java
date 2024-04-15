import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UnitTestsAssembler {

    // Meaningful program tests:

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

    @Test
    public void shortLoop() throws Exception {
        String contents = """
                copy R1 -2
                copy R2 90
                copy R3 5
                copy R4 -1
                math mult R1 R2
                math add R4 R3
                branch less or equal R0 R3 -3
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());

        Processor processor = new Processor();
        MainMemory.load(parser.parse().toArray(new String[0]));
        processor.run();
        assertEquals(5760, processor.getRegisters()[2].getSigned());
    }

    @Test
    public void basicFunctionCall() throws Exception {
        String contents = """
                push 50
                push 1343
                call 5
                pop R1
                halt
                
                // start add function
                pop R31 // pop the return address off stack
                pop R1 // pop param number 1
                pop R2 // pop param 2
                push add R1 R2
                push R31
                return
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        Processor processor = new Processor();
        var statements = parser.parse().toArray(new String[0]);
        MainMemory.load(statements);
        processor.run();
        assertEquals(1393, processor.getRegisters()[1].getSigned());
    }

    @Test
    public void loadAndStore() throws Exception {
        String contents = """
                copy R1 -123
                copy R2 20
                copy R3 800
                copy R4 2
                store R3 R1 R2
                store R2 R3 1
                math add R4 R3
                store R3 92
                
                load R3 R0 R18
                load R3 R19 18
                load R3 -1
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        Processor processor = new Processor();
        var statements = parser.parse().toArray(new String[0]);
        MainMemory.load(statements);
        processor.run();
        assertEquals(92, processor.getRegisters()[18].getSigned());
        assertEquals(-123, processor.getRegisters()[19].getSigned());
        assertEquals(20, processor.getRegisters()[3].getSigned());
    }

    // Checking every instruction type from the chart in combination with all MOPs and BOPs

    @Test
    public void testMathInstructions() throws Exception {
        String contents = """
                math add R3 R2
                math mult R4 R5 R1
                math sub R9 R8
                math shift right R10 R11 R12
                math shift left R13 R14 R15
                math not R2
                math and R16 R17
                math or R18 R19
                math xor R20 R21
                
                copy R22 10
                copy R23 -856
                copy R24 108324
                halt
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        var statements = parser.parse();
        ArrayList<String> checkInstructions = new ArrayList<>();
        checkInstructions.add("00000000000000001111100001000011");
        checkInstructions.add("00000000001000010101110000100010");
        checkInstructions.add("00000000000000100111110100000011");
        checkInstructions.add("00000000010100101111010110000010");
        checkInstructions.add("00000000011010111011010111100010");
        checkInstructions.add("00000000000000000010110001000001");
        checkInstructions.add("00000000000001000010001000100011");
        checkInstructions.add("00000000000001001010011001100011");
        checkInstructions.add("00000000000001010010101010100011");
        checkInstructions.add("00000000000000101000001011000001");
        checkInstructions.add("11111111001010100000001011100001");
        checkInstructions.add("01101001110010010000001100000001");
        checkInstructions.add("00000000000000000000000000000000");
        assertEquals(checkInstructions, statements);
    }

    @Test
    public void testBranchInstructions() throws Exception {
        String contents = """
                branch equal R0 R1 14
                branch notequal R2 R3
                branch less than R20 R21
                branch greater than R22 R23
                branch greater or equal R24 R25
                branch less or equal R26 R27
                
                jump 10
                jumpto 10
                jump -10
                jumpto -10
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        var statements = parser.parse();
        ArrayList<String> checkInstructions = new ArrayList<>();
        checkInstructions.add("00000000011100000000000000100111");
        checkInstructions.add("00000000000000001000010001100111");
        checkInstructions.add("00000000000001010000101010100111");
        checkInstructions.add("00000000000001011001001011100111");
        checkInstructions.add("00000000000001100001001100100111");
        checkInstructions.add("00000000000001101001011101100111");
        checkInstructions.add("00000000000000101000000000000101");
        checkInstructions.add("00000000000000000000000101000100");
        checkInstructions.add("11111111111111011000000000000101");
        checkInstructions.add("11111111111111111111111011000100");
        assertEquals(checkInstructions, statements);
    }

    @Test
    public void testPushInstructions() throws Exception {
        String contents = """
                push add R3 R4
                push mult R4 R5
                push sub R9 100
                push shift right R10 R11
                push shift left R13 R14
                push not R2
                push and R16 R17
                push or R18 R19
                push xor R20 118
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        var statements = parser.parse();
        ArrayList<String> checkInstructions = new ArrayList<>();
        checkInstructions.add("00000000000000001111100010001111");
        checkInstructions.add("00000000000000010001110010101111");
        checkInstructions.add("00000000000110010011110100101101");
        checkInstructions.add("00000000000000101011010101101111");
        checkInstructions.add("00000000000000110111010111001111");
        checkInstructions.add("00000000000000000010110001001101");
        checkInstructions.add("00000000000001000010001000101111");
        checkInstructions.add("00000000000001001010011001101111");
        checkInstructions.add("00000000000111011010101010001101");
        assertEquals(checkInstructions, statements);
    }

    @Test
    public void testCallInstructions() throws Exception {
        String contents = """
                call equal R2 R3 90
                call notequal R4 R5 R6 -20
                call R6 88
                call 30
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        var statements = parser.parse();
        ArrayList<String> checkInstructions = new ArrayList<>();
        checkInstructions.add("00000010110100001000000001101011");
        checkInstructions.add("11101100001000010100010011001010");
        checkInstructions.add("00000000000101100000000011001001");
        checkInstructions.add("00000000000000000000001111001000");
        assertEquals(checkInstructions, statements);
    }

    @Test
    public void testLoadInstructions() throws Exception {
        String contents = """
                load R13 R1 3
                load R19 R29 R5
                load R17 -90
                return
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        var statements = parser.parse();
        ArrayList<String> checkInstructions = new ArrayList<>();
        checkInstructions.add("00000000000110110111100000110011");
        checkInstructions.add("00000000100111110111100010110010");
        checkInstructions.add("11111111111010011011101000110001");
        checkInstructions.add("00000000000000000000000000010000");
        assertEquals(checkInstructions, statements);
    }

    @Test
    public void testStoreInstructions() throws Exception {
        String contents = """
                store R28 R3 278
                store R8 R18 R25
                store R9 -13426
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        var statements = parser.parse();
        ArrayList<String> checkInstructions = new ArrayList<>();
        checkInstructions.add("00001000101101110011100001110111");
        checkInstructions.add("00000000010001001011101100110110");
        checkInstructions.add("11110010111000111011100100110101");
        assertEquals(checkInstructions, statements);
    }

    @Test
    public void testPopInterruptInstructions() throws Exception {
        String contents = """
                peek R1 R2 800
                peek R1 R2 R3
                pop R8
                """;
        Lexer lexer = new Lexer(contents);
        Parser parser = new Parser(lexer.lex());
        var statements = parser.parse();
        ArrayList<String> checkInstructions = new ArrayList<>();
        checkInstructions.add("00011001000000000100000001011011");
        checkInstructions.add("00000000000010001000000001111010");
        checkInstructions.add("00000000000000000000000100011001");
        assertEquals(checkInstructions, statements);
    }
}