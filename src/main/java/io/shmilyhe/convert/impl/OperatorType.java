package io.shmilyhe.convert.impl;

import java.util.HashMap;

public enum OperatorType {

  BIT_OR("|", 2),

  BIT_AND("&", 2),

  BIT_XOR("^", 2),

  BIT_NOT("~", 1),

  SHIFT_LEFT("<<", 2),

  SHIFT_RIGHT(">>", 2),

  U_SHIFT_RIGHT(">>>", 2),

  NOT("!", 1),

  MULT("*", 2),

  Exponent("**", 2),

  DIV("/", 2),

  MOD("%", 2),

  ADD("+", 2),

  SUB("-", 2),

  LT("<", 2),

  LE("<=", 2),

  GT(">", 2),

  GE(">=", 2),

  EQ("==", 2),

  NEQ("!=", 2),

  AND("&&", 2),

  MATCH("=~", 2),

  OR("||", 2),

  INDEX("[]", 2),

  FUNC("()", Integer.MAX_VALUE),

  NEG("-neg", 1),

  TERNARY("?:", 3),

  ASSIGNMENT("=", 2),
  UNKOWN("unkown", 0);
  //DEFINE("=", 2);

  public final String token;

  public final int arity;

  OperatorType(final String token, final int operandCount) {
    this.token = token;
    this.arity = operandCount;
  }

  static HashMap<String,OperatorType> tymap= new HashMap();
  static {
    OperatorType[] ots= OperatorType.values();
    for(OperatorType o:ots){
      tymap.put(o.token, o);
    }
  }

  public static OperatorType find(String token){
   OperatorType ty= tymap.get(token);
    return ty==null?UNKOWN:ty;

  }
}
