package io.shmilyhe.convert.api;

public class ExpPartVo {
    private String key;
    private int index;
    private int type;
    private ExpPartVo next;
    private ExpPartVo tail;


    public ExpPartVo(String key){
        type=0;
        this.key=key;
    }

    public ExpPartVo(String key,int type){
        this.type=2;
        this.key=key;
    }

    public ExpPartVo(int index){
        type=1;
        this.index=index;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public ExpPartVo getNext() {
        return next;
    }
    public void setNext(ExpPartVo next) {
        this.next = next;
    }
    public void append(ExpPartVo next){
        if(tail==null){
            this.setNext(next);
            tail=next.tail==null?next:next.tail;
        }else{
            tail.next=next;
            tail=next.tail==null?next:next.tail;
        }
    }

    
}
