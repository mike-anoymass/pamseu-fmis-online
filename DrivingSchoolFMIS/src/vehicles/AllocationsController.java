package vehicles;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;
import static settings.AlertClass.makeAlert;
import static settings.NotificationClass.showNotification;
import staff.Employee;
import staff.EmployeeQueries;
import students.Student;
import students.StudentQueries;

public class AllocationsController {

    @FXML
    private TableView<Allocation> tableView;
    @FXML
    private JFXTextField Addcourse;
    @FXML
    private JFXTextField Addvenue;
    @FXML
    private JFXTextField VenueCapacity;
    @FXML
    private JFXComboBox<?> LeveInCourse;
    @FXML
    private JFXTextField lecturer;
    @FXML
    private JFXComboBox<?> lecturercourse;
    @FXML
    private ComboBox<String> driverCombo;
    @FXML
    private ComboBox<String> pendingCombo;
    @FXML
    private ComboBox<String> vehicleCombo;
    @FXML
    private ComboBox<String> studentCombo;
    @FXML
    private ComboBox<String> timeCombo;
    @FXML
    private DatePicker dateCombo;
    @FXML
    private Text titleTxt;
    @FXML
    private JFXTextField lessTxt;
    @FXML
    private JFXTextField balTxt;

    static ObservableList<Allocation> allocations = FXCollections.observableArrayList();

    public void initialize() {
        allocations.clear();
        prepareTable();
        loadDate();
        loadTimeRanges();
        loadDrivers();
        loadVehicles();
        loadStudents();
    }

    @FXML
    void Addcourse(ActionEvent event) {

    }

    @FXML
    void initiate(ActionEvent event) {
        try {
            String[] staffObj, vehicleObj;
            String student, staff, vehicle;
            LocalDate localDate;

            staff = driverCombo.getSelectionModel().getSelectedItem();
            vehicle = vehicleCombo.getSelectionModel().getSelectedItem();
            localDate = dateCombo.getValue();

            staffObj = staff.split(",");
            vehicleObj = vehicle.split(",");

            staff = staffObj[1];
            vehicle = vehicleObj[1];

            loadAllocations(Integer.parseInt(staff), vehicle, localDate.toString());

        } catch (NullPointerException ex) {
            makeAlert("error", "You need to select atleast date, instructor and vehicle");
        } catch (NumberFormatException ex) {
            makeAlert("error", "Enter a number in less and bal fields\n"
                    + "Or You have entered a letter in a field that requires a number");
        } catch (ArrayIndexOutOfBoundsException ex) {
            makeAlert("error", "Improper format!\n"
                    + "Please select from the drop downs\n"
                    + "Do not change the values from the drop downs");
        }
    }

    @FXML
    void print(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

        Allocation allocation = initializeAllocation();

        if (allocation != null) {
            boolean added = new AllocationQueries().addAllocation(allocation);

            if (!added) {
                showNotification("Allocation Has been added Succesfully");
                loadAllocations(allocation.getStaff(), allocation.getVehicle(), allocation.getDate());
                clearFields();
                
            } else {
                makeAlert("error", "We have Encountered an Error!\n"
                        + "Seems like this allocation exists\n"
                        + "You can update this record");
            }
        }

    }

    @FXML
    void update(ActionEvent event) {
        Allocation allocation = initializeAllocation();

        if (allocation != null) {
            boolean edited = new AllocationQueries().update(allocation);

            if (!edited) {
                showNotification("Allocation has been edited Succesfully");
                loadAllocations(allocation.getStaff(), allocation.getVehicle(), allocation.getDate());
                clearFields();

            }
        }
    }

