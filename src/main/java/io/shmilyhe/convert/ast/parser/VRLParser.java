package io.shmilyhe.convert.ast.parser;

import io.shmilyhe.convert.ast.statement.Statement;

public class VRLParser {

    public Statement parse(String str){
        Statement root = new Statement().setType(Statement.TYPE_ROOT);

        return root;
    }
    
}
