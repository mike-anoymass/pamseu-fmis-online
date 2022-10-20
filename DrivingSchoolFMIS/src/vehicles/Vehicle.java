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
    private String cofDate;
    private String insuranceDate;
    private String date;

    public Vehicle(int id, String regNo, String cofDate, String insuranceDate, String date) {
        this.id = id;
        this.regNo = regNo;
        this.cofDate = cofDate;
        this.insuranceDate = insuranceDate;
        this.date = date;
    }

    public Vehicle(String regNo, String cofDate, String insuranceDate) {
        this.regNo = regNo;
        this.cofDate = cofDate;
        this.insuranceDate = insuranceDate;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCofDate() {
        return cofDate;
    }

    public void setCofDate(String cofDate) {
        this.cofDate = cofDate;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

}
