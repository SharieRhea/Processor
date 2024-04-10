import java.util.*;

public class Parser {

    private static class TokenHandler {
        private final LinkedList<Token> tokens;

        public TokenHandler(LinkedList<Token> tokensList) {
            tokens = tokensList;
        }

        public Optional<Token> peek() {
            if (tokens.isEmpty())
                return Optional.empty();
            return Optional.ofNullable(tokens.get(0));
        }

        public Optional<Token> peek(int numberOfTokens) {
            if (numberOfTokens > tokens.size() - 1)
                return Optional.empty();
            return Optional.ofNullable(tokens.get(numberOfTokens));
        }

        public boolean isDone() {
            return tokens.isEmpty();
        }

        public Optional<Token> matchAndRemove(Token.TokenType type) {
            Token token = this.peek().orElse(null);
            if (token != null && token.getTokenType() == type) {
                tokens.remove();
                return Optional.of(token);
            }
            return Optional.empty();
        }

        public void remove() {
            this.peek().ifPresent(token -> tokens.remove());
        }
    }

    public static class SyntaxErrorException extends Exception {
        public SyntaxErrorException(String message, int lineNumber, int position) {
            super("%s at %d:%d".formatted(message, lineNumber, position));
        }

        public SyntaxErrorException(String message) {
            super(message);
        }
    }

    private enum InstructionFormat {
        ThreeR, TwoR, OneR, ZeroR
    }

    private final TokenHandler tokenHandler;
    private final HashMap<Token.TokenType, String> binaryMap;
    private String rs1;
    private String rs2;
    private String rd;
    private String immediate;

    public Parser(LinkedList<Token> tokens) {
        tokenHandler = new TokenHandler(tokens);
        binaryMap = new HashMap<>();
        populateBinaryMap(binaryMap);
    }

    public List<String> parse() throws SyntaxErrorException {
        ArrayList<String> statementsList = new ArrayList<>();
        parseSeparators();
        while(!tokenHandler.isDone()) {
            statementsList.add(processStatement());
            if (!parseSeparators()) {
                var nextToken = tokenHandler.peek();
                if (nextToken.isPresent())
                    throw new SyntaxErrorException("Expected separator after statement", nextToken.get().getLineNumber(), nextToken.get().getPosition());
                else
                    throw new SyntaxErrorException("Expected separator after statement by end of file");
            }
        }

        return statementsList;
    }

    private String processStatement() throws SyntaxErrorException {
        // a statement must start with a keyword (op code)
        var token = tokenHandler.peek();
        if (token.isEmpty() || token.get().getTokenType() != Token.TokenType.KEYWORD)
            throw new SyntaxErrorException("Expected keyword by end of file");

        tokenHandler.remove();
        var keyword = token.get().getKeyword();

        // Special Cases
        if (keyword == Token.TokenType.HALT)
            return "00000000000000000000000000000000";
        else if (keyword == Token.TokenType.RETURN)
            return "00000000000000000000000000010000";

        String opCode = binaryMap.get(keyword);

        return switch (keyword) {
            case COPY, JUMP, LOAD, STORE, PEEK, POP -> createInstruction(parseInstructionFormat(), "0000", opCode);
            case MATH, PUSH -> {
                String mathFunction = parseMathOperation(keyword);
                yield createInstruction(parseInstructionFormat(), mathFunction, opCode);
            }
            case BRANCH -> {
                String branchFunction = parseBooleanOperation(keyword);
                yield createInstruction(parseInstructionFormat(), branchFunction, opCode);
            }
            case CALL -> {
                token = tokenHandler.peek();
                if (token.isPresent() && token.get().getTokenType() == Token.TokenType.KEYWORD) {
                    String callFunction = parseBooleanOperation(keyword);
                    yield createInstruction(parseInstructionFormat(), callFunction, opCode);
                }
                else
                    yield createInstruction(parseInstructionFormat(), "0000", opCode);
            }
            default -> throw new SyntaxErrorException("%s is not a valid statement".formatted(token.get().getKeyword()), token.get().getLineNumber(), token.get().getPosition());
        };
    }

    private String createInstruction(InstructionFormat instructionFormat, String function, String opCode) {
        return switch (instructionFormat) {
            case ZeroR -> immediate + opCode + "00";
            case OneR -> immediate + function + rd + opCode + "01";
            case TwoR -> immediate + rs1 + function + rd + opCode + "11";
            case ThreeR -> immediate + rs1 + rs2 + function + rd + opCode + "10";
        };
    }

    private InstructionFormat parseInstructionFormat() throws SyntaxErrorException {
        LinkedList<Token> registersList = new LinkedList<>();
        Optional<Token> token;
        do {
            token = tokenHandler.matchAndRemove(Token.TokenType.REGISTER);
            token.ifPresent(registersList::add);
        } while (token.isPresent());
        token = tokenHandler.matchAndRemove(Token.TokenType.NUMBER);
        // No immediate was provided, create one as 0 for generating the instruction
        if (token.isEmpty())
            token = Optional.of(new Token(Token.TokenType.NUMBER, 0, 0, "0"));

        int numberOfRegisters = registersList.size();
        switch (numberOfRegisters) {
            case 0 -> {
                immediate = getImmediate(token.get().getValue(), 27);
                return InstructionFormat.ZeroR;
            }
            case 1 -> {
                immediate = getImmediate(token.get().getValue(), 18);
                rd = getRegister(registersList.get(0).getValue());
                return InstructionFormat.OneR;
            }
            case 2 -> {
                immediate = getImmediate(token.get().getValue(), 13);
                rs1 = getRegister(registersList.get(0).getValue());
                rd = getRegister(registersList.get(1).getValue());
                return InstructionFormat.TwoR;
            }
            case 3 -> {
                immediate = getImmediate(token.get().getValue(), 8);
                rs1 = getRegister(registersList.get(0).getValue());
                rs2 = getRegister(registersList.get(1).getValue());
                rd = getRegister(registersList.get(2).getValue());
                return InstructionFormat.ThreeR;
            }
            default -> {
                var nextToken = tokenHandler.peek();
                if (nextToken.isPresent())
                    throw new SyntaxErrorException("Expected no more than 3 registers", nextToken.get().getLineNumber(), nextToken.get().getPosition());
                else
                    throw new SyntaxErrorException("Expected no more than 3 registers by end of file");
            }
        }
    }

