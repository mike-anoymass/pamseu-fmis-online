/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commision;

import javafx.scene.control.CheckBox;

/**
 *
 * @author ANOYMASS
 */
public class Commission {

    private int testID;
    private String staffID;
    private double commision;
    private boolean paid;
    private String status;
    private String student;
    private String staff;
    private String dateObtained;
    private String datePaid;
    private String user;
    private CheckBox checkBox = new CheckBox();

    public Commission(int id, String staffID, double commision, boolean paid) {
        this.testID = id;
        this.staffID = staffID;
        this.commision = commision;
        this.paid = paid;
    }

    public Commission(int testID, String staffID, double commision, boolean paid,
            String student, String staff, String dateObtained, String datePaid, String user, String status) {
        this.testID = testID;
        this.staffID = staffID;
        this.commision = commision;
        this.paid = paid;
        this.student = student;
        this.staff = staff;
        this.dateObtained = dateObtained;
        this.datePaid = datePaid;
        this.user = user;
        this.status = status;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public double getCommision() {
        return commision;
    }

    public void setCommision(double commision) {
        this.commision = commision;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getDateObtained() {
        return dateObtained;
    }

    public void setDateObtained(String dateObtained) {
        this.dateObtained = dateObtained;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

}
