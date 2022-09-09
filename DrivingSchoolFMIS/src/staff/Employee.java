package staff;

import javafx.scene.control.CheckBox;

public class Employee {

    private String id;
    private String fullName;
    private String phone;
    private String postalAddress;
    private String physicalAddress;
    private String department;
    private String guardianPhone;
    private String employeeStatus;
    private String workingHours;
    private String date;
    private String dateRegistered;
    private CheckBox checkBox = new CheckBox();
    private String gender;
    private String dob;
    private boolean isActive;

    public Employee(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
    
    
    public Employee(String id, String fullName, String phone, String postalAddress,
            String physicalAddress, String department, String guardianPhone,
            String employeeStatus,
            String workingHours, String date, String dateRegistered,
            String gender, String dob, boolean isActive) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.department = department;
        this.isActive = isActive;
        this.guardianPhone = guardianPhone;
        this.employeeStatus = employeeStatus;
        this.workingHours = workingHours;
        this.date = date;
        this.dateRegistered = dateRegistered;
        this.gender = gender;
        this.dob = dob;
    }

    public Employee(String fullName, String phone,
            String postalAddress, String physicalAddress,
            String department, String employeeStatus,
            String workingHours, String date,
            String dateRegistered, String gender, String dob, boolean isActive) {
        this.fullName = fullName;
        this.phone = phone;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.department = department;
        this.employeeStatus = employeeStatus;
        this.isActive = isActive;
        this.workingHours = workingHours;
        this.date = date;
        this.dateRegistered = dateRegistered;
        this.gender = gender;
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
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

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
