package balances;

import students.Student;

public class Balance {
    private String id;
    private String fullName;
    private String course;
    private String duration;
    private String fees;
    private String paid;
    private String balance;
    private Student student;

    public Balance(String id, String fullName, String course, String duration,
                   String fees, String paid, String balance, Student student) {
        this.id = id;
        this.fullName = fullName;
        this.course = course;
        this.duration = duration;
        this.fees = fees;
        this.paid = paid;
        this.balance = balance;
        this.student = student;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullName) {
        this.fullName = fullName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
