package students;

import validation.ValidateFieldsClass;
import data.CheckExistence;
import settings.InitSetupForCombosAndToggles;
import discount.Discount;
import discount.DiscountQueries;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import login.LoginDocumentController;
import static mail.Mail.sendEmail;

import static settings.AlertClass.makeAlert;
import static settings.NotificationClass.showNotification;
import static settings.ProceedConfirmation.confirm;

public class StudentFormAddController implements Initializable {

    private ResultSet rs;

    @FXML
    private JFXTextField fullNameTxt;

    @FXML
    private JFXTextField phoneNumberTxt;

    @FXML
    private JFXTextArea addressTxt;

    @FXML
    private JFXTextField guadianNameTxt;

    @FXML
    private JFXTextField guardianPhoneTxt;

    @FXML
    private JFXTextArea guardianAdressTxt;

    @FXML
    private JFXRadioButton maleRadioBtn;

    @FXML
    private JFXRadioButton femaleRadioBtn;

    @FXML
    private ComboBox<String> courseCombo;

    @FXML
    private ComboBox<String> categoryCombo;

    @FXML
    private JFXTextField trnTxt;

    @FXML
    private DatePicker dateTxt;

    @FXML
    private JFXCheckBox discountCheck;

    @FXML
    private JFXTextField discountTxt;

    @FXML
    private JFXCheckBox govtFeeCheck;

    @FXML
    private Label feesLbl;

    @FXML
    void categoryComboOnAction(ActionEvent event) {
        new InitSetupForCombosAndToggles().categoryComboAction(courseCombo, categoryCombo, feesLbl);
    }

    @FXML
    void courseComboOnAction(ActionEvent event) {
        String courseID = courseCombo.getSelectionModel().getSelectedItem();
        new InitSetupForCombosAndToggles().loadCategoriesForThisCourse(courseID, categoryCombo);
    }

    @FXML
    void discountCheckOnAction(ActionEvent event) {
        if (discountCheck.isSelected()) {
            discountTxt.setVisible(true);
        } else {
            discountTxt.setVisible(false);
        }
    }

    @FXML
    void govtFeeCheckOnAction(ActionEvent event) {

    }

    @FXML
    void saveBtnOnAction(ActionEvent event) throws IOException {
        String gender, course;
        String dateRegistered;
        String[] courseArray;
        String trn = "";
        course = "";

        if (trnTxt.getText().length() == 0) {
            trn = null;
        }

        if (maleRadioBtn.isSelected()) {
            gender = "male";
        } else if (femaleRadioBtn.isSelected()) {
            gender = "female";
        } else {
            gender = "";
        }

        if (courseCombo.getSelectionModel().getSelectedItem() != null) {
            courseArray = courseCombo.getSelectionModel().getSelectedItem().split("~");
            course = courseArray[1];
        }

        try {
            dateRegistered = dateTxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (NullPointerException ex) {
            dateRegistered = "";
        }

        Student student = new Student(
                fullNameTxt.getText(), addressTxt.getText(), phoneNumberTxt.getText(),
                gender, course, categoryCombo.getSelectionModel().getSelectedItem(),
                "", dateRegistered, trn, discountCheck.isSelected(), govtFeeCheck.isSelected(), false,
                "", guadianNameTxt.getText(), guardianPhoneTxt.getText(), guardianAdressTxt.getText()
        );

        if (new ValidateFieldsClass().validateStudent(student, discountTxt.getText())) {
            if (new CheckExistence().checkStudentExist(student.getStudent_id())) {
                boolean added = StudentQueries.addStudent(student);

                if (!added) {
                    showNotification(student.getStudent_name() + " Has been added Succesfully");

                    Student s1 = new StudentQueries().getStudentByPhone(student.getStudent_phone());

                    new StudentQueries().addGuardian(
                            new Guardian(s1.getStudent_id(), student.getGuardian_name(),
                                    student.getGuardian_phone(), student.getGuardian_physicalAddress())
                    );

                    if (student.isAnyDiscount()) {

                        Student s = new StudentQueries().getStudentByPhone(student.getStudent_phone());

                        new DiscountQueries().addDiscount(new Discount(s.getStudent_id(), discountTxt.getText()));

                    }

                    clearTextFields();
                    boolean confirmed = confirm("Student");

                    if (!confirmed) {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                    }

                    System.out.println(student.toString());

                    sendEmail("New Student Added", toString(student));

                    FXMLLoader loader = new FXMLLoader();
                    loader.load(getClass().getResource("/students/Students.fxml").openStream());
                    StudentsController controller = loader.getController();
                    controller.initialize();
                }
            } else {
                makeAlert("warning", "The Student you entered Exist");
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitSetupForCombosAndToggles setup = new InitSetupForCombosAndToggles();
        setup.setGenderToggleGroup(maleRadioBtn, femaleRadioBtn);
        setup.loadIntoCourses(courseCombo);
        hideDiscounField();
    }

    private void clearTextFields() {
        trnTxt.setText("");
        fullNameTxt.setText("");
        addressTxt.setText("");
        phoneNumberTxt.setText("");
        guadianNameTxt.setText("");
        guardianAdressTxt.setText("");
        guardianPhoneTxt.setText("");
        discountTxt.setText("");
        courseCombo.getSelectionModel().select(null);
        categoryCombo.getSelectionModel().select(null);
        dateTxt.setValue(null);
        feesLbl.setText("");

        maleRadioBtn.setSelected(false);
        femaleRadioBtn.setSelected(false);
        discountCheck.setSelected(false);
        discountTxt.setVisible(false);
        govtFeeCheck.setSelected(false);
    }

    public String toString(Student student) {
        String details
                = "===================STUDENT DETAILS================"
                + "\nFULL NAME: " + student.getStudent_name()
                + "\nGENDER: " + student.getGender()
                + "\nCOURSE: " + student.getCourse()
                + "\nCOURSE TYPE: " + student.getCourseType()
                + "\nPHONE NUMBER: " + student.getStudent_phone()
                + "\nPOSTAL ADDRESS: " + student.getStudent_address()
                + "\n----------------------------------------------------------"
                + "\nGUARDIAN NAME: " + student.getGuardian_name()
                + "\nDATE REGISTERED: " + student.getDateRegistered()
                + "\nADDED BY: " + LoginDocumentController.firstname + " " + LoginDocumentController.lastname;

        return details;
    }

    private void hideDiscounField() {
        if (!LoginDocumentController.userType.equals("admin")) {
            discountCheck.setVisible(false);
        }
    }

}
