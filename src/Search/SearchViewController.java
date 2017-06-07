package Search;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Edit.EditViewController;
import MainClass.Address;
import Model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author nhata
 */
public class SearchViewController implements Initializable {
    
    @FXML
    private VBox vbox;
    @FXML
    private Label title;
    @FXML
    private TextField textfd;
    @FXML
    private ListView<String> list;
    private ObservableList<String> Data = FXCollections.observableArrayList();
    private AddressModel address;
    private int setSql;// setSql == 1 for Search Province
                       // setSql == 2 for Search District
                       // setSql == 3 for Search Ward
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            // TODO
            init();    
    
        
        
    }  
    public void setInfor(AddressModel a, int i){
        address = a;
        setSql = i;
    }
    public void initlist() throws ClassNotFoundException, SQLException{
        String sql = setSqlString(setSql);
        String s;
        char temp = ' ';
        Connection con = new CreateConnection().getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
       
        while(rs.next()){
            s = rs.getString(1)+ " " +rs.getString(2);
            if(s.charAt(0)>='A' && s.charAt(0)<='Z')
            {
                if(s.charAt(0)!=temp && Data.indexOf(Character.toString(s.charAt(0))) == -1)
                {
                    Data.add(Character.toString(s.charAt(0)));
                    temp = s.charAt(0);
                }
            }
            Data.add(s);
        }
        VNCodes all = new VNCodes();
        FXCollections.sort(Data,new Comparator<String>(){
        @Override
         public int compare(String o1,String o2){
            return all.generator(o1).compareTo(all.generator(o2));
        }       
    });
        textfd.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(textfd.getText().length() >30)
                {
                    String s =textfd.getText().substring(0,30);
                    textfd.setText(s);
                }
            }
            
        });
        con.close();
        st.close();
        rs.close();
        list.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue){
                    Platform.runLater(new Runnable(){
                public void run(){
                    list.getSelectionModel().select(-1);
                }
            });
            }
        });
        FilteredList<String> filteredData = new FilteredList<>(Data, p -> true);
        list.setItems(filteredData);
         list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> arg0) {
                return new ListCell<String>() {
                    
                    
                    @Override
                    protected void updateItem(String item, boolean bln) {
                        super.updateItem(item, bln);
                        if(item == null){
                            setGraphic(null);
                        }
                        if (item != null && item.length()>1) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddressUnit.fxml"));
                                HBox h = loader.load();
                                h.setStyle("-fx-border-color: #b2bac9;" +" -fx-border-width: 1px;");
                                AddressUnitController ctr = loader.getController();
                                ctr.init(item,address,setSql);
                                setGraphic(h);
                            } catch (IOException ex) {
                                Logger.getLogger(SearchViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (item != null && item.length()==1) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddressCoreUnit.fxml"));
                                HBox h = loader.load();
                                h.setStyle("-fx-border-color: #b2bac9;" +" -fx-border-width: 1px;");
                                AddressCoreUnitController ctr = loader.getController();
                                ctr.init(item);
                                setGraphic(h);
                            } catch (IOException ex) {
                                Logger.getLogger(SearchViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                };
                        }
        }
        );

        textfd.textProperty().addListener((observable, oldValue , newValue) ->{
            filteredData.setPredicate(sss -> {
                if(newValue == null && newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(sss.toLowerCase().contains(lowerCaseFilter))
                    return true;
                return false;
            });
        });
              
        list.getStylesheets().add(getClass().getResource("/Search/ListViewCSS.css").toExternalForm());

           
                }
                        
                        
         
    
    private void init(){
        vbox.setStyle("-fx-background-color: white;"
                        +"-fx-border-width: 0.5 0.5 0.5 0.5;"
                        +"-fx-border-color: gray;");
        title.setStyle(" -fx-border-width: 0 0 2 0; " +
                " -fx-border-color: #ff0000; " +
                "-fx-text-fill: red;" + "-fx-opacity: 1.0;");

        textfd.setStyle(    "-fx-focus-color: #d35244;"
                           + "-fx-faint-focus-color: #d3524422;"
                           +"-fx-highlight-fill: -fx-accent;"
                            +"-fx-highlight-text-fill: white;"
                            + "-fx-background-image:url('/Search/search-icon.png'); "
                            + " -fx-background-repeat: no-repeat;"
                             +" -fx-background-position:  right 7px center ;"
                            +  "-fx-background-size: 25px 25px;"
                            + " -fx-background-repeat: no-repeat;"
                            +"-fx-background-radius: 10, 10, 10,10;"
                            +" -fx-text-box-border: red; "
                            +" -fx-border-width: 2 2 2 0; " );

    }
    private String setSqlString(int i){
        String sql = "";
        switch(i)
        {
            case 1 : 
                sql = "SELECT type, name from province";
                break;
            case 2 :
                sql = "SELECT district.type, district.name from district, province where"
                        +" district.provinceid = province.provinceid AND "
                        + " province.name = '" +address.getProvince().getName() +"'";
                break;
            case 3 :
                sql = "SELECT ward.type, ward.name from ward,district,province where "
                        + "district.provinceid = province.provinceid AND district.districtid = ward.districtid "
                        +" AND  province.name = '" +address.getProvince().getName() +"' AND "
                        + " district.name = '" + address.getDistrict().getName() + "'";
                break;
        }
        return sql;
    }
    @FXML
    private void CloseSearch(MouseEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit/EditView.fxml"));
        Parent root = loader.load();
        Address.getStage().setScene(new Scene(root));
        EditViewController ctrl = loader.getController();
        ctrl.initView(address);
                
    }
}
