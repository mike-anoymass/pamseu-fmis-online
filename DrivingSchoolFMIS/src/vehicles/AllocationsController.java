package vehicles;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static drivingschoolfmis.DrivingSchoolFMIS.schoolName;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javax.swing.filechooser.FileSystemView;
import login.LoginDocumentController;
import org.controlsfx.control.textfield.TextFields;
import receipts.Receipts;
import static receipts.ReceiptsController.report;
import static settings.AlertClass.makeAlert;
import static settings.NotificationClass.showNotification;
import staff.Employee;
import staff.EmployeeQueries;
import students.Student;
import students.StudentQueries;

public class AllocationsController {

    @FXML
    private BorderPane bPane;
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
        bPane.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());
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
            String staff, vehicle;
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
        ObservableList<Allocation> allocs = tableView.getItems();

        String staff, vehicle;
        LocalDate localDate;

        staff = driverCombo.getSelectionModel().getSelectedItem();
        vehicle = vehicleCombo.getSelectionModel().getSelectedItem();
        localDate = dateCombo.getValue();

        if (allocs.size() > 0) {

            try {
                Document doc = new Document();
                String fileName = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
                        + "\\" + schoolName
                        + " FMIS\\Allocation " + localDate.toString() + " " + System.currentTimeMillis() + ".pdf";

                PdfWriter.getInstance(doc, new FileOutputStream(fileName));
                doc.open();
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("img/driving_32.png");
                image.scaleToFit(120, 90);
                doc.add(image);
                doc.add(new Paragraph(schoolName + " Driving School - Student Allocation Report",
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.BOLD, BaseColor.DARK_GRAY))
                );
                doc.add(new Paragraph("Printed by "
                        + LoginDocumentController.firstname + " " + LoginDocumentController.lastname
                        + " on " + new Date().toString()
                ));
                doc.add(Chunk.NEWLINE);
                doc.add(new LineSeparator());
                doc.add(Chunk.NEWLINE);

                PdfPTable table0 = new PdfPTable(2);
                PdfPCell titleCell0 = new PdfPCell(new Paragraph("Allocation Summary"));
                titleCell0.setColspan(4);
                titleCell0.setBorder(Rectangle.NO_BORDER);
                titleCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell0.setBackgroundColor(BaseColor.LIGHT_GRAY);

                table0.addCell(titleCell0);
                table0.addCell("Date:");
                table0.addCell(localDate.toString());
                table0.addCell("Instructor:");
                table0.addCell(staff);
                table0.addCell("Vehicle:");
                table0.addCell(vehicle);

                PdfPTable table1 = new PdfPTable(6);
                PdfPCell titleCell1 = new PdfPCell(new Paragraph("Allocation Details"));
                titleCell1.setColspan(14);
                titleCell1.setBorder(Rectangle.NO_BORDER);
                titleCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);

                table1.addCell(titleCell1);
                table1.addCell("TIME");
                table1.addCell("STUDENT");
                table1.addCell("LESS");
                table1.addCell("BAL");
                table1.addCell("CONTACT");
                table1.addCell("PENDING");

                for (Allocation a : allocs) {
                    table1.addCell(a.getTime());
                    table1.addCell(a.getStudentName());
                    table1.addCell(String.valueOf(a.getLess()));
                    table1.addCell(String.valueOf(a.getBal()));
                    table1.addCell(a.getStudentContact());
                    table1.addCell(a.getPendingStudentName());
                }

                report(doc, fileName, table0, table1);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            makeAlert("warning", "Nothing to report");
        }
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

    @FXML
    void delete(ActionEvent event) {
        
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
            txt.addAll("Reg #" + "," + vehicle.getRegNo());
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

        txt.addAll("06:30-07:00", "07:00-07:30", "07:30-08:00", "08:00-08:30",
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
