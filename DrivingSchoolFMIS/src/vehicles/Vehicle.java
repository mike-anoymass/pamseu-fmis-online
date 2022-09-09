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
public class Vehicle {

    private int id;
    private String regNo;
    private String name;
    private String date;

    public Vehicle(int id, String regNo, String name, String date) {
        this.id = id;
        this.regNo = regNo;
        this.name = name;
        this.date = date;
    }

    public Vehicle(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
