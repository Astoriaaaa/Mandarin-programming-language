package parser;
import ast.*;

public interface parseInfix {
    astt.Expression parseInfix(GenerateAST.ParserInit p, astt.Expression exp);
}
