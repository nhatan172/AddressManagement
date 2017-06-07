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
public class AddressModel {
    private String address;
    private String ID;
    private Identification ward ;//phường
    private Identification district ;//đường
    private Identification province ;//tỉnh
    private Boolean status = false ;//mặc định

    public String getID(){
        return ID;
    }
    public boolean getStatus(){
        return status;
    }
    public String getAddress(){
        return address;
    }
    public Identification getWard(){
        return ward;
    }    
    public Identification getDistrict(){
        return district;
    }   
    public Identification getProvince(){
        return province;
    }
    public void setAddress(String input){
        this.address = input;
    }
    public void setWard(Identification input){
        this.ward = input;
    }
    public void setDistrict(Identification input){
        this.district = input;
    }
    public void setProvince(Identification input){
        this.province = input;
    }
    public void setStatus(int input){
        if(input == 1)
            this.status = true;
        else this.status = false;
    }
    public void setID(String ID){
        this.ID = ID;
    }

 
}
