import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

public class Lexer {
    private StringHandler stringHandler;
    private int lineNumber;
    private int position;
    private HashMap<String, Token.TokenType> keywordsMap;
    private LinkedList<Token> tokensList;

    public static void main(String[] args) throws Exception {
        String contents = new String(Files.readAllBytes(Paths.get(args[0])));
        Lexer lexer = new Lexer(contents);
        System.out.println(lexer.lex());
    }

    public Lexer(String contents) {
        stringHandler = new StringHandler(contents);
        lineNumber = 1;
        position = 1;

        keywordsMap = new HashMap<>();
        populateKeywordMap(keywordsMap);
        tokensList = new LinkedList<>();
    }

    public LinkedList<Token> lex() throws Exception {
        while (!stringHandler.isDone()) {
            char character = stringHandler.peek();

            // Check for a register
            if ((character == 'r' || character == 'R') &&
                    Character.isDigit(stringHandler.peek(1))) {
                stringHandler.swallow(1);
                position++;
                int beginPosition = position;
                String number = processNumber();
                tokensList.add(new Token(Token.TokenType.REGISTER, lineNumber, beginPosition, number));
            }
            else {
                switch (character) {
                    case ' ' -> {
                        stringHandler.swallow(1);
                        position++;
                    }
                    case '\r' -> stringHandler.swallow(1);
                    case '\n' -> {
                        tokensList.add(new Token(Token.TokenType.SEPARATOR,
                                lineNumber, position));
                        stringHandler.swallow(1);
                        lineNumber++;
                        position = 1;
                    }
                    case '/' -> {
                        stringHandler.swallow(1);
                        if (stringHandler.getChar() == '/')
                            processComment();
                        else
                            // todo: exception class
                            throw new Exception("");
                    }
                    default -> {
                        // todo: need to handle negative numbers (and maybe also + sign)
                        // Might be a number
                        if (Character.isDigit(character))
                            tokensList.add(new Token(Token.TokenType.NUMBER, lineNumber, position, processNumber()));
                        // Only option left is a keyword
                        else {
                            StringBuilder phrase = new StringBuilder();
                            Token.TokenType keyword;
                            // handle keyword phrases up to 3 words (ex. greater than equal)
                            int numberOfWords = 0;
                            do {
                                phrase.append(processWord()).append(" ");
                                keyword = keywordsMap.get(phrase.toString().strip());
                                numberOfWords++;
                                // swallow the space between words
                                stringHandler.swallow(1);
                            } while (keyword == null && numberOfWords < 3);
                            if (keyword == null)
                                // todo: exception
                                throw new Exception("");
                            tokensList.add(new Token(keyword, lineNumber, position));
                        }
                    }
                }
            }
        }
        tokensList.add(new Token(Token.TokenType.SEPARATOR, lineNumber, position));
        return tokensList;
    }

    private String processNumber() {
        StringBuilder value = new StringBuilder();
        while (!stringHandler.isDone() && Character.isDigit(stringHandler.peek())) {
            value.append(stringHandler.getChar());
            position++;
        }
        return value.toString();
    }

    private String processWord() {
        StringBuilder value = new StringBuilder();
        while (!stringHandler.isDone() && Character.isLetter(stringHandler.peek())) {
            value.append(stringHandler.getChar());
            position++;
        }
        return value.toString();
    }

    private void processComment() {
        while (!stringHandler.isDone() && stringHandler.peek() != '\n') {
            stringHandler.swallow(1);
        }
    }

    private void populateKeywordMap(HashMap<String, Token.TokenType> map) {
        map.put("math", Token.TokenType.MATH);
        map.put("add", Token.TokenType.ADD);
        map.put("sub", Token.TokenType.SUB);
        map.put("mult", Token.TokenType.MULT);
        map.put("and", Token.TokenType.AND);
        map.put("or", Token.TokenType.OR);
        map.put("not", Token.TokenType.NOT);
        map.put("xor", Token.TokenType.XOR);
        map.put("copy", Token.TokenType.COPY);
        map.put("halt", Token.TokenType.HALT);
        map.put("branch", Token.TokenType.BRANCH);
        map.put("jump", Token.TokenType.JUMP);
        map.put("call", Token.TokenType.CALL);
        map.put("push", Token.TokenType.PUSH);
        map.put("load", Token.TokenType.LOAD);
        map.put("return", Token.TokenType.RETURN);
        map.put("store", Token.TokenType.STORE);
        map.put("peek", Token.TokenType.PEEK);
        map.put("pop", Token.TokenType.POP);
        map.put("interrupt", Token.TokenType.INTERRUPT);
        map.put("equal", Token.TokenType.EQUAL);
        map.put("not equal", Token.TokenType.NOTEQUAL);
        map.put("greater than", Token.TokenType.GREATER);
        map.put("less than", Token.TokenType.LESS);
        map.put("greater than equal", Token.TokenType.GREATER_EQUAL);
        map.put("less than equal", Token.TokenType.LESS_EQUAL);
        map.put("shift", Token.TokenType.SHIFT);
        map.put("left", Token.TokenType.LEFT);
        map.put("right", Token.TokenType.RIGHT);
    }
}