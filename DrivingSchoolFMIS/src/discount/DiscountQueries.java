package discount;

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

public class DiscountQueries {

    public ObservableList<Discount> getDiscounts() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Discount> discount = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * FROM discounts");
            rs = pst.executeQuery();

            while (rs.next()) {
                discount.add(new Discount(
                        rs.getString("student"),
                        rs.getString("amount"))
                );
            }

            return discount;
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
                Logger.getLogger(DiscountQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return discount;
    }

    public Discount getDiscount(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        Discount discount = null;

        try {
            pst = conn.prepareStatement("SELECT * FROM discounts where student=?");
            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                discount = new Discount(
                        rs.getString("student"),
                        rs.getString("amount")
                );

                return discount;
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
                Logger.getLogger(DiscountQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return discount;
    }

    public boolean addDiscount(Discount discount) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;

        try {
            pst = conn.prepareStatement("INSERT INTO discounts (student, amount) VALUES (?, ?)");
            pst.setString(1, discount.getStudent());
            pst.setString(2, discount.getAmount());
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
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
                Logger.getLogger(DiscountQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public boolean editDiscount(String id, String amount) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;

        try {
            pst = conn.prepareStatement("UPDATE discounts "
                    + "SET amount=?"
                    + " WHERE student=?");
            pst.setString(1, amount);
            pst.setString(2, id);
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(DiscountQueries.class.getName()).log(Level.SEVERE, null, ex);
            makeAlert("error", "Failed to edit Discount -> " + ex);

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
                Logger.getLogger(DiscountQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public void deleteDiscount(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("DELETE FROM discounts WHERE student=?");
            pst.setString(1, id);

            pst.executeUpdate();

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
                Logger.getLogger(DiscountQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
