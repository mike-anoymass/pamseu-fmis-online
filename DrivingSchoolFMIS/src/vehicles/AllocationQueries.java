/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles;

import commision.CommissionQueries;
import connection.SqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tests.Test;

/**
 *
 * @author ANOYMASS
 */
public class AllocationQueries {

    public boolean addAllocation(Allocation allocation) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO allocations (student, pending, vehicle, staff, date, time, less, bal) "
                    + "VALUES (?, ?,?, ?, ?, ?, ?, ?)"
            );
            pst.setInt(1, allocation.getStudentID());
            pst.setInt(2, allocation.getPendingStudentID());
            pst.setString(3, allocation.getVehicle());
            pst.setInt(4, allocation.getStaff());
            pst.setString(5, allocation.getDate());
            pst.setString(6, allocation.getTime());
            pst.setInt(7, allocation.getLess());
            pst.setInt(8, allocation.getBal());

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
                Logger.getLogger(AllocationQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }
    
    public ObservableList<Allocation> getAllocationFor(int staff, String vehicle, String date) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Allocation> allocations = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * FROM allocations a "
                    + " inner join students s on a.student=s.studentID "
                    + " inner join vehicle v on v.regNo=a.vehicle "
                    + " inner join employees st on st.id=a.staff "
                    + " inner join students stu on stu.studentID = a.pending "
                    + " where a.staff=? and a.vehicle=? and a.date=? "
                    + " ORDER BY a.time");
            pst.setInt(1, staff);
            pst.setString(2, vehicle);
            pst.setString(3, date);
            
            rs = pst.executeQuery();

            while (rs.next()) {
                allocations.add(
                        new Allocation(
                                rs.getInt("a.student"),
                                rs.getInt("a.pending"),
                                rs.getInt("a.staff"),
                                rs.getString("a.vehicle"),
                                rs.getString("a.time"), 
                                rs.getString("a.dateAdded"),
                                rs.getInt("bal"),
                                rs.getInt("less"), 
                                rs.getString("s.fullname"),
                                rs.getString("s.phone"),
                                rs.getString("stu.fullname")
                        )
                );
            }

            return allocations;
        } catch (SQLException ex) {
            Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(AllocationQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return allocations;
    }
    
    public boolean update(Allocation allocation) {
       PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("UPDATE allocations "
                    + "SET less=?, bal=?, student=?, pending=? "
                    + "WHERE date=? and staff=? and vehicle=? and time=? ");

            pst.setInt(1, allocation.getLess());
            pst.setInt(2, allocation.getBal());
            pst.setInt(3, allocation.getStudentID());
            pst.setInt(4, allocation.getPendingStudentID());
            pst.setString(5, allocation.getDate());
            pst.setInt(6, allocation.getStaff());
            pst.setString(7, allocation.getVehicle());
            pst.setString(8, allocation.getTime());

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AllocationQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(AllocationQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return true;
    }
}
