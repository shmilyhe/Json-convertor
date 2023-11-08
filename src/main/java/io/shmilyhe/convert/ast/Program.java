package io.shmilyhe.convert.ast;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.ast.statement.Statement;

/**
 * Abstract Syntax Tree
 * 
 */
public class Program {
    private String name;
    protected List<Statement> body;

    public String getName() {
        return name;
    }
    public Program setName(String name) {
        this.name = name;
        return this;
    }
    public List<Statement> getBody() {
        return body;
    }
    public Program setBody(List<Statement> body) {
        this.body = body;
        return this;
    }

    public Program addStatement(Statement stat){
        if(body==null){
            body=new ArrayList<>();
        }
        body.add(stat);
        return this;
    }

    

}
