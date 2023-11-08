package io.shmilyhe.convert.tokenizer;



public class DefaultParser implements IParser{

    @Override
    public Block parse(ITokenizer tks,IParserEvent evnt) {
        int offset = 0;
        StringBuilder part = new StringBuilder();
        int col=0;
        int line=0;
        while(tks.hasNext()){
            char ch=tks.next();
            col=tks.column();
            line=tks.line();
            switch (ch) {
                case '\r':
                case '\n':
                    while(tks.hasNext()){
                        char c=tks.next();
                        if(c!='\n'&& c!='\r'){
                            tks.back(c);
                            break;
                        }
                        //System.out.println("n1:["+(c)+"]("+((int)c)+")");
                    }
                    if (part.length()>0) {
                        evnt.text(part.toString(), line, col);
                        part=new StringBuilder();
                    }
                    evnt.nextLine();
                    break;
                case '#':
                    if(part.length()>0){
                        evnt.text(part.toString(),line , col);
                        part = new StringBuilder();
                    }
                    tks.toLineEnd();
                    evnt.nextLine();
                    //part.append('#').append(tks.toLineEnd());
                    //evnt.token(part.toString(),line , col);
                    //part = new StringBuilder();
                    break;
                case '"':
                    String t=tks.tillNext('"');
                    part.append('"').append(t);
                    //evnt.token(part.toString(),line , col);
                    //part = new StringBuilder();
                    break;
                case ' ':
                    /*if (part.length() > 0){
                        evnt.token(part.toString(),line , col);
                        part=new StringBuilder();
                    }*/
                    break;
                case '-':
                case '+':
                    if (part.length() > 0&&offset > 0) {
                        evnt.text(part.toString(),line , col);
                        evnt.symbol(String.valueOf(ch),line , col);
                        part = new StringBuilder();
                    } else {
                        part.append(ch);
                    }
                    
                    break;
                case '(':

                    if (part.length() > 0){
                        evnt.function(part.toString(),line , col);
                        part = new StringBuilder();
                    }else
                    evnt.symbol(String.valueOf(ch),line , col);
                    break;
                case '/':
                case '*':
                case ')':
                case '%':
                case ',':
                case ';':
                case '{':
                case '}':
                    if (part.length() > 0){
                        evnt.text(part.toString(),line , col);
                        part = new StringBuilder();
                    }
                    evnt.symbol(String.valueOf(ch),line , col);
                    
                    offset = 0;
                    break;
                case '>':
                    if (part.length() > 0)
                        evnt.text(part.toString(),line , col);

                    if(tks.hasNext()){
                        char c1=tks.next();
                        if(c1=='>'){
                            if(tks.hasNext()){
                                char c2=tks.next();
                                if(c2=='>'){
                                    evnt.symbol(">>>",line , col);
                                }else{
                                    tks.back(c2);
                                    evnt.symbol(">>",line , col);
                                }
                            }else{
                                evnt.symbol(">>",line , col);
                            }
                        }else if(c1=='='){
                            evnt.symbol(">=",line , col);
                        }else{
                            evnt.symbol(">",line , col);
                            tks.back(c1);
                        }
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                case '<':
                if (part.length() > 0)
                        evnt.text(part.toString(),line , col);
                     if(tks.hasNext()){
                        char c1=tks.next();
                        if(c1=='<'){
                            evnt.symbol("<<",line , col);
                        }else if(c1=='='){
                            evnt.symbol("<=",line , col);
                        }else{
                            tks.back(c1);
                            evnt.symbol("<",line , col);
                        }
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                case '!':
                if (part.length() > 0)
                        evnt.text(part.toString(),line , col);
                     if(tks.hasNext()){
                        char c1=tks.next();
                        if(c1=='!'){
                            evnt.symbol("!!",line , col);
                        }else if(c1=='='){
                            evnt.symbol("!=",line , col);
                        }else{
                            tks.back(c1);
                            evnt.symbol("!",line , col);
                        }
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                case '=':
                case '|':
                case '&':
                    if (part.length() > 0)
                        evnt.text(part.toString(),line , col);
                    if(tks.hasNext()){
                        char c1=tks.next();
                        if(c1==ch){
                            evnt.symbol(new String(new char[]{ch,c1}),line , col);
                        }else{
                            tks.back(c1);
                            evnt.symbol(String.valueOf(ch),line , col);
                        }
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                default:
                    part.append(ch);
                    break;
            }
            offset += 1;
        }
        if(part.length()>0){
            col=tks.column();
            line=tks.line();
            evnt.text(part.toString(),line , col);
        }
        return evnt.getBlock();
    }
    
}
