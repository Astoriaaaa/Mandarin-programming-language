package parser;
import ast.*;

public interface infixParse {
    astt.Expression parseInfix(GenerateAST.ParserInit p, astt.Expression exp);
}
