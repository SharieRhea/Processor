public class Token {

    public enum TokenType {
        MATH, ADD, SUB, MULT, AND, OR, NOT, XOR, COPY, HALT, BRANCH, JUMP, CALL,
        PUSH, LOAD, RETURN, STORE, PEEK, POP, INTERRUPT, EQUAL, NOTEQUAL, GREATER, LESS,
        GREATER_EQUAL, LESS_EQUAL, SHIFT, LEFT, RIGHT,
        REGISTER, NUMBER,
        SEPARATOR
    }

    private final TokenType tokenType;
    private String value;
    private final int lineNumber;
    private final int position;

    // Token with no value (keyword)
    public Token(TokenType type, int lineNum, int charPosition) {
        tokenType = type;
        lineNumber = lineNum;
        position = charPosition;
    }

    // Token with value (number)
    public Token(TokenType type, int lineNum, int charPosition, String tokenValue) {
        this(type, lineNum, charPosition);
        value = tokenValue;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getPosition() {
        return position;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (this.value != null)
            return tokenType.toString() + "(" + value + ")";
        else
            return tokenType.toString();
    }
}
