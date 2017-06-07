/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import Model.AddressModel;
import Model.CreateConnection;
import Model.Identification;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author nhata
 */
public class MainViewController implements Initializable {
   //ythis is comment
    /**
     * Initializes the controller class.
     */
    @FXML private ScrollPane scrollP;
    @FXML private VBox vbox;
    @FXML private Label Title;
    @FXML private VBox AddrShow;
    @FXML private Button ButtonAdd;
    private ObservableList<AddressModel> AddressData = FXCollections.observableArrayList();
    private Connection con;
    private Statement st;
    private PreparedStatement pr;
    private ResultSet rs;
    @Override
        public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            init();
            getData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void init(){
        scrollP.setStyle( "-fx-focus-color: white;");
        //AddrShow.setStyle("  -fx-focus-color: transparent");
        vbox.setStyle("-fx-background-color: white;"
                        +"-fx-border-width: 0.5 0.5 0.5 0.5;"
                        +"-fx-border-color: gray;");
        Title.setDisable(true);
        Title.setStyle(" -fx-border-width: 0 0 2 0; " +
                " -fx-border-color: #ff0000; " +
                "-fx-text-fill: red;" + "-fx-opacity: 1.0;");
        ButtonAdd.setStyle("-fx-background-color: #ff0000");
    }
    private void getData() throws ClassNotFoundException, SQLException, IOException{
        String sql = "select address.name , ward.type, ward.name, district.type,district.name,province.type, province.name, address.status, addressid "
               + "from address, district, province, ward " +
                "where address.wardid = ward.wardid and ward.districtid = district.districtid and district.provinceid = province.provinceid"
                +" order by addressid ASC";
        con = new CreateConnection().getConnection();
        st = con.createStatement();
        rs = st.executeQuery(sql);
        while(rs.next()){
            AddressModel a = new AddressModel();
            a.setAddress(rs.getString(1));
            a.setWard(new Identification(rs.getString(2), rs.getString(3)));
            a.setDistrict(new Identification(rs.getString(4), rs.getString(5)));
            a.setProvince(new Identification(rs.getString(6), rs.getString(7))); 
            a.setStatus(Integer.parseInt(rs.getString(8)));
            a.setID(rs.getString(9));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/Unit.fxml"));
            HBox h = loader.load();
            h.setStyle("-fx-border-color: #b2bac9;" +" -fx-border-width: 1px;"
                        +"-fx-background-color: white;");
            UnitController ctr = loader.getController();
            ctr.intit(a);
            AddrShow.getChildren().add(h);
        }
        con.close();
        rs.close();
        st.close();
    }
    @FXML
    private void themDiaChi(MouseEvent e){
        Alert alert = new Alert(Alert.AlertType.NONE ,"This function isn't required!" , ButtonType.OK);
        alert.setTitle("ERROR");
        alert.showAndWait();
    }
}
