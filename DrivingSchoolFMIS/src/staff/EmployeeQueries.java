package staff;

import connection.SqlConnection;
import java.sql.Connection;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;

import static settings.AlertClass.makeAlert;

public class EmployeeQueries {

    public ObservableList<Employee> getEmployees() {
        PreparedStatement pst = null;
        ResultSet rsE = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    "SELECT employees.id AS staffID, fullname, employees.phone as staffPhone, "
                    + "employees.postalAddress AS staffPostalAddress, employees.physicalAddress AS staffPAddr"
                    + ", gender, department, employeeStatus, isActive, workingHours, dateOfEntry,"
                    + " date, dob , employeenextofkins.phone AS gPhone "
                    + "FROM employees "
                    + "INNER JOIN employeenextofkins ON employees.id=employeenextofkins.employee"
                    + " ORDER BY employees.date DESC "
            );
            rsE = pst.executeQuery();

            while (rsE.next()) {
                employees.add(new Employee(
                        rsE.getString("staffID"),
                        rsE.getString("fullname"),
                        rsE.getString("staffPhone"),
                        rsE.getString("staffPostalAddress"),
                        rsE.getString("staffPAddr"),
                        rsE.getString("department"),
                        rsE.getString("gPhone"),
                        rsE.getString("employeeStatus"),
                        rsE.getString("workingHours"),
                        rsE.getString("date"),
                        rsE.getString("dateOfEntry"),
                        rsE.getString("gender"),
                        rsE.getString("dob"),
                        rsE.getBoolean("isActive")
                ));
            }

            return employees;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {

                if (rsE != null) {
                    rsE.close();
                }

                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return employees;
    }

    public boolean addStaff(Employee emp) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO employees (fullname, phone ,postalAddress,"
                    + "physicalAddress, department, employeeStatus, "
                    + "workingHours, dateOfEntry, date, gender, dob, isActive) "
                    + "VALUES (?, ?, ?, ?, ? ,? , ? , ?, NOW(), ?, ? ,?)");
            pst.setString(1, emp.getFullName());
            pst.setString(2, emp.getPhone());
            pst.setString(3, emp.getPostalAddress());
            pst.setString(4, emp.getPhysicalAddress());
            pst.setString(5, emp.getDepartment());
            pst.setString(6, emp.getEmployeeStatus());
            pst.setString(7, emp.getWorkingHours());
            pst.setString(8, emp.getDateRegistered());
            pst.setString(9, emp.getGender());
            pst.setString(10, emp.getDob());
            pst.setBoolean(11, emp.isIsActive());

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public Employee getStaff(String fullName) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("SELECT * FROM employees where fullname=?");
            pst.setString(1, fullName);
            rs = pst.executeQuery();
            while (rs.next()) {
                return new Employee(
                        rs.getString("id"),
                        rs.getString("fullname")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public ObservableList<Employee> getInstructors() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Employee> staff = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT * FROM employees where department=?");
            pst.setString(1, "Driver");
            rs = pst.executeQuery();

            while (rs.next()) {
                staff.add(new Employee(
                        rs.getString("id"),
                        rs.getString("fullname")
                ));

            }
            return staff;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return staff;
    }

    public void addNextOfKin(NextOfKin nextOfKin) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO employeenextofkins (employee, name, phone, physicalAddress) "
                    + "VALUES (?, ?, ?, ?)");
            pst.setString(1, nextOfKin.getEmployee());
            pst.setString(2, nextOfKin.getName());
            pst.setString(3, nextOfKin.getPhone());
            pst.setString(4, nextOfKin.getPhysicalAddress());

            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int deleteEmployees(ObservableList<Employee> selectedStaff, boolean action) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        if (action) {

            for (int i = 0; i < selectedStaff.size(); i++) {

                try {
                    pst = conn.prepareStatement("DELETE FROM employees WHERE id=?");
                    pst.setString(1, selectedStaff.get(i).getId());

                    pst.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                        Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            return 101;
        }
        return 404;
    }

    public ObservableList<Employee> getDepartment(String department) {
        PreparedStatement pst = null;
        ResultSet rsE = null;
        Connection conn = new SqlConnection().DbConnector();
        ObservableList<Employee> staff = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement(
                    "SELECT employees.id AS staffID, fullname, employees.phone as staffPhone, "
                    + "employees.postalAddress AS staffPostalAddress, employees.physicalAddress AS staffPAddr"
                    + ", gender, department, employeeStatus, workingHours, dateOfEntry,"
                    + " date, dob , employeenextofkins.phone AS gPhone , isActive "
                    + "FROM employees "
                    + "INNER JOIN employeenextofkins ON employees.id=employeenextofkins.employee "
                    + "WHERE department = ?"
                    + " ORDER BY employees.date DESC "
            );

            pst.setString(1, department);
            rsE = pst.executeQuery();

            while (rsE.next()) {
                staff.add(new Employee(
                        rsE.getString("staffID"),
                        rsE.getString("fullname"),
                        rsE.getString("staffPhone"),
                        rsE.getString("staffPostalAddress"),
                        rsE.getString("staffPAddr"),
                        rsE.getString("department"),
                        rsE.getString("gPhone"),
                        rsE.getString("employeeStatus"),
                        rsE.getString("workingHours"),
                        rsE.getString("date"),
                        rsE.getString("dateOfEntry"),
                        rsE.getString("gender"),
                        rsE.getString("dob"),
                        rsE.getBoolean("isActive")
                ));
            }

            return staff;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {

                if (rsE != null) {
                    rsE.close();
                }

                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return staff;
    }

    public NextOfKin getNextOfKin(String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement(
                    "SELECT *  FROM employeenextofkins "
                    + "WHERE employee = ?"
            );

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                return new NextOfKin(
                        rs.getString("employee"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("physicalAddress")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean editStaff(Employee emp, String id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("UPDATE employees "
                    + "SET fullname = ?, phone=? ,postalAddress=?,"
                    + "physicalAddress=?, department=?, employeeStatus=?, "
                    + "workingHours=?, dateOfEntry=?, gender=?, dob=?, isActive=? "
                    + " WHERE id=?"
            );

            pst.setString(1, emp.getFullName());
            pst.setString(2, emp.getPhone());
            pst.setString(3, emp.getPostalAddress());
            pst.setString(4, emp.getPhysicalAddress());
            pst.setString(5, emp.getDepartment());
            pst.setString(6, emp.getEmployeeStatus());
            pst.setString(7, emp.getWorkingHours());
            pst.setString(8, emp.getDateRegistered());
            pst.setString(9, emp.getGender());
            pst.setString(10, emp.getDob());
            pst.setBoolean(11, emp.isIsActive());
            pst.setString(12, id);

            return pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            makeAlert("error", "Failed to edit Staff -> " + ex);

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
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public void editNextOfKin(NextOfKin nextOfKin) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = new SqlConnection().DbConnector();

        try {
            pst = conn.prepareStatement("UPDATE employeenextofkins "
                    + "SET name = ?, phone=? ,physicalAddress=? "
                    + " WHERE employee=?"
            );

            pst.setString(1, nextOfKin.getName());
            pst.setString(2, nextOfKin.getPhone());
            pst.setString(3, nextOfKin.getPhysicalAddress());
            pst.setString(4, nextOfKin.getEmployee());

            pst.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

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
                Logger.getLogger(EmployeeQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
