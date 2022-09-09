/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trail;

/**
 *
 * @author ANOYMASS
 */
public class Trail {

    private String fullname;
    private String type;
    private String phone;
    private String date;

    public Trail(String fullname, String type, String phone, String date) {
        this.fullname = fullname;
        this.type = type;
        this.phone = phone;
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
