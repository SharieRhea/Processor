public class Token {

    public enum TokenType {
        MATH, ADD, SUB, MULT, AND, OR, NOT, XOR, COPY, HALT, BRANCH, JUMP, CALL,
        PUSH, LOAD, RETURN, STORE, PEEK, POP, EQUAL, NOTEQUAL, GREATER, LESS,
        GREATER_EQUAL, LESS_EQUAL, SHIFT_LEFT, SHIFT_RIGHT,
        REGISTER, NUMBER, KEYWORD,
        SEPARATOR
    }

    private final TokenType tokenType;
    private String value;
    private TokenType keyword;
    private final int lineNumber;
    private final int position;

    // Token with no value
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

    // Token with value (keyword)
    public Token(TokenType type, int lineNum, int charPosition, TokenType value) {
        this(type, lineNum, charPosition);
        keyword = value;
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

    public TokenType getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        if (this.value != null)
            return tokenType.toString() + "(" + value + ")";
        else if (this.keyword != null)
            return tokenType.toString() + "(" + keyword.toString() + ")";
        else
            return tokenType.toString();
    }
}
