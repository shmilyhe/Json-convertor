package io.shmilyhe.convert.ast.token;

public class CacheTokenizer implements ITokenizer{

    protected Node head;
    protected Node tail;
    protected Node flag;

    int size=0;

    public void add(Token t){
        if(head==null){
            head=new Node();
            flag=head;
            tail=flag;
        }
        Node n = new Node();
        n.data=t;
        n.pre=tail;
        tail.next=n;
        tail=n;
        size++;
    }
    public int size(){
        return size;
    }

    public CacheTokenizer removeTail(){
        if(tail!=null&&tail.pre!=null){
            tail=tail.pre;
            tail.next=null;
        }
        return this;
    }

    @Override
    public boolean hasNext() {
        return flag!=null&&flag.next!=null;
    }

    @Override
    public Token next() {
        flag=flag.next;
        return flag.data;
    }

    @Override
    public void back() {
        if(flag!=null&flag.pre!=null){
            flag=flag.pre;
        }
    }
    @Override
    public void reset() {
        flag=head;
    }

    public String toString(){
        return String.valueOf(flag==head);
    }
    
}
class Node{
    Node next;
    Node pre;
    Token data;
}
