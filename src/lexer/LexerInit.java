package lexer;

public class LexerInit {
    public int curPos;
    public int finalPos;
    public char curChar;
    public String input;

    public LexerInit(String input) {
        this.input = input;
        this.finalPos = input.length() - 1;
        this.curPos = -1;
        nextChar();
    }

    public void nextChar() {
        if (finalPos <= curPos) {
            this.curChar = 0;
            this.curPos += 1;
        } else {
            this.curPos += 1;  
            this.curChar = input.charAt(curPos);          
        }
    }

}
