/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.collections.ObservableList;
import static mail.Mail.sendEmail;
import vehicles.Vehicle;
import vehicles.VehicleQueries;

/**
 *
 * @author ANOYMASS
 */
class CheckInsuranceAndCofExpiry {

    public CheckInsuranceAndCofExpiry() {

    }

    public void check() {
        ObservableList<Vehicle> vhicles = new VehicleQueries().getVehicles();
        String[] dateObj;
        int ciYear, ciMonth, ciDay;

        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        for (Vehicle v : vhicles) {
            dateObj = v.getCofDate().split("-");
            ciYear = Integer.parseInt(dateObj[0]);
            ciMonth = Integer.parseInt(dateObj[1]);
            ciDay = Integer.parseInt(dateObj[2]);

            if (ciYear == year && month == ciMonth) {
                int remainingDays = ciDay - day;

                if (remainingDays > 0 && remainingDays < 6) {
                    sendEmail("COF Reminder", "I would like to remind you that vehicle "
                            + v.getRegNo() + " certificate of fitness will expire on " + v.getCofDate()
                            + ". This means you have " + remainingDays + " day(s) until COF expires \n"
                            + "Kindly issue another COF");
                }
            }

            dateObj = v.getInsuranceDate().split("-");
            ciYear = Integer.parseInt(dateObj[0]);
            ciMonth = Integer.parseInt(dateObj[1]);
            ciDay = Integer.parseInt(dateObj[2]);

            if (ciYear == year && month == ciMonth) {
                int remainingDays = ciDay - day;

                if (remainingDays > 0 && remainingDays < 6 ){
                    sendEmail("Insurance Reminder", "I would like to remind you that vehicle "
                            + v.getRegNo() + " insurance will expire on " + v.getInsuranceDate()
                            + ". This means you have " + remainingDays + " day(s) until insurance expires \n"
                            + "Kindly get a fresh insurance");
                }
            }

        }

    }

}
