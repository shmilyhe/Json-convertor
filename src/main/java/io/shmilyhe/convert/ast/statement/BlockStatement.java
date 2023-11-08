package io.shmilyhe.convert.ast.statement;

public class BlockStatement extends Statement {
    @Override
    public String getType() {
        return TYPE_BLOCK;
    }
}
