package com;

import io.shmilyhe.convert.tools.ExpCalculate;

public class Testtokenizer {
    public static void main(String[] args){
        {
            String exp="!((x+y)>=1&&x*x!=0||y<2)";
            String kt[]= ExpCalculate.tokenizer(exp);
            for(String k:kt){
                System.out.print(k+"_");
            }
            System.out.println();
        }
        {
            String exp="if (.ext==123) { .uuu=true }";
            String kt[]= ExpCalculate.tokenizer(exp);
            for(String k:kt){
                System.out.print(k+"_");
            }
            System.out.println();
        }
        

    }
}
