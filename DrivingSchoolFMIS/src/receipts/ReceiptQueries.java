package receipts;

import connection.SqlConnection;
import courses.Course;
import courses.CourseQueries;
import drivingschoolfmis.DrivingSchoolFMIS;
import java.sql.Connection;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import static settings.AlertClass.makeAlert;
import static settings.AlertClass.makePromptAlert;

public class ReceiptQueries {

    public Receipts getReceipt(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM receipts where receiptNo=?");
            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                return new Receipts(
                        rs.getString("studentID"),
                        rs.getString("date"),
                        rs.getString("dateOfPayment"),
                        rs.getString("amount"),
                        rs.getString("modeOfPayment"),
                        rs.getString("paymentOf"),
                        rs.getString("user"),
                        rs.getString("ref")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            makeAlert("error", ex + "");

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
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public boolean addReceipt(Receipts receipt) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO receipts (studentID, date, amount, modeOfPayment, paymentOf,"
                    + " user , reference, dateOfPayment) "
                    + "VALUES (?, NOW() , ?, ?, ?, ? ,? , ? )");
            pst.setString(1, receipt.getStudent());
            pst.setString(2, receipt.getAmount());
            pst.setString(3, receipt.getMop());
            pst.setString(4, receipt.getBpo());
            pst.setString(5, receipt.getUser());
            pst.setString(6, receipt.getRef());
            pst.setString(7, receipt.getDateOfReceipt());

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public ObservableList<Receipts> getReceiptsFor(String studentID) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Receipts> receipts = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * FROM receipts"
                    + " where studentID=? "
                    + "ORDER BY date DESC");
            pst.setString(1, studentID);
            rs = pst.executeQuery();
            while (rs.next()) {
                receipts.add(new Receipts(
                        rs.getString("receiptNo"),
                        rs.getString("date"),
                        rs.getString("amount"),
                        rs.getString("modeOfPayment"),
                        rs.getString("paymentOf"),
                        rs.getString("user"),
                        rs.getString("reference"),
                        rs.getString("dateOfPayment"),
                        "",
                        ""
                ));
            }
            return receipts;

        } catch (SQLException ex) {
            Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return receipts;
    }

    public ObservableList<Receipts> getReceipts(String studentID) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Receipts> receipts = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("Select * from receipts "
                    + "where studentID=? order by rowid DESC");
            pst.setString(1, studentID);
            rs = pst.executeQuery();

            while (rs.next()) {
                receipts.add(new Receipts(
                        rs.getString("studentID"),
                        rs.getString("date"),
                        rs.getString("dateOfPayment"),
                        rs.getString("amount"),
                        rs.getString("modeOfPayment"),
                        rs.getString("paymentOf"),
                        rs.getString("user"),
                        rs.getString("ref")
                ));
            }

            return receipts;

        } catch (SQLException ex) {
            Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return receipts;
    }

    public static int deleteReceipt(String id, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM receipts WHERE receiptNo=?");
                pst.setString(1, id);
                return pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                    Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return 404;
    }

    public ObservableList<Receipts> getNReceipts() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Receipts> receipts = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    "Select receiptNo, fullname, rn.date AS aDate, amount, modeOfPayment,"
                    + " paymentOf, user , reference, dateOfPayment "
                    + "from receipts AS rn INNER JOIN students ON rn.studentID=students.studentID "
                    + "order by fullname");
            rs = pst.executeQuery();
            while (rs.next()) {
                receipts.add(new Receipts(
                        rs.getString("receiptNo"),
                        rs.getString("aDate"),
                        rs.getString("dateOfPayment"),
                        rs.getString("amount"),
                        rs.getString("modeOfPayment"),
                        rs.getString("paymentOf"),
                        rs.getString("user"),
                        rs.getString("reference"),
                        rs.getString("fullname")
                ));
            }
            return receipts;

        } catch (SQLException ex) {
            Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return receipts;
    }

    public ObservableList<Receipts> getFeesReceipts() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Receipts> receipts = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    "Select receiptNo, fullname, rn.date AS aDate, amount, modeOfPayment,"
                    + " paymentOf, user , reference, dateOfPayment "
                    + "from receipts AS rn INNER JOIN students ON rn.studentID=students.studentID "
                    + "WHERE paymentOf = ?  "
                    + "order by fullname");

            pst.setString(1, "Fees");
            rs = pst.executeQuery();
            while (rs.next()) {
                receipts.add(new Receipts(
                        rs.getString("receiptNo"),
                        rs.getString("aDate"),
                        rs.getString("dateOfPayment"),
                        rs.getString("amount"),
                        rs.getString("modeOfPayment"),
                        rs.getString("paymentOf"),
                        rs.getString("user"),
                        rs.getString("reference"),
                        rs.getString("fullname")
                ));
            }
            return receipts;

        } catch (SQLException ex) {
            Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return receipts;
    }

    public static int deleteReceipt(ObservableList<Receipts> selected, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {
            for (int i = 0; i < selected.size(); i++) {

                try {
                    pst = conn.prepareStatement("DELETE FROM receipts WHERE receiptNo=?");
                    pst.setString(1, selected.get(i).getId());

                    pst.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
            return 101;
        }
        return 404;
    }

    public ObservableList<Receipts> getReceiptsForThisYear(String selectedYear) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Receipts> receipts = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    "Select receiptNo, fullname, rn.date AS aDate, amount, modeOfPayment, paymentOf, user , reference "
                    + "from receipts AS rn INNER JOIN students ON rn.studentID=students.studentID "
                    + "where aDate LIKE '2021' "
                    + "order by fullname");
            rs = pst.executeQuery();

            while (rs.next()) {
                receipts.add(new Receipts(
                        rs.getString("receiptNo"),
                        rs.getString("aDate"),
                        rs.getString("aDate"),
                        rs.getString("amount"),
                        rs.getString("modeOfPayment"),
                        rs.getString("paymentOf"),
                        rs.getString("user"),
                        rs.getString("ref"),
                        rs.getString("fullname")
                ));
            }
            return receipts;

        } catch (SQLException ex) {
            Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(ReceiptQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return receipts;
    }
}
