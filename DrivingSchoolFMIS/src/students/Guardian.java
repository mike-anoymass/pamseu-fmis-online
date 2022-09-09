/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package students;

/**
 *
 * @author ANOYMASS
 */
public class Guardian {

    private String id;
    private String student;
    private String guardian_name;
    private String guardian_phone;
    private String guardian_physicalAddress;

    public Guardian() {

    }

    public Guardian(String student, String name, String phone, String physicalAddress) {
        this.student = student;
        this.guardian_phone = phone;
        this.guardian_physicalAddress = physicalAddress;
        this.guardian_name = name;
    }

    public Guardian(String id, String student, String name, String phone, String physicalAddress) {
        this.student = student;
        this.guardian_phone = phone;
        this.guardian_physicalAddress = physicalAddress;
        this.guardian_name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getGuardian_name() {
        return guardian_name;
    }

    public void setGuardian_name(String guardian_name) {
        this.guardian_name = guardian_name;
    }

    public String getGuardian_phone() {
        return guardian_phone;
    }

    public void setGuardian_phone(String guardian_phone) {
        this.guardian_phone = guardian_phone;
    }

    public String getGuardian_physicalAddress() {
        return guardian_physicalAddress;
    }

    public void setGuardian_physicalAddress(String guardian_physicalAddress) {
        this.guardian_physicalAddress = guardian_physicalAddress;
    }

}
