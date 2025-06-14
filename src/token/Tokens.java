package token;

import java.util.HashMap;
import java.util.Map;


public class Tokens {
    public static String Tokentype;
    final public static String LET = "LET";
    final public static String RETURN = "RETURN";
    final public static String FN = "FUNCTION";
    final public static String IDENTIFIER = "IDENTIFIER";
    final public static String TRUE = "TRUE";
    final public static String FALSE = "FALSE";
    final public static String INT = "INT";
    final public static String STRING = "STRING";
    final public static String IF = "IF";
    final public static String ELSE = "ELSE";
    final public static String ELSEIF = "ELSEIF";
    final public static String ENDIF = "ENDIF";


    final public static String PLUS = "+";
    final public static String MINUS = "-";
    final public static String ASSIGN = "=";
    final public static String MUL = "*";
    final public static String EXCLAIM = "!";

    final public static String GT = ">";
    final public static String LT = "<";
    final public static String GTE = ">=";
    final public static String LTE = "<=";
    final public static String EQ = "==";
    final public static String NEQ = "!=";

    final public static String LB = "(";
    final public static String RB = ")";
    final public static String LP = "{";
    final public static String RP = "}";
    final public static String LS = "[";
    final public static String RS = "]";
    final public static String SEMICOLON = ";";
    final public static String COMMA = ",";
    final public static String QUOTES = "\"";
    final public static String ILEGAL = "ILEGAL";
    final public static String EOF = "EOF";
    final public static String NULL = "NULL";

    public static Map<String, String>hashMap;

    static {
        hashMap = new HashMap<>();
        hashMap.put(LET, "设"); 
        hashMap.put(RETURN, "返回");
        hashMap.put(FN, "功能");
        hashMap.put(TRUE, "真的");
        hashMap.put(FALSE, "错误的");
        hashMap.put(NULL, "无效的");
        hashMap.put(IF, "如果");
        hashMap.put(ELSE, "或者");
        hashMap.put(ELSEIF, "否则");
        hashMap.put(ENDIF, "结尾");
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


