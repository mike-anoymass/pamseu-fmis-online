package tests;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import commision.Commission;
import commision.CommissionQueries;
import fees.FeesCalculationsClass;
import fees.FeesResults;
import settings.InitSetupForCombosAndToggles;
import validation.ValidateFieldsClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

import static settings.AlertClass.makeAlert;
import static settings.AlertClass.makePromptAlert;
import static settings.NotificationClass.showNotification;
import staff.Employee;
import staff.EmployeeQueries;
import students.Student;

public class StudentTestController {

    private Student student;
    private ObservableList<Test> testData = FXCollections.observableArrayList();
    private final ObservableList<String> instructor = FXCollections.observableArrayList();
    private ResultSet rs;
    private final TestQueries query = new TestQueries();
    private final EmployeeQueries instructorQuery = new EmployeeQueries();

    @FXML
    private AnchorPane anchor;

    @FXML
    private ComboBox<String> instructorCombo;

    @FXML
    private Label titleLbl;

    @FXML
    private Label courseLbl;

    @FXML
    private Label categoryLbl;

    @FXML
    private JFXTextField fullNameTxt;

    @FXML
    private ComboBox<String> testComboBox;

    @FXML
    private JFXRadioButton passRadioBtn;

    @FXML
    private JFXRadioButton failRadioBtn;

    @FXML
    private DatePicker dateTxt;

    @FXML
    private TableView<Test> testTable;

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        String result, dateOfTest;
        String testTaken = testComboBox.getSelectionModel().getSelectedItem();

        if (passRadioBtn.isSelected()) {
            result = "pass";
        } else if (failRadioBtn.isSelected()) {
            result = "fail";
        } else {
            result = "";
        }

        try {
            dateOfTest = dateTxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (NullPointerException ex) {
            dateOfTest = "";
        }

        Test test = new Test(
                student.getStudent_id(),
                testTaken,
                "date",
                dateOfTest,
                result
        );

        if (new ValidateFieldsClass().validateTest(test)) {
            if (testTaken.equals("Road test") && instructorCombo.isVisible()) {

                String driver = instructorCombo.getSelectionModel().getSelectedItem();
                String staffID = "";

                if (driver != null) {
                    int added = query.addTest(test);

                    if (added > 0) {
                        showNotification(test.getTestName() + " for " + student.getStudent_name()
                                + " Has been added Succesfully");

                        Employee staff = instructorQuery.getStaff(driver);

                        staffID = staff.getId();

                        //calculate commision here 
                        double commisionValue = calculateCommision();

                        Commission commision = new Commission(added, staffID, commisionValue, false);

                        CommissionQueries commissionQueries = new CommissionQueries();
                        commissionQueries.addCommission(commision);

                        clearTextFields();
                        loadTestData();
                    }
                } else {
                    makeAlert("warning", "Please select The Driver for this successful road test");
                }

            } else {
                int added = query.addTest(test);

                if (added > 0) {
                    showNotification(test.getTestName() + " for " + student.getStudent_name()
                            + " Has been added Succesfully");

                    clearTextFields();
                    loadTestData();
                }
            }

        }
    }

    @FXML
    void deleteTest(ActionEvent event) {
        boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure you want to delete this test record ? \n"
                + "This Procedure is irreversible- we hope that you know what you are doing");
        try {
            Test test = testTable.getSelectionModel().getSelectedItem();

            if (query.deleteTest(test.getId(), action) != 404) {
                showNotification("Test Deleted Successfully");
            }

            loadTestData();

        } catch (NullPointerException ex) {
            makeAlert("warning", "Nothing to delete\nSelect test from the table");
        }

    }

    @FXML
    void testOnAction(ActionEvent event) {
        String test = testComboBox.getSelectionModel().getSelectedItem();

        try {
            if (test.equals("Road test") && passRadioBtn.isSelected()) {
                instructorCombo.setVisible(true);
            } else {
                instructorCombo.setVisible(false);
            }
        } catch (NullPointerException n) {
            test = "";
        }

    }

    public void initialize(Student student) {
        this.student = student;
        anchor.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());

        setLabels();
        loadTestInComboBox();
        setRemarksToggleGroup();

        loadTestData();
        setTestTable();

        loadInstructors();
    }

    private void loadTestData() {
        testData.clear();

        testData.addAll(query.getTestsFor(student.getStudent_id()));

    }

    private void setTestTable() {
        testTable.setItems(testData);

        TableColumn col1 = new TableColumn("Test Taken");
        TableColumn col2 = new TableColumn("Result");
        TableColumn col3 = new TableColumn("Test Taken on");
        TableColumn col4 = new TableColumn("Added By");

        col1.setMinWidth(100);
        col2.setMinWidth(50);
        col3.setMinWidth(90);
        col4.setMinWidth(100);

        testTable.getColumns().addAll(col1, col2, col3, col4);
        testTable.setEditable(true);

        col1.setCellValueFactory(new PropertyValueFactory<>("testName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("passOrFail"));
        col3.setCellValueFactory(new PropertyValueFactory<>("dateOfTest"));
        col4.setCellValueFactory(new PropertyValueFactory<>("user"));

    }

    private void loadTestInComboBox() {
        ObservableList<String> test = FXCollections.observableArrayList();
        test.add("Aptitude test 1");
        test.add("Aptitude test 2");
        test.add("Road test");

        testComboBox.setItems(test);
    }

    private void setLabels() {
        titleLbl.setText("Tests for: " + student.getStudent_name());
        courseLbl.setText("Course: " + student.getCourse());
        categoryLbl.setText("Category: " + student.getCourseType());
        fullNameTxt.setText(student.getStudent_name());
    }

    public void setRemarksToggleGroup() {
        new InitSetupForCombosAndToggles().setGenderToggleGroup(passRadioBtn, failRadioBtn);
    }

    private void clearTextFields() {
        testComboBox.getSelectionModel().select(null);
        instructorCombo.getSelectionModel().select(null);
        passRadioBtn.setSelected(false);
        passRadioBtn.setSelected(false);
        dateTxt.setValue(null);
    }

    private void loadInstructors() {
        instructor.clear();

        ObservableList<Employee> staff = instructorQuery.getInstructors();

        staff.stream().forEach((s) -> {
            instructor.add(
                    s.getFullName()
            );
        });

        instructorCombo.setItems(instructor);

    }

    private double calculateCommision() {
        FeesResults calculate = new FeesCalculationsClass().FeesCalculations(student);

        String fees = calculate.getTotalFee();

        double rate = new CommissionQueries().getRate();

        return Double.parseDouble(fees) * rate / 100;
    }
}
