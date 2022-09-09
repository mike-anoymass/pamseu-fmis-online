/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

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
public class UserQueries {

    public ObservableList<User> getUsers() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("Select * from users order by date DESC");
            rs = pst.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("date"),
                        rs.getString("image"),
                        rs.getBoolean("isActive")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return users;
    }

    public User getUser(String username) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM users where username=?");
            pst.setString(1, username);
            rs = pst.executeQuery();

            while (rs.next()) {
                return new User(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("date"),
                        rs.getString("image"),
                        rs.getBoolean("isActive")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public boolean addUser(User user) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO users (username, firstname , lastname, phone, email, role, password, date, image, isActive) "
                    + "VALUES (?, ?, ?, ?, ?, ? ,?,NOW() , ? ,?)"
            );
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getFirstName());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getPhone());
            pst.setString(5, user.getEmail());
            pst.setString(6, user.getUsertype());
            pst.setString(7, user.getPassword());
            pst.setString(8, user.getImage());
            pst.setBoolean(9, user.isActive());

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public int countUsers() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int count = 0;
        try {
            pst = conn.prepareStatement("Select count(*) FROM users");

            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;

        } catch (SQLException ex) {
            Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return count;
    }

    public boolean editUser(User user) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("UPDATE users "
                    + "SET firstname=?, lastname=?, email=?, phone=?, role=?, password=?, image=?, isActive=? "
                    + " WHERE username=?");

            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(4, user.getPhone());
            pst.setString(3, user.getEmail());
            pst.setString(5, user.getUsertype());
            pst.setString(6, user.getPassword());
            pst.setString(7, user.getImage());
            pst.setBoolean(8, user.isActive());
            pst.setString(9, user.getUsername());

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return true;
    }

    public int deleteUser(String username, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM users WHERE username=?");
                pst.setString(1, username);
                return pst.executeUpdate();

            } catch (SQLException ex) {

                Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                    Logger.getLogger(UserQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return 404;
    }
}
