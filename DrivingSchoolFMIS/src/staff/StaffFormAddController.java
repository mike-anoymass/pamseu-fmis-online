package staff;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import payments.AddExpense;
import payments.Expense;
import settings.InitSetupForCombosAndToggles;
import validation.ValidateFieldsClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import static settings.NotificationClass.showNotification;
import static settings.ProceedConfirmation.confirm;

public class StaffFormAddController {

    private ResultSet rs;
    private EmployeeQueries empQuery = new EmployeeQueries();

    @FXML
    private JFXTextField fullNameTxt;

    @FXML
    private DatePicker dobTxt;

    @FXML
    private JFXRadioButton maleRadioBtn;

    @FXML
    private JFXCheckBox activeCheck;

    @FXML
    private JFXRadioButton femaleRadioBtn;

    @FXML
    private JFXTextField phoneNumberTxt;

    @FXML
    private JFXTextArea addressTxt;

    @FXML
    private JFXTextField physicalAddrTxt;

    @FXML
    private ComboBox<String> departmentCombo;

    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private JFXTextField hrsTxt;

    @FXML
    private DatePicker dateTxt;

    @FXML
    private Label feesLbl;

    @FXML
    private JFXTextField guardianNameTxt;

    @FXML
    private JFXTextField guardianNumberTxt;

    @FXML
    private JFXTextArea guardianPhysicalAddressTxt;

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        String dateRegistered, dob;
        String gender;

        if (!maleRadioBtn.isSelected()) {
            if (femaleRadioBtn.isSelected()) {
                gender = "female";
            } else {
                gender = "";
            }
        } else {
            gender = "male";
        }

        try {
            dateRegistered = dateTxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dob = dobTxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (NullPointerException ex) {
            dateRegistered = "";
            dob = "";
        }

        NextOfKin nextOfKin = new NextOfKin(
                "",
                guardianNameTxt.getText(),
                guardianNumberTxt.getText(),
                guardianPhysicalAddressTxt.getText()
        );

        Employee emp = new Employee(
                fullNameTxt.getText(), phoneNumberTxt.getText(),
                addressTxt.getText(), physicalAddrTxt.getText(),
                departmentCombo.getSelectionModel().getSelectedItem(),
                statusCombo.getSelectionModel().getSelectedItem(),
                hrsTxt.getText(), "", dateRegistered, gender, dob, activeCheck.isSelected()
        );

        if (new ValidateFieldsClass().validateStaff(emp, nextOfKin)) {
            boolean added = empQuery.addStaff(emp);

            if (!added) {

                showNotification(emp.getFullName() + " Has been added Succesfully");

                Employee staff = empQuery.getStaff(emp.getFullName());

                nextOfKin.setEmployee(staff.getId());

                empQuery.addNextOfKin(nextOfKin);
                new AddExpense().add(new Expense(emp.getFullName(), ""));

                clearTextFields();
                boolean confirmed = confirm("Employee");

                if (!confirmed) {
                    ((Node) event.getSource()).getScene().getWindow().hide();
                }

                FXMLLoader loader = new FXMLLoader();
                try {
                    loader.load(getClass().getResource("/staff/Staff.fxml").openStream());
                } catch (IOException ex) {
                    Logger.getLogger(StaffFormAddController.class.getName()).log(Level.SEVERE, null, ex);
                }
                StaffController controller = loader.getController();
                controller.initialize();

            }
        }
    }

    public void initialize() {
        InitSetupForCombosAndToggles setup = new InitSetupForCombosAndToggles();
        setup.setGenderToggleGroup(maleRadioBtn, femaleRadioBtn);
        setup.loadIntoDepartments(departmentCombo);
        setup.loadIntoStatus(statusCombo);
    }

    private void clearTextFields() {
        guardianNameTxt.setText("");
        guardianNumberTxt.setText("");
        guardianPhysicalAddressTxt.setText("");
        fullNameTxt.setText("");
        addressTxt.setText("");
        phoneNumberTxt.setText("");
        physicalAddrTxt.setText("");
        hrsTxt.setText("");
        activeCheck.setSelected(true);
        departmentCombo.getSelectionModel().select(null);
        statusCombo.getSelectionModel().select(null);
        dateTxt.setValue(null);
        dobTxt.setValue(null);

        maleRadioBtn.setSelected(false);
        femaleRadioBtn.setSelected(false);
    }

}
