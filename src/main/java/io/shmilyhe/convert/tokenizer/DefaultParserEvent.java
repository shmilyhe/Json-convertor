package io.shmilyhe.convert.tokenizer;


public class DefaultParserEvent implements IParserEvent{

    Block root;
    Block curr;
    public DefaultParserEvent(){
        root=new Block();
        root.setType(Block.TYPE_LINE);
        curr=root;
    }

    boolean blockStart=true;
    String last;
    @Override
    public void text(String str, int line, int col) {
        //System.out.print(str);
       if("if".equals(str)){
            Block fb= new Block().setType(Block.TYPE_IF).setName(str);
            fb.setLine(line);
            curr.sub(fb);
            curr=fb;
       }else if("each".equals(str)){
            Block fb= new Block().setType(Block.TYPE_EACH).setName(str);
            fb.setLine(line);
            curr.sub(fb);
            curr=fb;
       }else if("else".equals(str)){
            
       }else{
            curr.addToken(new Token()
            .setLine(line)
            .setColumn(col)
            .setText(str)
            .setType(Token.TYPE_VAR));
       }
       last=str;
       blockStart=false;
    }
    static Token comma= new Token().setType(Token.TYPE_COMMA);
        int bracketLevel=0;
        int blockLevel=0;
    public void symbol(String str, int line, int col) {
        //System.out.print(str);
       if("(".equals(str)){
            bracketLevel++;
           curr.addToken(new Token().setLine(line).setColumn(col).setText(str).setType(Token.TYPE_OPERATE));
       }else if(")".equals(str)){
            bracketLevel--;
            if(curr.getType()==Block.TYPE_FUN&& curr.getLevel()==bracketLevel){
                curr=curr.getParent();
            }
       }else if(",".equals(str)){
            curr.addToken(comma);
       }else if(";".equals(str)){
            
       }else if("{".equals(str)){
            Block nl = new Block().setType(Block.TYPE_LINE);
            nl.setLine(line);
            curr.sub(nl);
            curr=nl;
       }else if("}".equals(str)){
            Block ll=curr.getParent();
            curr=ll;
       }else{
            curr.addToken(
                new Token().setLine(line).setColumn(col)
                .setText(str).setType(Token.TYPE_OPERATE)
            );
       }

       last=str;
       blockStart=false;
    }
    @Override
    public Block getBlock() {
        return root;
    }

    @Override
    public void nextLine() {
        //System.out.println();
        Block nb =new Block();
        nb.setParent(curr.getParent());
        nb.setLine(curr.getLine()+1);
        curr.setNext(nb);
        //curr.setLine();
        curr.setType(Block.TYPE_LINE);
        curr=nb;
        blockStart=true;
    }

    @Override
    public void function(String str, int line, int col) {
        //System.out.print(str);
        curr.setName(str);
        curr.setLine(line);
        if("if".equals(str)){
            Block fb= new Block().setType(Block.TYPE_IF).setName(str).setLevel(bracketLevel);
            fb.setLine(line);
            curr.setNext(fb);
            curr=fb;
       }else if("each".equals(str)){ 
            Block fb= new Block().setType(Block.TYPE_EACH).setName(str).setLevel(bracketLevel);
            fb.setLine(line);
            curr.setNext(fb);
            curr=fb;
       }else{
            Block fb= new Block().setType(Block.TYPE_FUN).setName(str).setLevel(bracketLevel);
            fb.setLine(line);
            if(curr.tokens.size()>0){
                curr.addToken(new Token().setBlock(fb).setLine(line).setColumn(col).setType(Token.TYPE_FUNCTION));
                curr.sub(fb);
            }else{
                curr.setNext(fb);
            }
            
            curr=fb;
       }
       bracketLevel++;
       
        
    }
    
}
