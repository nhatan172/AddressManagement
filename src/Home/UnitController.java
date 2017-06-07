/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import MainClass.Address;
import Model.AddressModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author nhata
 */
public class UnitController implements Initializable {

    @FXML
    private Label Addr;
    @FXML
    private Label Ward;
    @FXML
    private Label District;
    @FXML
    private Label Province;
    @FXML
    private Text Status;
    @FXML
    private Text ButtonOpts;
    private AddressModel a;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ButtonOpts.setStyle("-fx-text-color: red ");
    }    

    public void intit(AddressModel add){
        a = add;
        Addr.setText(add.getAddress());
        Ward.setText(add.getWard().getIdent());
        District.setText(add.getDistrict().getIdent());
        Province.setText(add.getProvince().getIdent());
        Status.setVisible(add.getStatus());
    }
 
    @FXML
    private void optionEvent(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/OptionDialog.fxml"));
        Parent r = loader.load();
        OptionDialogController ctr = loader.getController();
        ctr.setAdd(a);
        Stage stage  = new Stage();
        stage.setScene(new Scene(r));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setX(Address.getStage().getX());
        stage.setY(Address.getStage().getY()+200);
        stage.setTitle("Options");
        ctr.setStage(stage);
        stage.showAndWait();
    }


}
