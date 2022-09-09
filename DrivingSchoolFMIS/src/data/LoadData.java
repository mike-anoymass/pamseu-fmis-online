package data;

import payments.PaymentQueries;
import payments.Payment;
import courses.CourseQueries;
import courses.Course;
import receipts.ReceiptQueries;
import receipts.Receipts;
import staff.EmployeeQueries;
import staff.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class LoadData {

    ObservableList<Receipts> receiptData = FXCollections.observableArrayList();
    ObservableList<Payment> payment = FXCollections.observableArrayList();
    static ObservableList<Employee> employees = FXCollections.observableArrayList();
    ResultSet rs;

    public static EmployeeQueries staffQuery = new EmployeeQueries();

    public static ObservableList<Employee> loadEmployees(ObservableList<Employee> emp) {

        return emp;

    }

    public ObservableList<Receipts> loadReceipts() {

        return new ReceiptQueries().getFeesReceipts();
    }

    public ObservableList<Payment> loadPayments() {
        return new PaymentQueries().getAllPayments();
    }

    public ObservableList<Course> loadCourseTableData() {
        ObservableList<Course> courseData = new CourseQueries().getAllCourses();

        return courseData;
    }
}
