package payments;

import connection.SqlConnection;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static settings.AlertClass.makeAlert;

public class PaymentQueries {

    public ObservableList<Expense> getAllExpenses() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Expense> expenses = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * FROM expenses ORDER BY id DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getString("name"),
                        rs.getString("date"))
                );
            }
            return expenses;
        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return expenses;
    }

    public ObservableList<Payment> getAllPayments() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Payment> payments = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    "SELECT * FROM payments "
                    + "ORDER BY date DESC, expense");
            rs = pst.executeQuery();

            while (rs.next()) {
                payments.add(new Payment(
                        rs.getString("id"),
                        rs.getString("expense"),
                        rs.getString("date"),
                        rs.getString("dateOfPayment"),
                        rs.getString("amount"),
                        rs.getString("mode"),
                        rs.getString("ref"),
                        rs.getString("mirage"),
                        rs.getString("user")
                )
                );
            }

            return payments;
        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return payments;
    }

    public Payment getPayment(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM payments where id=?");
            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                return new Payment(
                        rs.getString("id"),
                        rs.getString("expense"),
                        rs.getString("date"),
                        rs.getString("dateOfPayment"),
                        rs.getString("amount"),
                        rs.getString("mode"),
                        rs.getString("ref"),
                        rs.getString("mirage"),
                        rs.getString("user")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public boolean addPayment(Payment p) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO payments (expense, date, amount, mode, dateOfPayment, mirage,"
                    + " user , ref) "
                    + "VALUES (?, NOW(), ?, ?, ?, ? ,?, ?)");
            pst.setString(1, p.getExpense());
            pst.setString(2, p.getAmount());
            pst.setString(3, p.getMode());
            pst.setString(4, p.getDateOfPayment());
            pst.setString(5, p.getMirage());
            pst.setString(6, p.getUser());
            pst.setString(7, p.getRef());
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            makeAlert("error", "Error Adding payment\nThis employee name does not exist in expense table\n"
                    + "Please Add this employee name on the list of expense inorder to record payments");
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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public Expense getExpense(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM expenses where name=?");
            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                return new Expense(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("date")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public static boolean addExpense(Expense e) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        try {
            pst = conn.prepareStatement(
                    "INSERT INTO expenses (name, date) "
                    + " VALUES (?,  NOW())");
            pst.setString(1, e.getName());

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public static int deleteExpense(String name, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM expenses WHERE name=?");
                pst.setString(1, name);
                return pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return 404;
    }

    public static int deletePayment(String id, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM payments WHERE id=?");
                pst.setString(1, id);
                return pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                    Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return 404;
    }

    public ObservableList<Payment> getPaymentsFor(String expense) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Payment> payments = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    "SELECT * FROM payments "
                    + "WHERE expense LIKE ?"
                    + "ORDER BY date DESC");

            pst.setString(1, "%" + expense + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
                payments.add(new Payment(
                        rs.getString("id"),
                        rs.getString("expense"),
                        rs.getString("date"),
                        rs.getString("dateOfPayment"),
                        rs.getString("amount"),
                        rs.getString("mode"),
                        rs.getString("ref"),
                        rs.getString("mirage"),
                        rs.getString("user")
                )
                );
            }

            return payments;
        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return payments;
    }

    public boolean editPayment(Payment p, String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "UPDATE payments "
                    + "SET expense=?, amount=?, mode=?, ref=? , dateOfPayment=?, mirage=?"
                    + "WHERE id=?");
            pst.setString(1, p.getExpense());
            pst.setString(2, p.getAmount());
            pst.setString(3, p.getMode());
            pst.setString(4, p.getRef());
            pst.setString(5, p.getDateOfPayment());
            pst.setString(6, p.getMirage());
            pst.setString(7, id);

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(PaymentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }
}
