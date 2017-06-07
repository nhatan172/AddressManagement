package Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nhata
 */
public class CreateConnection {
    
    private Connection con;
    private final String driver = "org.h2.Driver";
    private final String dbUrl = "jdbc:h2:~/diachinh";
    public CreateConnection() throws ClassNotFoundException, SQLException{
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(dbUrl);
                    } catch (InstantiationException ex) {
            Logger.getLogger(CreateConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CreateConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// bắt lỗi đường link hỏng
    public Connection getConnection(){
        return con;
    }
}
