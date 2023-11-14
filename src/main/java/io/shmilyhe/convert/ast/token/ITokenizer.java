package io.shmilyhe.convert.ast.token;

/**
 * 
 * 序列化器
 * 字符转成语法 token
 */
public interface ITokenizer {
    boolean hasNext();
    Token next();
    void back();
    void reset();
    void print();
}
