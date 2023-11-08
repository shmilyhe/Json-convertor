package io.shmilyhe.convert.tokenizer;

public interface IParserEvent {
   void nextLine();
   void text(String str,int line,int col);
   void symbol(String str,int line,int col);
   void function(String str,int line,int col);
   Block getBlock();
}