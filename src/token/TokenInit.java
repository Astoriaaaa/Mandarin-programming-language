package token;

public class TokenInit {
    public String tokType;
    public String tokLiteral;
    public String tokString;
    public TokenInit(String tokType, String tokLiteral) {
         this.tokType = tokType;
         this.tokLiteral = tokLiteral;
         this.tokString = "{tokType: " + tokType + ", tokLiteral: " + tokLiteral + "}";
    } 
 }
