/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package students;

import javafx.scene.control.CheckBox;

/**
 *
 * @author ANOYMASS
 */
public class Student extends Guardian {

    private String student_id;
    private String student_name;
    private String student_address;
    private String student_phone;
    private String gender;
    private String course;
    private String courseType;
    private String date;
    private String dateRegistered;
    private String trn;
    private boolean anyDiscount;
    private boolean anyGovtFee;
    private boolean graduated;
    private String guardianPhoneNumber;
    private String addedBy;
    private CheckBox checkBox = new CheckBox();

    public Student(String student_name, String student_address,
            String student_phone, String gender, String course, String courseType,
            String date, String dateRegistered, String trn,
            boolean anyDiscount, boolean anyGovtFee, boolean graduated,
            String student, String name, String phone, String physicalAddress) {
        super(student, name, phone, physicalAddress);
        this.student_name = student_name;
        this.student_address = student_address;
        this.student_phone = student_phone;
        this.gender = gender;
        this.course = course;
        this.courseType = courseType;
        this.date = date;
        this.dateRegistered = dateRegistered;
        this.trn = trn;
        this.anyDiscount = anyDiscount;
        this.anyGovtFee = anyGovtFee;
        this.graduated = graduated;
    }

    public Student(String student_id, String student_name, String student_address,
            String student_phone, String gender, String course, String courseType,
            String date, String dateRegistered, String trn,
            boolean anyDiscount, boolean anyGovtFee, boolean graduated, String guardianPhone,
            String addedBy) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_address = student_address;
        this.student_phone = student_phone;
        this.gender = gender;
        this.course = course;
        this.courseType = courseType;
        this.date = date;
        this.dateRegistered = dateRegistered;
        this.trn = trn;
        this.anyDiscount = anyDiscount;
        this.anyGovtFee = anyGovtFee;
        this.graduated = graduated;
        this.guardianPhoneNumber = guardianPhone;
        this.addedBy = addedBy;
    }

    public Student(String student_id, String student_name, String student_address,
            String student_phone, String dateRegistered, String gender, String trn, boolean anyDiscount,
            boolean anyGovtFee, boolean graduated,
            String student, String name, String phone, String physicalAddress,
            String course, String category) {
        super(student, name, phone, physicalAddress);
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_address = student_address;
        this.student_phone = student_phone;
        this.gender = gender;
        this.dateRegistered = dateRegistered;
        this.trn = trn;
        this.course = course;
        this.courseType = category;
        this.anyDiscount = anyDiscount;
        this.anyGovtFee = anyGovtFee;
        this.graduated = graduated;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_address() {
        return student_address;
    }

    public void setStudent_address(String student_address) {
        this.student_address = student_address;
    }

    public String getStudent_phone() {
        return student_phone;
    }

    public void setStudent_phone(String student_phone) {
        this.student_phone = student_phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getTrn() {
        return trn;
    }

    public void setTrn(String trn) {
        this.trn = trn;
    }

    public boolean isAnyDiscount() {
        return anyDiscount;
    }

    public void setAnyDiscount(boolean anyDiscount) {
        this.anyDiscount = anyDiscount;
    }

    public boolean isAnyGovtFee() {
        return anyGovtFee;
    }

    public void setAnyGovtFee(boolean anyGovtFee) {
        this.anyGovtFee = anyGovtFee;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

    public String getGuardianPhoneNumber() {
        return guardianPhoneNumber;
    }

    public void setGuardianPhoneNumber(String guardianPhoneNumber) {
        this.guardianPhoneNumber = guardianPhoneNumber;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

}
