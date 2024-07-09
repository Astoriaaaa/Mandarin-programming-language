package token;

import java.util.HashMap;
import java.util.Map;


public class Tokens {
    public static String Tokentype;
    public static String LET = "LET";
    public static String RETURN = "RETURN";
    public static String FN = "FUNCTION";
    public static String IDENTIFIER = "IDENTIFIER";
    public static String TRUE = "TRUE";
    public static String FALSE = "FALSE";
    public static String INT = "INT";
    public static String STRING = "STRING";
    public static String IF = "IF";
    public static String ELSE = "ELSE";
    public static String ELSEIF = "ELSEIF";
    public static String ENDIF = "ENDIF";


    public static String PLUS = "+";
    public static String MINUS = "-";
    public static String ASSIGN = "=";
    public static String MUL = "*";
    public static String EXCLAIM = "!";

    public static String GT = ">";
    public static String LT = "<";
    public static String GTE = ">=";
    public static String LTE = "<=";
    public static String EQ = "==";
    public static String NEQ = "!=";

    public static String LB = "(";
    public static String RB = ")";
    public static String LP = "{";
    public static String RP = "}";
    public static String LS = "[";
    public static String RS = "]";
    public static String SEMICOLON = ";";
    public static String COMMA = ",";
    public static String QUOTES = "\"";
    public static String ILEGAL = "ILEGAL";
    public static String EOF = "EOF";
    public static String NULL = "NULL";

    public static Map<String, String>hashMap;

    static {
        hashMap = new HashMap<>();
        hashMap.put(LET, "let");
        hashMap.put(RETURN, "return");
        hashMap.put(FN, "fn");
        hashMap.put(TRUE, "true");
        hashMap.put(FALSE, "false");
        hashMap.put(NULL, "null");
        hashMap.put(IF, "if");
        hashMap.put(ELSE, "else");
        hashMap.put(ELSEIF, "elseif");
        hashMap.put(ENDIF, "endif");
    }

    public static boolean isToken(String val) {
        return hashMap.containsValue(val);
    }

    public static String identType(String val) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(val)) {
                return entry.getKey();
            }
        }
        return null;
    }

}


