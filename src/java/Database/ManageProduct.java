/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Bean.Product;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author niponsarikan
 */
public class ManageProduct {

    private Connection conn;

    //  *** Information Can Change Depend on Computer Mysql Directories ^_^  eiei
    private String db_driver = "com.mysql.jdbc.Driver";
    private String db_url = "jdbc:mysql://localhost:3306/Photo?zeroDateTimeBehavior=convertToNull";
    private String db_user = "root";
    private String db_pass = "root";

    public ManageProduct() {
        try {
            Class.forName(db_driver);

            conn = DriverManager.getConnection(db_url, db_user, db_pass);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegLogDB.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void addProduct(Product photo) {
        try {
            PreparedStatement pro = conn.prepareStatement("insert into product values(default,?,?,now(),?,default,'Not',?,?,0)");
            PreparedStatement pID = conn.prepareStatement("select max(p_id) from product");
            PreparedStatement add = conn.prepareStatement("update product set p_address =? where p_id =?");
            pro.setString(1, photo.getName());
            pro.setDouble(2, photo.getPrice());
            pro.setString(3, photo.getDescription());
            pro.setInt(4, Integer.parseInt(photo.getCateID()));
            pro.setInt(5, Integer.parseInt(photo.getmID()));
            pro.executeUpdate();
            ResultSet rs = pID.executeQuery();
            rs.next();
            String url = rs.getString(1);
            add.setString(1, "./PhotoStore/" + photo.getmID() + "/" + url + ".jpg");
            add.setInt(2, Integer.parseInt(url));
            add.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Remove Product (Set to unavailable)
    public void removeProduct(String pID) {
        try {
            PreparedStatement del = conn.prepareStatement("update product set p_status ='No' where p_id =?");
            del.setInt(1, Integer.parseInt(pID));
            del.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Set Product to available in store
    public void enProduct(String pID) {
        try {
            PreparedStatement del = conn.prepareStatement("update product set p_status ='Yes' where p_id =?");
            del.setInt(1, Integer.parseInt(pID));
            del.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Delete photo from Database
    public void delProductData(String pID) {
        try {
            PreparedStatement url = conn.prepareStatement("select p_address from product where p_id =?");
            PreparedStatement del = conn.prepareStatement("delete from product where p_id=?");
            ManageProduct manage = new ManageProduct();
            url.setInt(1, Integer.parseInt(pID));
            ResultSet rs = url.executeQuery();
            rs.next();
            String address = rs.getString(1);
            del.setInt(1, Integer.parseInt(pID));
            del.executeUpdate();
            manage.delPhoto(address);

        } catch (SQLException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Use to delete Photo in folder
    public void delPhoto(String url) {
        try {
            File file = new File(url);

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static void main(String[] args) {

         //java.util.Date dt = new java.util.Date();
        //java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String currentTime = sdf.format(dt);
    }

}
