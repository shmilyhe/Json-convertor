package io.shmilyhe.convert.ast.token;

public interface ITokenizer {
    boolean hasNext();
    Token next();
    void back();
    void reset();
    void print();
}
