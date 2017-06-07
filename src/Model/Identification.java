/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author nhata
 */
//định nghĩa kiểu các đối tượng
public class Identification {
    private String type;
    private String name;
    
    public Identification(String type,String name){
        this.name = name;
        this.type = type;
    }
    public String getIdent(){
        return type +" "+ name;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public void setName(String s){
        name = s;
    }
    public void setType(String s){
        type = s;
    }
}
