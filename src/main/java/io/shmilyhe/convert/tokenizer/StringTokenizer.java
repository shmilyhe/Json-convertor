package io.shmilyhe.convert.tokenizer;

public class StringTokenizer implements ITokenizer{
    final static String SYMBOL=";+-*/%(){}<>!&|,=\r\n\t :^#";
    protected CharSequence string;
    int index=0;
    int line=0;
    int column=0;
    public StringTokenizer(CharSequence seq){
        string=seq;
    }

    @Override
    public char next() {
        if(string==null)return (char)0;
        if(string.length()>index){
            char c=string.charAt(index++);
            if(c=='\r'||c=='\n'){
                line++;
                column=0;
            }
            column++;
            return c;
        }
        return (char)0;
    }

    @Override
    public boolean hasNext() {
        return string!=null&&string.length()>index;
    }

    @Override
    public String toLineEnd() {
        StringBuilder sb = new StringBuilder();
        while(hasNext()){
            char c=next();
            if(c=='\r'||c=='\n'){
                this.back(c);
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }


    public String whitespace(){
        StringBuilder sb = new StringBuilder();
        while(hasNext()){
            char c=next();
            if(c!=' '&&c!='\t'){
                this.back(c);
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public int column() {
        return column-1;
    }

    @Override
    public int line() {
        return line+1;
    }

    @Override
    public String tillNext(char e) {
        StringBuilder sb = new StringBuilder();
        char nc=(char)0;
        while(hasNext()){
            char c=next();
            if(c=='\r'||c=='\n')break;
            if(c==e&&nc!='\\'){
                sb.append(c);
                break;
            }else{
                sb.append(c);
                nc=c;
            }    
        }
        return sb.toString();
    }

    @Override
    public void back(char ch) {
        if(index>0)index--;
    }

    @Override
    public int offset() {
        return index;
    }

    @Override
    public String toSymbol() {
        StringBuilder sb = new StringBuilder();
        int st=0;
        while(hasNext()){
            char c=next();
            if(SYMBOL.indexOf(c)>-1){
                this.back(c);
                break;
            }
            if(c=='['){
                    st++;
            }
            if(c==']'){
                if(st==0){
                    this.back(c);
                    break;
                }else{
                    st--;
                }
            }
            sb.append(c);
               
        }
        return sb.toString();
    }
    
}
