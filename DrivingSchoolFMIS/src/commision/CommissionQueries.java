/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commision;

import connection.SqlConnection;
import discount.DiscountQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static settings.NotificationClass.showNotification;
import tests.Test;

/**
 *
 * @author ANOYMASS
 */
public class CommissionQueries {

    String sql = "SELECT * "
            + "FROM commisions c "
            + "JOIN employees e ON e.id=c.employee "
            + "JOIN tests t on t.id=c.test "
            + "JOIN students s on s.studentID=t.student ";

    public static int payCommisions(ObservableList<Commission> selected, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action & conn != null) {

            try {

                for (int i = 0; i < selected.size(); i++) {
                    pst = conn.prepareStatement("UPDATE commisions SET isPaid=?,"
                            + " datePaid=NOW() WHERE test=?");
                    pst.setBoolean(1, true);
                    pst.setInt(2, selected.get(i).getTestID());
                    pst.executeUpdate();
                }

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

                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return 101;
    }

    static int unPayCommisions(ObservableList<Commission> selected, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action & conn != null) {

            try {

                for (int i = 0; i < selected.size(); i++) {
                    pst = conn.prepareStatement("UPDATE commisions "
                            + "SET datePaid=NULL, isPaid=? WHERE test=?");
                    pst.setBoolean(1, false);
                    pst.setInt(2, selected.get(i).getTestID());

                    pst.executeUpdate();
                }

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

                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return 101;

    }

    public ObservableList<Test> getCommissionFor(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Test> tests = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * FROM tests"
                    + " where student=? "
                    + "ORDER BY dateOfTest DESC, date, testName, passOrFail");
            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                tests.add(
                        new Test(
                                rs.getString("id"),
                                rs.getString("student"),
                                rs.getString("testName"),
                                rs.getString("date"),
                                rs.getString("dateOfTest"),
                                rs.getString("passOrFail"),
                                rs.getString("user")
                        )
                );
            }

            return tests;
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
                Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return tests;
    }

    public int addCommission(Commission commission) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        try {
            pst = conn.prepareStatement("INSERT INTO "
                    + "commisions (test, employee, commision, isPaid)"
                    + " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, commission.getTestID());
            pst.setString(2, commission.getStaffID());
            pst.setDouble(3, commission.getCommision());
            pst.setBoolean(4, commission.isPaid());

            pst.execute();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                showNotification("Commision Added Successfully");
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DiscountQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    ObservableList<Commission> getCommisions() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Commission> comm = FXCollections.observableArrayList();
        String status = "";

        try {
            pst = conn.prepareStatement(sql + " ORDER BY isPaid, e.fullname");
            rs = pst.executeQuery();

            while (rs.next()) {
                status = rs.getBoolean("isPaid") ? "Paid" : "Not Paid";
                comm.add(
                        new Commission(
                                rs.getInt("t.id"),
                                rs.getString("e.id"),
                                rs.getDouble("commision"),
                                rs.getBoolean("isPaid"),
                                rs.getString("s.fullname"),
                                rs.getString("e.fullname"),
                                rs.getString("dateOfTest"),
                                rs.getString("datePaid"),
                                rs.getString("t.user"),
                                status
                        )
                );
            }

            return comm;
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
                Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return comm;
    }

    public ObservableList<Commission> getPaidCommisions() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Commission> comm = FXCollections.observableArrayList();
        String status = "";

        try {
            pst = conn.prepareStatement(sql + " where isPaid=? ORDER BY isPaid, e.fullname");
            pst.setBoolean(1, true);

            rs = pst.executeQuery();

            while (rs.next()) {
                status = rs.getBoolean("isPaid") ? "Paid" : "Not Paid";
                comm.add(
                        new Commission(
                                rs.getInt("t.id"),
                                rs.getString("e.id"),
                                rs.getDouble("commision"),
                                rs.getBoolean("isPaid"),
                                rs.getString("s.fullname"),
                                rs.getString("e.fullname"),
                                rs.getString("dateOfTest"),
                                rs.getString("datePaid"),
                                rs.getString("t.user"),
                                status
                        )
                );
            }

            return comm;
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
                Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return comm;
    }

    ObservableList<Commission> getUnpaidCommisions() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Commission> comm = FXCollections.observableArrayList();
        String status = "";

        try {
            pst = conn.prepareStatement(sql + " where isPaid=? ORDER BY isPaid, e.fullname");
            pst.setBoolean(1, false);

            rs = pst.executeQuery();

            while (rs.next()) {
                status = rs.getBoolean("isPaid") ? "Paid" : "Not Paid";
                comm.add(
                        new Commission(
                                rs.getInt("t.id"),
                                rs.getString("e.id"),
                                rs.getDouble("commision"),
                                rs.getBoolean("isPaid"),
                                rs.getString("s.fullname"),
                                rs.getString("e.fullname"),
                                rs.getString("dateOfTest"),
                                rs.getString("datePaid"),
                                rs.getString("t.user"),
                                status
                        )
                );
            }

            return comm;
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
                Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return comm;
    }

    public double getRate() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Commission> comm = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("Select * from commisionrate");

            rs = pst.executeQuery();

            while (rs.next()) {
                return rs.getDouble("rate");
            }

            return 0.0;
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
                Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return 0.0;
    }

    public void setRate(double rate) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (conn != null) {

            try {
                pst = conn.prepareStatement("UPDATE commisionrate SET rate=?"
                        + " where id = ?");
                pst.setDouble(1, rate);
                pst.setInt(2, 1);
                pst.executeUpdate();
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

                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(CommissionQueries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

}
