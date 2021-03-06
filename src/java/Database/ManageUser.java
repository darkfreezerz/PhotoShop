/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author niponsarikan
 */
public class ManageUser {
    
     private Connection conn;
    
    //  *** Information Can Change Depend on Computer Mysql Directories ^_^  eiei
    private String db_driver = "com.mysql.jdbc.Driver";
    private String db_url = "jdbc:mysql://localhost:3306/Photo?zeroDateTimeBehavior=convertToNull";
    private String db_user = "root";
    private String db_pass = "root";

    public ManageUser() {
        try {
            Class.forName(db_driver);

            conn = DriverManager.getConnection(db_url, db_user, db_pass);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegLogDB.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    //Enable Customer
    public void enCustomer(String cID){
         try {
             PreparedStatement en = conn.prepareStatement("update customer set c_status ='Yes' where c_id =?;");
             en.setInt(1,Integer.parseInt(cID));
         } catch (SQLException ex) {
             Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    //Enable Mechant
    public void enMerchant(String mID){
         try {
             PreparedStatement en = conn.prepareStatement("update merchant set m_status ='Yes' where m_id =?;");
             en.setInt(1,Integer.parseInt(mID));
         } catch (SQLException ex) {
             Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    //Remove Customer (Set to unavailable)
    public void removeCustomer(String cID) {
        try {
            PreparedStatement del = conn.prepareStatement("update customer set c_status ='No' where c_id =?");
            del.setInt(1, Integer.parseInt(cID));
            del.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Remove Merchant (Set to unavailable)
    public void removeMerchant(String mID) {
        try {
            PreparedStatement del = conn.prepareStatement("update merchant set m_status ='No' where m_id =?");
            del.setInt(1, Integer.parseInt(mID));
            del.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
