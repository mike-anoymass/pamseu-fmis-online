package data;

import students.StudentQueries;
import vehicles.VehicleQueries;

public class CheckExistence {

    public boolean checkStudentExist(String id) {
        return new StudentQueries().getStudent(id) == null;
    }

    public boolean checkVehicleExist(String regNo) {
        return new VehicleQueries().getVehicle(regNo) == null;
    }
}
