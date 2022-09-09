/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trail;

import connection.SqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ANOYMASS
 */
public class TrailQueries {
    public ObservableList<Trail> getAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Trail> list = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * "
                    + "FROM trail t "
                    + "INNER JOIN users u ON t.username=u.username "
                    + "ORDER BY t.id DESC");
            rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new Trail(
                        rs.getString("firstname") + " " + rs.getString("lastname"),
                        rs.getString("role"),
                        rs.getString("phone"),
                        rs.getString("date")
                )
                );
            }

            return list;
        } catch (SQLException ex) {
            Logger.getLogger(TrailQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }

                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TrailQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return list;
    }
    
     public boolean add(String username) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;

        try {
            pst = conn.prepareStatement("INSERT INTO trail (username, date) VALUES (?, NOW())");
            pst.setString(1, username);
           
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(TrailQueries.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }

                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TrailQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }
}