    private void prepareTable() {
        tableView.setItems(allocations);

        TableColumn col0 = new TableColumn("TIME");
        TableColumn col1 = new TableColumn("STUDENT");
        TableColumn col2 = new TableColumn("LESS");
        TableColumn col3 = new TableColumn("BAL");
        TableColumn col4 = new TableColumn("CONTACT");
        TableColumn col5 = new TableColumn("PENDING");

        col0.setMinWidth(120);
        col1.setMinWidth(180);
        col2.setMinWidth(100);
        col3.setMinWidth(100);
        col4.setMinWidth(170);
        col5.setMinWidth(170);

        tableView.getColumns().addAll(col0, col1, col2, col3, col4, col5);

        col0.setCellValueFactory(new PropertyValueFactory<>("time"));
        col1.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("less"));
        col3.setCellValueFactory(new PropertyValueFactory<>("bal"));
        col4.setCellValueFactory(new PropertyValueFactory<>("studentContact"));
        col5.setCellValueFactory(new PropertyValueFactory<>("pendingStudentName"));
    }

    private void loadDate() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String date = (day + "/" + (month + 1) + "/" + year);

        titleTxt.setText("Student Allocation **" + date + "**");
    }

    private void loadDrivers() {
        ObservableList<Employee> drivers = new EmployeeQueries().getInstructors();
        ObservableList<String> staffTxt = FXCollections.observableArrayList();

        drivers.stream().forEach((driver) -> {
            staffTxt.addAll(driver.getFullName() + "," + driver.getId());
        });

        driverCombo.setItems(staffTxt);
        TextFields.bindAutoCompletion(driverCombo.getEditor(), driverCombo.getItems());
    }

    private void loadVehicles() {
        ObservableList<Vehicle> vehicles = new VehicleQueries().getVehicles();
        ObservableList<String> txt = FXCollections.observableArrayList();

        vehicles.stream().forEach((vehicle) -> {
            txt.addAll(vehicle.getName() + "," + vehicle.getRegNo());
        });

        vehicleCombo.setItems(txt);
        TextFields.bindAutoCompletion(vehicleCombo.getEditor(), vehicleCombo.getItems());
    }

    private void loadStudents() {
        ObservableList<Student> students = new StudentQueries().getStudentsNotGraduated();
        ObservableList<String> txt = FXCollections.observableArrayList();

        students.stream().forEach((student) -> {
            txt.addAll(student.getStudent_name() + "," + student.getStudent_id());
        });

        pendingCombo.setItems(txt);
        studentCombo.setItems(txt);
        TextFields.bindAutoCompletion(studentCombo.getEditor(), studentCombo.getItems());
        TextFields.bindAutoCompletion(pendingCombo.getEditor(), pendingCombo.getItems());
    }

    private void loadTimeRanges() {
        ObservableList<String> txt = FXCollections.observableArrayList();

        txt.addAll("06:30-7:00", "07:00-07:30", "07:30-08:00", "08:00-08:30",
                "08:30-09:00", "09:00-09:30", "09:30-10:00", "10:30-11:00",
                "11:30-12:00", "12:00-12:30", "12:30-13:00", "13:00-13:30",
                "13:30-14:00", "11:30-12:00", "14:30-15:00", "15:00-15:30",
                "15:30-16:00", "16:00-16:30", "16:30-17:00", "17:00-17:30",
                "17:30-18:00");

        timeCombo.setItems(txt);

        TextFields.bindAutoCompletion(timeCombo.getEditor(), timeCombo.getItems());
    }

    private void loadAllocations(int staff, String vehicle, String date) {
        allocations.clear();

        allocations.addAll(new AllocationQueries().getAllocationFor(staff, vehicle, date));
    }

    private Allocation initializeAllocation() {
        try {
            String[] studentObj, staffObj, vehicleObj, pendingObj, timeObj;
            String student, staff, vehicle, time, pending;
            LocalDate localDate;

            student = studentCombo.getSelectionModel().getSelectedItem();
            pending = pendingCombo.getSelectionModel().getSelectedItem();
            staff = driverCombo.getSelectionModel().getSelectedItem();
            vehicle = vehicleCombo.getSelectionModel().getSelectedItem();
            time = timeCombo.getSelectionModel().getSelectedItem();
            localDate = dateCombo.getValue();

            studentObj = student.split(",");
            staffObj = staff.split(",");
            vehicleObj = vehicle.split(",");
            pendingObj = pending.split(",");
            timeObj = time.split("-");

            student = studentObj[1];
            staff = staffObj[1];
            vehicle = vehicleObj[1];
            pending = pendingObj[1];
            String extra = timeObj[1];

            Allocation allocation = new Allocation(localDate.toString(), Integer.parseInt(student), Integer.parseInt(pending),
                    Integer.parseInt(staff), vehicle, time,
                    Integer.parseInt(balTxt.getText()), Integer.parseInt(lessTxt.getText()));

            return allocation;

        } catch (NullPointerException ex) {
            makeAlert("error", "Fill all the fields");
        } catch (NumberFormatException ex) {
            makeAlert("error", "Enter a number in less and bal fields\n"
                    + "Or You have entered a letter in a field that requires a number");
        } catch (ArrayIndexOutOfBoundsException ex) {
            makeAlert("error", "Improper format!\n"
                    + "Please select from the drop downs\n"
                    + "Do not change the values from the drop downs");
        }

        return null;
    }

    private void clearFields() {
        lessTxt.setText("");
        balTxt.setText("");
        pendingCombo.getSelectionModel().select(null);
        studentCombo.getSelectionModel().select(null);
        timeCombo.getSelectionModel().select(null);
    }

}
