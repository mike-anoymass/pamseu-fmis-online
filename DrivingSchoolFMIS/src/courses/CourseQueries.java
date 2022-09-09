/*
	 * To change this license header, choose License Headers in Project Properties.
	 * To change this template file, choose Tools | Templates
	 * and open the template in the editor.
 */
package courses;

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

/**
 *
 * @author ANOYMASS
 */
public class CourseQueries {

    public ObservableList<Course> getAllCourses() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT * FROM courses ORDER BY id DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("governmentFee")
                )
                );
            }
            return courses;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;
    }

    public Course getCourse(String courseCode) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        Course course = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM courses where code=?");
            pst.setString(1, courseCode);
            rs = pst.executeQuery();

            while (rs.next()) {
                course = new Course(
                        rs.getString("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("governmentFee")
                );

                return course;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return course;
    }

    public Course getCourseById(String courseId) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        Course course = null;

        try {
            pst = conn.prepareStatement("SELECT * FROM courses where id=?");
            pst.setString(1, courseId);
            rs = pst.executeQuery();

            while (rs.next()) {
                course = new Course(
                        rs.getString("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("governmentFee")
                );

                return course;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return course;
    }

    public boolean addCourse(Course course) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;

        try {
            pst = conn.prepareStatement("INSERT INTO courses (code, name, governmentFee)"
                    + " VALUES (?, ?, ?)");
            pst.setString(1, course.getCode());
            pst.setString(2, course.getName());
            pst.setString(3, course.getGovernmentFee());
            success = pst.execute();

            return success;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public int deleteCourse(String courseCode, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int success = 404;

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM courses WHERE code=?");
                pst.setString(1, courseCode);
                success = pst.executeUpdate();
                return success;
            } catch (SQLException ex) {
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        return success;
    }

    public boolean editCourse(Course course) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;

        try {
            pst = conn.prepareStatement("UPDATE courses SET name=?,"
                    + " code=?, governmentFee=?"
                    + " WHERE id=?");
            pst.setString(1, course.getName());
            pst.setString(2, course.getCode());
            pst.setString(3, course.getGovernmentFee());
            pst.setString(4, course.getId());
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public ObservableList<CourseType> getAllCourseTypes() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<CourseType> durations = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * FROM durations ORDER BY id DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                durations.add(new CourseType(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("days")
                ));
            }
            return durations;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;
    }

    public CourseType getCourseType(String name) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        CourseType duration = null;

        try {
            pst = conn.prepareStatement("SELECT * FROM durations where name=?");
            pst.setString(1, name);
            rs = pst.executeQuery();

            while (rs.next()) {
                duration = new CourseType(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("days")
                );

                return duration;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return duration;
    }

    public boolean addCourseType(CourseType cType) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;
        try {
            pst = conn.prepareStatement("INSERT INTO durations (name, days) VALUES (?, ?)");
            pst.setString(1, cType.getName());
            pst.setString(2, cType.getDays());
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public boolean editCourseType(CourseType cType) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;
        try {
            pst = conn.prepareStatement("UPDATE durations SET days=?, name=? WHERE id=?");
            pst.setString(1, cType.getDays());
            pst.setString(2, cType.getName());
            pst.setString(3, cType.getId());
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public int deleteCourseType(String text, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int success = 404;

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM durations WHERE id=?");
                pst.setString(1, text);
                success = pst.executeUpdate();
                return success;

            } catch (SQLException ex) {
                makeAlert("error", "You can not"
                        + " delete this category\nReason: "
                        + "This category has students and courses associated with it");
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return success;
    }

    public ObservableList<CourseDuration> getAllCourseDurations() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<CourseDuration> cd = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT courseID , duration, "
                    + "code, durations.name AS category, fees, date "
                    + "FROM courseduration"
                    + " INNER JOIN courses ON courseduration.courseID=courses.id "
                    + " INNER JOIN durations ON courseduration.duration=durations.id "
                    + "ORDER BY date DESC, code");
            rs = pst.executeQuery();

            while (rs.next()) {
                cd.add(new CourseDuration(
                        rs.getString("courseID"),
                        rs.getString("duration"),
                        rs.getString("code"),
                        rs.getString("category"),
                        rs.getString("fees"),
                        rs.getString("date"))
                );
            }

            return cd;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;
    }

    public CourseDuration getCourseInfo(String courseID, String durationName) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        CourseDuration cd = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM courseduration where courseID=? and duration=?");
            pst.setString(1, courseID);
            pst.setString(2, durationName);
            rs = pst.executeQuery();

            while (rs.next()) {
                cd = new CourseDuration(
                        rs.getString("courseID"),
                        rs.getString("duration"),
                        rs.getString("fees"),
                        rs.getString("date"));
            }

            return cd;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return cd;
    }

    public boolean addCourseInfo(CourseDuration info) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;
        try {
            pst = conn.prepareStatement("INSERT INTO courseduration (courseID, duration, fees, date) "
                    + "VALUES (?, ?, ?, NOW())");
            pst.setString(1, info.getCourseID());
            pst.setString(2, info.getDurationID());
            pst.setString(3, info.getAmount());
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }

    public int deleteCourseInfo(String code, String duration, boolean action) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int success = 404;

        if (action) {
            try {
                pst = conn.prepareStatement("DELETE FROM courseduration"
                        + " WHERE courseID=? and duration=?");
                pst.setString(1, code);

                pst.setString(2, duration);

                success = pst.executeUpdate();

                return success;

            } catch (SQLException ex) {
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return success;
    }

    public int countCourses() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int count = 0;
        try {
            pst = conn.prepareStatement("Select count(*) FROM courses");

            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;

        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return count;
    }

    public int countCourseTypes() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int count = 0;
        try {
            pst = conn.prepareStatement("Select count(*) FROM durations");

            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;

        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return count;
    }

    public int countCourseInfo() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        int count = 0;
        try {
            pst = conn.prepareStatement("Select count(*) FROM courseduration");

            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;

        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return count;
    }

    public ObservableList<CourseType> getDurationsForThisCourse(String courseID) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<CourseType> duration = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT duration ,name , days "
                    + "FROM courseduration INNER JOIN durations ON courseduration.duration=durations.id "
                    + "WHERE courseduration.courseID = ?");
            pst.setString(1, courseID);
            rs = pst.executeQuery();

            while (rs.next()) {
                duration.add(new CourseType(
                        rs.getString("duration"),
                        rs.getString("name"),
                        rs.getString("days"))
                );
            }

            return duration;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;

    }

    public boolean editCourseInfo(CourseDuration cd) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        boolean success = true;
        try {
            pst = conn.prepareStatement("UPDATE courseduration SET fees=? "
                    + "WHERE courseID=? and duration=?");
            pst.setString(1, cd.getAmount());
            pst.setString(2, cd.getCourseID());
            pst.setString(3, cd.getDurationID());
            success = pst.execute();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CourseQueries.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return success;
    }
}