    private String getRegister(String value) throws SyntaxErrorException {
        int number = Integer.parseInt(value);

        if (number < 0 || number > 31)
            throw new SyntaxErrorException("Register must be 0-31.");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 4; i >= 0; i --) {
            if (number / Math.pow(2, i) >= 1) {
                number -= (int) Math.pow(2, i);
                stringBuilder.append("1");
            }
            else
                stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }

    private String getImmediate(String value, int length) throws SyntaxErrorException {
        int number = Integer.parseInt(value);
        StringBuilder stringBuilder = new StringBuilder();

        if (number < Math.pow(2, length) / 2 * -1 || number > Math.pow(2, length) / 2 - 1)
            throw new SyntaxErrorException("Number %d is not a valid immediate for %d bits.".formatted(number, length));

        boolean isNegative = false;
            if (number < 0) {
                number = (number * -1) -1;
                isNegative = true;
            }
            for (int i = length - 1; i >= 0; i--) {
                if (number / Math.pow(2, i) >= 1) {
                    number -= (int) Math.pow(2, i);
                    stringBuilder.append("1");
                }
                else
                    stringBuilder.append("0");
            }
            if (isNegative) {
                // Invert string for negative
                char[] intermediate =stringBuilder.toString().toCharArray();
                stringBuilder = new StringBuilder();
                for (char character: intermediate) {
                    if (character == '0')
                        stringBuilder.append("1");
                    else
                        stringBuilder.append("0");
                }
            }

            return stringBuilder.toString();
    }

    private String parseMathOperation(Token.TokenType opCode) throws SyntaxErrorException {
        var token = tokenHandler.peek();
        if (token.isEmpty() || token.get().getTokenType() != Token.TokenType.KEYWORD)
            throw new SyntaxErrorException("Expected a math operation following %s".formatted(opCode));

        tokenHandler.remove();
        var keyword = token.get().getKeyword();
        switch (keyword) {
            case AND, OR, XOR, NOT, SHIFT_LEFT, SHIFT_RIGHT, ADD, SUB, MULT -> {
                return binaryMap.get(keyword);
            }
            default -> throw new SyntaxErrorException("Expected a math operation following %s".formatted(opCode));
        }
    }

    private String parseBooleanOperation(Token.TokenType opCode) throws SyntaxErrorException {
        var token = tokenHandler.peek();
        if (token.isEmpty() || token.get().getTokenType() != Token.TokenType.KEYWORD)
            throw new SyntaxErrorException("Expected a boolean operation following %s".formatted(opCode));

        tokenHandler.remove();
        var keyword = token.get().getKeyword();
        switch (keyword) {
            case EQUAL, NOTEQUAL, LESS, GREATER_EQUAL, GREATER, LESS_EQUAL -> {
                return binaryMap.get(keyword);
            }
            default -> throw new SyntaxErrorException("Expected a boolean operation following %s".formatted(opCode));
        }
    }

    private boolean parseSeparators() {
        boolean anySeparators = false;
        Optional<Token> token = tokenHandler.matchAndRemove(Token.TokenType.SEPARATOR);
        while (token.isPresent()) {
            token = tokenHandler.matchAndRemove(Token.TokenType.SEPARATOR);
            anySeparators = true;
        }
        return anySeparators;
    }

    private void populateBinaryMap(HashMap<Token.TokenType, String> map) {
        // op codes
        map.put(Token.TokenType.MATH, "000");
        map.put(Token.TokenType.BRANCH, "001");
        map.put(Token.TokenType.CALL, "010");
        map.put(Token.TokenType.PUSH, "011");
        map.put(Token.TokenType.LOAD, "100");
        map.put(Token.TokenType.STORE, "101");
        map.put(Token.TokenType.PEEK, "110");
        map.put(Token.TokenType.POP, "110");
        map.put(Token.TokenType.RETURN, "100");
        map.put(Token.TokenType.COPY, "000");
        map.put(Token.TokenType.HALT, "000");

        // math operations
        map.put(Token.TokenType.AND, "1000");
        map.put(Token.TokenType.OR, "1001");
        map.put(Token.TokenType.XOR, "1010");
        map.put(Token.TokenType.NOT, "1011");
        map.put(Token.TokenType.SHIFT_LEFT, "1101");
        map.put(Token.TokenType.SHIFT_RIGHT, "1101");
        map.put(Token.TokenType.ADD, "1110");
        map.put(Token.TokenType.SUB, "1111");
        map.put(Token.TokenType.MULT, "0111");

        // boolean operations
        map.put(Token.TokenType.EQUAL, "0000");
        map.put(Token.TokenType.NOTEQUAL, "0001");
        map.put(Token.TokenType.LESS, "0010");
        map.put(Token.TokenType.GREATER_EQUAL, "0100");
        map.put(Token.TokenType.GREATER, "0100");
        map.put(Token.TokenType.LESS_EQUAL, "0101");
    }
}
