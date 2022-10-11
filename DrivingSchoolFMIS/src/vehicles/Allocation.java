/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles;

/**
 *
 * @author ANOYMASS
 */
public class Allocation {
    private int studentID;
    private int pendingStudentID;
    private int staff;
    private String vehicle;
    private String time;
    private String dateAdded;
    private int bal;
    private int less;
    private String date;
    private String studentName;
    private String studentContact;
    private String pendingStudentName;

    public Allocation(String date, int studentID, int pendingStudentID, int staff, String vehicle, String time, int bal, int less) {
        this.studentID = studentID;
        this.pendingStudentID = pendingStudentID;
        this.staff = staff;
        this.vehicle = vehicle;
        this.time = time;
        this.bal = bal;
        this.less = less;
        this.date = date;
    }

    Allocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Allocation(int studentID, int pendingStudentID, int staff, String vehicle, String time, String dateAdded, int bal, int less, String studentName, String studentContact, String pendingStudentName) {
        this.studentID = studentID;
        this.pendingStudentID = pendingStudentID;
        this.staff = staff;
        this.vehicle = vehicle;
        this.time = time;
        this.dateAdded = dateAdded;
        this.bal = bal;
        this.less = less;
        this.studentName = studentName;
        this.studentContact = studentContact;
        this.pendingStudentName = pendingStudentName;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getPendingStudentID() {
        return pendingStudentID;
    }

    public void setPendingStudentID(int pendingStudentID) {
        this.pendingStudentID = pendingStudentID;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public int getLess() {
        return less;
    }

    public void setLess(int less) {
        this.less = less;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(String studentContact) {
        this.studentContact = studentContact;
    }

    public String getPendingStudentName() {
        return pendingStudentName;
    }

    public void setPendingStudentName(String pendingStudentName) {
        this.pendingStudentName = pendingStudentName;
    }
 
}
