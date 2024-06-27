package parser;
import ast.*;

public interface prefixParse {
    astt.Expression parsePrefix(GenerateAST.ParserInit p);
}
