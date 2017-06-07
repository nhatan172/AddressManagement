/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import Edit.EditViewController;
import MainClass.Address;
import Model.AddressModel;
import Model.CreateConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nhata
 */
public class OptionDialogController implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private Label lblDel;
    @FXML
    private Label lblEdit;
    @FXML
    private Label lblDefault;
    private AddressModel addr;
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        init();
    }
    public void setStage(Stage s){
        stage = s;
    }
    public void setAdd(AddressModel a){
        addr = a;
    }
    private void init(){
        String css = "-fx-border-width: 0 0 1 0; " +"-fx-border-color: red;";
        lblDel.setStyle(css);
        lblEdit.setStyle(css);
        vbox.setStyle("-fx-background-color: white;"
                        +"-fx-border-width: 0.5 0.5 0.5 0.5;"
                        +"-fx-border-color: gray;");
    }

    @FXML
    private void DeleteEvent(MouseEvent event) throws ClassNotFoundException, SQLException, IOException {
        String sql = "DELETE FROM address where addressid = '" + addr.getID() + "'";
        Connection con =  new CreateConnection().getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        con.close();
        st.close();
        stage.close();
        Alert alert = new Alert(Alert.AlertType.NONE,"Xóa thành công!" , ButtonType.OK);
        alert.setTitle("Thông báo");
        alert.showAndWait();
        Parent root =  FXMLLoader.load(getClass().getResource("/Home/MainView.fxml"));
        Address.getStage().setScene(new Scene(root));
    }


    @FXML
    private void editEvent(MouseEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit/EditView.fxml"));
         Parent root = loader.load();
         EditViewController ctrl = loader.getController();
         ctrl.initView(addr);
         stage.close();
         Address.getStage().setScene(new Scene(root));
    }


    @FXML
    private void DefaultEvent(MouseEvent event) throws ClassNotFoundException, SQLException, IOException {
        String  sql1 = "UPDATE address SET status = 0",
                sql2 = "UPDATE address SET status = 1 where addressid = '" + addr.getID() + "'";
        Connection con =  new CreateConnection().getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(sql1);
        st.execute(sql2);
        con.close();
        st.close();
        stage.close();
        Alert alert = new Alert(Alert.AlertType.NONE,"Cập nhật thành công!" , ButtonType.OK);
        alert.setTitle("Thông báo");
        alert.showAndWait();
        Parent root =  FXMLLoader.load(getClass().getResource("/Home/MainView.fxml"));
        Address.getStage().setScene(new Scene(root));
    }
    
}
