package com;

import io.shmilyhe.convert.ConvertorFactory;

public class TestFactory extends ConvertorFactory {

    public static void main(String[] args){
        TestFactory tf = new TestFactory();
        {
            String exp = "if xxxxx { x+y+z}   ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "each(xxxxx){ x+y+z}   ";
            System.out.println(tf.getExpression(exp));
        }
         {
            String exp = "each   (xxxxx)   { x+y+z}   ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "each   (xxxxx)   { print(x+y+z)}   ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "{ x+y+z}   ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "{    ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "   }  ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "  .l=123  }  ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "  .x=123   ";
            System.out.println(tf.getExpression(exp));
        }
        {
            String exp = "if abcd { x+y+z}   ";
            System.out.println("if:" +tf.getIfExpression(exp));
        }
        {
            String exp = "if   (   abcd ) { x+y+z}   ";
            System.out.println("if:" +tf.getIfExpression(exp));
        }

        {
            String exp = "each   (   .abcd ) { x+y+z}   ";
            System.out.println("each:" +tf.getEachExpression(exp));
        }
        
        
        
        
    }
    
}
