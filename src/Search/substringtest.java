/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;


/**
 *
 * @author nhata
 */
public class substringtest {
    public static void main(String[] args){
    String s = "thi tran cai be";
    String s1 = "thi tran";
        System.out.println(s.contains(s1)); 
    System.out.print("|" + s.substring(0, "thi tran".length()) + "|");
    System.out.print("|" + s.substring("thi tran".length()+1,s.length()) + "|");
    }
}
