package io.shmilyhe.convert.tokenizer;



/**
 * 
 */
public interface IParser {
    Block parse(ITokenizer tks,IParserEvent evnt);
}
