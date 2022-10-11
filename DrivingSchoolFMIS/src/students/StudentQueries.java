/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package students;

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
public class StudentQueries {

    private final String studentAndGuardianQuery = "Select "
            + "students.studentID AS student_id, fullname, postalAddress, students.phone AS phone, course, date,"
            + " duration, gender, trn, dateRegistered, anyDiscount,"
            + " anyGovernmentFee, graduated, id, addedBy, "
            + "g.phone AS gPhone,physicalAddress, name "
            + "FROM students INNER JOIN studentnextofkins AS g ON students.studentID=g.studentID "
            ;

    /*public ObservableList<Student> getStudentsWithFees() {
         PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Student> students = FXCollections.observableArrayList();
                
        try {
            pst = conn.prepareStatement(
                    "SELECT studentID, fullname, postalAddress, phone, "
                    + "email, course, qualification, students.date AS dateAdded, students.duration AS courseType, fees  "
                    + "FROM students "
                    + "INNER JOIN courseDuration ON students.course=courseDuration.courseID"
                    + " and students.duration=courseDuration.duration");
            rs = pst.executeQuery();
            
            while(rs.next()){
                students.add(new Student(
                        studentAndGuardianQuery,
                        studentAndGuardianQuery,
                        studentAndGuardianQuery,
                        studentAndGuardianQuery, 
                        studentAndGuardianQuery, 
                        studentAndGuardianQuery,
                        studentAndGuardianQuery,
                        true,
                        true,
                        true,
                        studentAndGuardianQuery, 
                        studentAndGuardianQuery, 
                        studentAndGuardianQuery, 
                        studentAndGuardianQuery, 
                        studentAndGuardianQuery, 
                        studentAndGuardianQuery)
                );
            }
            
            return students;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }*/
    public ObservableList<Student> getStudents() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(studentAndGuardianQuery + " ORDER BY students.studentID DESC");
            rs = pst.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("course"),
                        rs.getString("duration"),
                        rs.getString("date"),
                        rs.getString("dateRegistered"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        rs.getString("gPhone"),
                        rs.getString("addedBy")
                ));

            }

            return students;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }

    public ObservableList<Student> getStudentsWithDiscount() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    studentAndGuardianQuery
                    + " WHERE anyDiscount=?"
                    + " order by date DESC");
            pst.setBoolean(1, true);
            rs = pst.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("course"),
                        rs.getString("duration"),
                        rs.getString("date"),
                        rs.getString("dateRegistered"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        rs.getString("gPhone"),
                        rs.getString("addedBy")
                ));

            }
            return students;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }

    public ObservableList<Student> getStudentsGraduated() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    studentAndGuardianQuery
                    + " WHERE graduated=?"
                    + " order by date DESC");
            pst.setBoolean(1, true);
            rs = pst.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("course"),
                        rs.getString("duration"),
                        rs.getString("date"),
                        rs.getString("dateRegistered"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        rs.getString("gPhone"),
                        rs.getString("addedBy")
                ));

            }
            return students;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return students;
    }

    public ObservableList<Student> getStudentsNotGraduated() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    studentAndGuardianQuery
                    + " WHERE graduated=? "
                    + " order by date DESC ");
            pst.setBoolean(1, false);
            rs = pst.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("course"),
                        rs.getString("duration"),
                        rs.getString("date"),
                        rs.getString("dateRegistered"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        rs.getString("gPhone"),
                        rs.getString("addedBy")
                ));

            }

            return students;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }

    public ObservableList<Student> getStudentsWithGvtFee() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    studentAndGuardianQuery
                    + " WHERE anyGovernmentFee=?"
                    + " order by date DESC");
            pst.setBoolean(1, true);
            rs = pst.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("course"),
                        rs.getString("duration"),
                        rs.getString("date"),
                        rs.getString("dateRegistered"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        rs.getString("gPhone"),
                        rs.getString("addedBy")
                ));

            }

            return students;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return students;
    }

    public Student getStudent(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    studentAndGuardianQuery + " where students.studentID=?");
            pst.setString(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                return new Student(
                        rs.getString("student_id"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("course"),
                        rs.getString("duration"),
                        rs.getString("date"),
                        rs.getString("dateRegistered"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        rs.getString("gPhone"),
                        rs.getString("addedBy")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Student getStudentWithoutGuardian(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("select * from students where studentID=?");
            pst.setString(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                return new Student(
                        rs.getString("studentID"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("dateRegistered"),
                        rs.getString("gender"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        rs.getString(""),
                        "",
                        "",
                        "",
                        rs.getString("course"),
                        rs.getString("duration")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    static boolean addStudent(Student student) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO students (fullname,gender, phone,"
                    + "postalAddress,course , duration, trn, dateRegistered,date,"
                    + "anyDiscount,anyGovernmentFee, graduated, addedBy) "
                    + "VALUES (?, ?, ?, ?, ?, ? ,? , ?, NOW(), ?, ? ,?, ?)");
            pst.setString(1, student.getStudent_name());
            pst.setString(2, student.getGender());
            pst.setString(3, student.getStudent_phone());
            pst.setString(4, student.getStudent_address());
            pst.setString(5, student.getCourse());
            pst.setString(6, student.getCourseType());
            pst.setString(7, student.getTrn());
            pst.setString(8, student.getDateRegistered());
            pst.setBoolean(9, student.isAnyDiscount());
            pst.setBoolean(10, student.isAnyGovtFee());
            pst.setBoolean(11, student.isGraduated());
            pst.setString(12, login.LoginDocumentController.firstname + " " + login.LoginDocumentController.lastname);

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public void addGuardian(Guardian guardian) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO studentnextofkins (studentID, phone, physicalAddress, name) "
                    + "VALUES (?, ?, ?, ?)");
            pst.setString(1, guardian.getStudent());
            pst.setString(2, guardian.getGuardian_phone());
            pst.setString(3, guardian.getGuardian_physicalAddress());
            pst.setString(4, guardian.getGuardian_name());

            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static int deleteStudents(ObservableList<Student> selectedStudents, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action & conn != null) {

            try {
                for (int i = 0; i < selectedStudents.size(); i++) {

                    pst = conn.prepareStatement("DELETE FROM students WHERE studentID=?");
                    pst.setString(1, selectedStudents.get(i).getStudent_id());

                    pst.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                    Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return 101;

    }

    public boolean editStudent(Student student, String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("UPDATE students "
                    + "SET fullname=?,gender=?, phone=?,"
                    + "postalAddress=?, trn=?, dateRegistered=?, "
                    + "anyDiscount=?, anyGovernmentFee=?, graduated=?, updatedOn=NOW() "
                    + " WHERE studentID=?");
            pst.setString(1, student.getStudent_name());
            pst.setString(2, student.getGender());
            pst.setString(3, student.getStudent_phone());
            pst.setString(4, student.getStudent_address());
            pst.setString(5, student.getTrn());
            pst.setString(6, student.getDateRegistered());
            pst.setBoolean(7, student.isAnyDiscount());
            pst.setBoolean(8, student.isAnyGovtFee());
            pst.setBoolean(9, student.isGraduated());
            pst.setString(10, id);

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public static boolean editStudentGuardian(Guardian guardian, String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        try {
            pst = conn.prepareStatement("UPDATE studentnextofkins "
                    + "SET name=?, phone=?, physicalAddress=? "
                    + " WHERE studentID=?");
            pst.setString(1, guardian.getGuardian_name());
            pst.setString(2, guardian.getGuardian_phone());
            pst.setString(3, guardian.getGuardian_physicalAddress());
            pst.setString(4, id);

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public int countStudents() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int count = 0;
        try {
            pst = conn.prepareStatement("Select count(*) FROM students");

            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return count;
    }

    public static int countStudentsInThisCourse(String code) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        int count = 0;
        try {
            pst = conn.prepareStatement("Select count(*) FROM students "
                    + "where course=?");

            pst.setString(1, code);
            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return count;
    }

    public Student getStudentByPhone(String phone) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM students where phone=?");
            pst.setString(1, phone);
            rs = pst.executeQuery();

            while (rs.next()) {
                return new Student(
                        rs.getString("studentID"),
                        rs.getString("fullname"),
                        rs.getString("postalAddress"),
                        rs.getString("phone"),
                        rs.getString("dateRegistered"),
                        rs.getString("gender"),
                        rs.getString("trn"),
                        rs.getBoolean("anyDiscount"),
                        rs.getBoolean("anyGovernmentFee"),
                        rs.getBoolean("graduated"),
                        "",
                        "",
                        "",
                        "",
                        rs.getString("course"),
                        rs.getString("duration")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Guardian getStudentGuardian(String student_id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM studentnextofkins where studentID=?");
            pst.setString(1, student_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                return new Guardian(
                        rs.getString("id"),
                        rs.getString("studentID"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("physicalAddress")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(StudentQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
