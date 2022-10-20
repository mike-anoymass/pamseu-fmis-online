/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles;

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
public class VehicleQueries {

    static boolean addVehicle(Vehicle vehicle) {
       PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO vehicle (regNo, cof_date , insurance_date) "
                    + "VALUES (?, ?, ?)"
            );
            pst.setString(1, vehicle.getRegNo());
            pst.setString(2, vehicle.getCofDate());
            pst.setString(3, vehicle.getInsuranceDate());

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    static boolean editVehicle(Vehicle vehicle, int id) {
       PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("UPDATE vehicle "
                    + "SET regNo=?, cof_date=?, insurance_date=? "
                    + " WHERE id=?");

            pst.setString(1, vehicle.getRegNo());
            pst.setString(2, vehicle.getCofDate());
            pst.setString(3, vehicle.getInsuranceDate());
            pst.setInt(4, id);

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return true;
    }

    public ObservableList<Vehicle> getVehicles() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("Select * from vehicle order by id DESC");
            rs = pst.executeQuery();

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("regNo"),
                        rs.getString("cof_date"),
                        rs.getString("insurance_date"),
                        rs.getString("date")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return vehicles;
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
            Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return count;
    }
    
    public int deleteVehicle(int id, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM vehicle WHERE id=?");
                pst.setInt(1, id);
                return pst.executeUpdate();

            } catch (SQLException ex) {

                Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                    Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return 404;
    }

    public Vehicle getVehicle(String regNo) {
         PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM vehicle where regNo=?");
            pst.setString(1, regNo);
            rs = pst.executeQuery();

            while (rs.next()) {
                return new Vehicle(
                        rs.getInt("id"),
                        rs.getString("regNo"),
                        rs.getString("cof_date"),
                        rs.getString("insurance_date"),
                        rs.getString("date")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(VehicleQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

}
