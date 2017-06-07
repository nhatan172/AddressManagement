/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edit;

import MainClass.Address;
import Model.*;
import Search.SearchViewController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author nhata
 */
public class EditViewController implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private Label province;
    @FXML
    private Label district;
    @FXML
    private Label ward;
    @FXML
    private TextField address;
    @FXML
    private Label title;
    @FXML
    private RadioButton buttonDefault;
    @FXML
    private Button buttonEdit;
    private AddressModel addr;
    private boolean bProvince = false, bDistrict = false,
                    bWard = false, bAddress = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCss();

    }
    private void initCss(){
        vbox.setStyle("-fx-background-color: white;"
                        +"-fx-border-width: 0.5 0.5 0.5 0.5;"
                        +"-fx-border-color: gray;");
        String css = " -fx-border-width: 0 0 1 0; " +" -fx-border-color: #b2bac9;";
        title.setStyle(" -fx-border-width: 0 0 2 0; " +" -fx-border-color: red;");
        buttonDefault.getStylesheets().add(getClass().getResource("radiobutton.css").toExternalForm());
        buttonEdit.setStyle("-fx-background-color: red");
        address.setStyle( "-fx-focus-color: red;"
                            + "-fx-faint-focus-color: #d3524422;");
        address.setStyle(css);
        ward.setStyle(css);
        province.setStyle(css);
        district.setStyle(css);
        address.setPromptText("Số nhà, Xóm, Tổ..v.v");
        address.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isEmpty())
                    addr.setAddress(null);
                else addr.setAddress(newValue);
            }
            
        });
    }
    public void initView(AddressModel add){
        String css1 = "-fx-border-width: 0 0 1 0; " +"-fx-border-color:  #b2bac9;";
        addr = add;
        if(add.getProvince() == null)
        {
            province.setText("Tỉnh/Thành phố");
            province.setStyle(css1);
            province.setTextFill(Paint.valueOf("#6e6767"));
            bProvince = false;
        }
        else{
            province.setText(add.getProvince().getIdent());
            bProvince = true;
        }
        if(add.getDistrict()== null)
        {
            district.setText("Thành phố/Quận/Huyện/Thị xã");
            district.setStyle(css1);
            district.setTextFill(Paint.valueOf("#6e6767"));
            bDistrict = false;
        }
        else{
            district.setText(add.getDistrict().getIdent());
            bDistrict = true;
        }
        if(add.getWard()== null)
        {
            ward.setText("Phường/Xã/Thị trấn");
            ward.setStyle(css1);
            bWard = false;
            ward.setTextFill(Paint.valueOf("#6e6767"));
        }
        else{
            ward.setText(add.getWard().getIdent());
            bWard = true;
        }
        if(add.getAddress()== null)
            bAddress = false;
        else{
            address.setText(add.getAddress());
            bAddress = true;
        }
   
        buttonDefault.setSelected(add.getStatus());
    }
    @FXML
    private void provinceClick(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Search/SearchView.fxml"));
        Parent root = loader.load();
        SearchViewController ctrl =  loader.getController();
        ctrl.setInfor(addr, 1);
        ctrl.initlist();
        Address.getStage().setScene(new Scene(root));
    }
    @FXML
    private void districtClick(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
        if(bProvince == false)
        {
            Alert alert = new Alert(Alert.AlertType.NONE ,"Chưa chọn Tỉnh/Thành phố." , ButtonType.OK);
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Search/SearchView.fxml"));
        Parent root = loader.load();
        SearchViewController ctrl =  loader.getController();
        ctrl.setInfor(addr, 2);
        ctrl.initlist();
        Address.getStage().setScene(new Scene(root));
    }
    @FXML
    private void wardClick(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
        if(bProvince == false)
        {
            Alert alert = new Alert(Alert.AlertType.NONE ,"Chưa chọn Tỉnh/Thành phố." , ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else if(bDistrict == false){
            Alert alert = new Alert(Alert.AlertType.NONE ,"Chưa chọn Thành phố/Quận/Huyện/Thị Xã." , ButtonType.OK);
            alert.showAndWait();
            return;

        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Search/SearchView.fxml"));
        Parent root = loader.load();
        SearchViewController ctrl =  loader.getController();
        ctrl.setInfor(addr, 3);
        ctrl.initlist();
        Address.getStage().setScene(new Scene(root));
    }
    @FXML
    private void imageClick(MouseEvent event) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("/Home/MainView.fxml"));
            Address.getStage().setScene(new Scene(root));        
    }
    @FXML
    private void clickEdit(MouseEvent event) throws ClassNotFoundException, SQLException, IOException{
        if(address.getText().isEmpty())
            bAddress = false;
        if(bProvince == false){
            Alert alert = new Alert(Alert.AlertType.NONE, "Bạn chưa chọn Tỉnh/Thành phố.", ButtonType.OK);
            alert.setTitle("Thông báo");
            alert.showAndWait();
        }
        else if(bDistrict == false ){
            Alert alert = new Alert(Alert.AlertType.NONE, "Bạn chưa chọn Thành phố/Quận/Huyện/Thị xã.", ButtonType.OK);
            alert.setTitle("Thông báo");
            alert.showAndWait();
        }
        else if(bWard == false){
            Alert alert = new Alert(Alert.AlertType.NONE, "Bạn chưa chọn Phường/Xã/Thị trấn.", ButtonType.OK);
            alert.setTitle("Thông báo");
            alert.showAndWait();
        }
        else if(address.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.NONE, "Bạn cần phải nhập địa chỉ chi tiết", ButtonType.OK);
            alert.setTitle("Thông báo");
            alert.showAndWait();
        }
        else{
            String sql1 = "UPDATE address set name = '" + address.getText()+"'"+" where addressid ='" + addr.getID()+"'";
            String sql2 = "select ward.wardid from district, province, ward " +
                "where ward.districtid = district.districtid and district.provinceid = province.provinceid"
                    +" AND district.name = ? AND province.name = ? AND ward.name = ? and ward.type = ?";
            String sql3 = "UPDATE address set wardid = ? where addressid ='" + addr.getID() +"'";
            Connection con = new CreateConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(sql2);
            Statement st = con.createStatement();
            st.executeUpdate(sql1);
            ps.setString(1, addr.getDistrict().getName());
            ps.setString(2, addr.getProvince().getName());
            ps.setString(3, addr.getWard().getName());
            ps.setString(4, addr.getWard().getType());
            ResultSet rs = ps.executeQuery();
            ps = con.prepareStatement(sql3);
            if(rs.next()){
                ps.setString(1, rs.getString(1));
                ps.executeUpdate();
            }
            
            if(buttonDefault.isSelected() != addr.getStatus()){
                String  sql4 = "UPDATE address SET status = 0",
                sql5 = "UPDATE address SET status = 1 where addressid = '" + addr.getID() + "'";
                if(buttonDefault.isSelected() == true){
                    st = con.createStatement();
                    st.executeUpdate(sql4);
                    st.executeUpdate(sql5);
                }
                else
                {
                    st = con.createStatement();
                    st.executeUpdate(sql4);
                }
            }
            con.close();
            st.close();
            rs.close();
            ps.close();
            
            Alert alert = new Alert(Alert.AlertType.NONE, "Cập nhật thành công.", ButtonType.OK);
            alert.setTitle("Thông báo");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/Home/MainView.fxml"));
            Address.getStage().setScene(new Scene(root));
        }
    }
}
