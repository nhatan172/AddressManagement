package Search;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Edit.EditViewController;
import MainClass.Address;
import Model.AddressModel;
import Model.Identification;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author nhata
 */
public class AddressUnitController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private HBox hbox;
    @FXML
    private Label label2;
    private AddressModel address;
    private int TypeEdit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    public void init(String s, AddressModel a,int type){
        label.setText(s);
        address = a;
        TypeEdit = type;
    }
    private Identification StringToIdent(String s){
        String[] TYPE = {"Xã","Phường","Thị Trấn","Huyện","Thị Xã","Quận","Thành Phố","Tỉnh"};
        List<String> type = Arrays.asList(TYPE);
        for (String t : type){
            if(s.contains(t))
                return new Identification(t,s.substring(t.length()+1,s.length()));
        }
        return null;
    }
    @FXML
    private void ClickChoose(MouseEvent e) throws IOException{
        switch(TypeEdit){
            case 1: if(address.getDistrict()== null || address.getProvince().equals(StringToIdent(label.getText())) == false)
                    {
                        address.setProvince(StringToIdent(label.getText()));
                        address.setDistrict(null);
                        address.setWard(null);
                        address.setAddress(null);
                    }
                    break;
            case 2: if(address.getDistrict() == null || address.getDistrict().equals(StringToIdent(label.getText())) == false)
                    {
                        address.setDistrict(StringToIdent(label.getText()));
                        address.setWard(null);
                        address.setAddress(null);
                    }
                        break;
            case 3: if(address.getWard() == null || address.getWard().equals(StringToIdent(label.getText())) == false)
                    {     
                        address.setWard(StringToIdent(label.getText()));
                        address.setAddress(null);
                    }
                    break;
            default: break;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit/EditView.fxml"));
        Parent root = loader.load();
        Address.getStage().setScene(new Scene(root));
        EditViewController ctrl = loader.getController();
        ctrl.initView(address);
    }
    @FXML
    private void EnteredChooseSide(MouseEvent e){
        label2.setEffect(new Glow(1));
    }
    @FXML
    private void ExitedChooseSide(MouseEvent e){
        label2.setEffect(null);
    }
}
