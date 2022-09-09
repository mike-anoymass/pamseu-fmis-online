package tests;

import connection.SqlConnection;
import discount.DiscountQueries;

import java.sql.Connection;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import login.LoginDocumentController;

public class TestQueries {

    public ObservableList<Test> getTests() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Test> tests = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT fullname, tests.id AS testID, "
                    + "testName, tests.date AS testDate, passOrFail, dateOfTest, user"
                    + " FROM tests "
                    + "INNER JOIN students ON students.studentID=tests.student "
                    + "ORDER BY fullname, dateOfTest, tests.date");
            rs = pst.executeQuery();

            while (rs.next()) {
                tests.add(
                        new Test(
                                rs.getString("testID"),
                                rs.getString("fullname"),
                                rs.getString("testName"),
                                rs.getString("testDate"),
                                rs.getString("dateOfTest"),
                                rs.getString("passOrFail"),
                                rs.getString("user")
                        )
                );
            }

            return tests;
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
                Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tests;
    }

    public ObservableList<Test> getTestsFor(String id) {
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
            Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return tests;
    }

    public int addTest(Test test) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("INSERT INTO "
                    + "tests (student, testName, date , dateOfTest, passOrFail, user)"
                    + " VALUES (?, ?, NOW(), ? ,?, ?)", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, test.getStudent());
            pst.setString(2, test.getTestName());
            pst.setString(3, test.getDateOfTest());
            pst.setString(4, test.getPassOrFail());
            pst.setString(5, LoginDocumentController.firstname + " " + LoginDocumentController.lastname);

            pst.execute();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
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
                Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public int deleteTest(String id, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM tests WHERE id=?");
                pst.setString(1, id);

                return pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                    Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return 404;

    }

    public int deleteMany(ObservableList<Test> selectedTests, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {

            for (int i = 0; i < selectedTests.size(); i++) {

                try {
                    pst = conn.prepareStatement("DELETE FROM tests WHERE id=?");
                    pst.setString(1, selectedTests.get(i).getId());

                    pst.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

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
                Logger.getLogger(TestQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 101;
        }
        return 404;
    }
}
