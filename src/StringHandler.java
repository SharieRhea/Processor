public class StringHandler {
    private int index;
    private final String content;

    public StringHandler(String fileContents) {
        index = 0;
        content = fileContents;
    }

    public char peek() {
        return content.charAt(index);
    }

    public char peek(int numberOfCharacters) {
        return content.charAt(index + numberOfCharacters);
    }

    public String peekString(int numberOfCharacters) {
        // Trying to peek too far, return as much as possible.
        if (index + numberOfCharacters > content.length() - 1) {
            return this.remainder();
        }
        return content.substring(index, index + numberOfCharacters);
    }

    public char getChar() {
        char returnVal = content.charAt(index);
        index++;
        return returnVal;
    }

    public void swallow(int numberOfCharacters) {
        index += numberOfCharacters;
    }

    public String remainder() {
        return content.substring(index);
    }

    public Boolean isDone() {
        return index == content.length();
    }
}
