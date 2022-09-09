package students;

import validation.ValidateFieldsClass;
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
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import login.LoginDocumentController;

import static settings.NotificationClass.showNotification;

public class StudentFormEditController {

    Student student;
    ResultSet rs;

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
    private Label feesLbl;

    @FXML
    private JFXCheckBox graduatedCheck;

    @FXML
    private JFXCheckBox discountCheck;

    @FXML
    private JFXTextField discountTxt;

    @FXML
    private JFXCheckBox govtFeeCheck;

    @FXML
    void categoryComboOnAction(ActionEvent event) {
        new InitSetupForCombosAndToggles().categoryComboAction(courseCombo, categoryCombo, feesLbl);
    }

    @FXML
    void graduatedCheckOnAction(ActionEvent event) {

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
    void editBtnOnAction(ActionEvent event) {
        String gender;
        String dateRegistered;
        String trn = null;

        if (trnTxt.getText() != null && trnTxt.getText().length() == 0) {
            trn = null;
        } else {
            trn = trnTxt.getText();
        }

        if (maleRadioBtn.isSelected()) {
            gender = "male";
        } else if (femaleRadioBtn.isSelected()) {
            gender = "female";
        } else {
            gender = "";
        }

        try {
            dateRegistered = dateTxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (NullPointerException ex) {
            dateRegistered = "";
        }

        Student student = new Student(
                this.student.getStudent_id(), fullNameTxt.getText(), addressTxt.getText(), phoneNumberTxt.getText(),
                dateRegistered, gender, trn, discountCheck.isSelected(), govtFeeCheck.isSelected(), graduatedCheck.isSelected(),
                this.student.getStudent_id(), guadianNameTxt.getText(), guardianPhoneTxt.getText(), guardianAdressTxt.getText(),
                courseCombo.getSelectionModel().getSelectedItem(), categoryCombo.getSelectionModel().getSelectedItem()
        );

        if (new ValidateFieldsClass().validateStudent(student, discountTxt.getText())) {
            boolean edited = new StudentQueries().editStudent(student, this.student.getStudent_id());

            if (!edited) {
                int count = 0;
                showNotification(student.getStudent_name() + " Has been Edited Succesfully");

                StudentQueries.editStudentGuardian(
                        new Guardian(this.student.getStudent_id(), guadianNameTxt.getText(), guardianPhoneTxt.getText(), guardianAdressTxt.getText()),
                        this.student.getStudent_id());

                if (!student.isAnyDiscount()) {
                    new DiscountQueries().deleteDiscount(this.student.getStudent_id());
                } else if (student.isAnyDiscount()) {

                    Discount discount = new DiscountQueries().getDiscount(this.student.getStudent_id());
                    
                    if(discount != null){
                        new DiscountQueries().editDiscount(this.student.getStudent_id(), discountTxt.getText());
                        count++;
                    }
                    
                    if (count == 0) {
                        new DiscountQueries().addDiscount(new Discount(this.student.getStudent_id(),
                                discountTxt.getText()));
                    }
                }

                ((Node) event.getSource()).getScene().getWindow().hide();
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.load(getClass().getResource("/students/Students.fxml").openStream());
                    StudentsController controller = loader.getController();
                    controller.initialize();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void govtFeeCheckOnAction(ActionEvent event) {

    }

    public void initialize(Student student) {
        this.student = student;
        InitSetupForCombosAndToggles setup = new InitSetupForCombosAndToggles();
        setup.setGenderToggleGroup(maleRadioBtn, femaleRadioBtn);
        setup.loadIntoCourses(courseCombo);

        autoFillForm();
        hideDiscounField();
    }

    private void autoFillForm() {
        fullNameTxt.setText(student.getStudent_name());
        phoneNumberTxt.setText(student.getStudent_phone());
        addressTxt.setText(student.getStudent_address());

        if (student.getGender().equals("male")) {
            maleRadioBtn.setSelected(true);
        } else if (student.getGender().equals("female")) {
            femaleRadioBtn.setSelected(true);
        } else {
            maleRadioBtn.setSelected(false);
            femaleRadioBtn.setSelected(false);
        }

        courseCombo.getSelectionModel().select(student.getCourse());
        categoryCombo.getSelectionModel().select(student.getCourseType());
        trnTxt.setText(student.getTrn());
        dateTxt.setValue(LocalDate.parse(student.getDateRegistered()));

        Guardian guardian = new StudentQueries().getStudentGuardian(student.getStudent_id());

        if (guardian != null) {
            guadianNameTxt.setText(guardian.getGuardian_name());
            guardianPhoneTxt.setText(guardian.getGuardian_phone());
            guardianAdressTxt.setText(guardian.getGuardian_physicalAddress());
        }

        if (student.isAnyDiscount()) {
            discountCheck.setSelected(true);
            discountTxt.setVisible(true);

            Discount discount = new DiscountQueries().getDiscount(student.getStudent_id());

            discountTxt.setText(discount.getAmount());

        }

        if (student.isAnyGovtFee()) {
            govtFeeCheck.setSelected(true);
        }

        if (student.isGraduated()) {
            graduatedCheck.setSelected(true);
        }

    }
    
    private void hideDiscounField() {
        if (!LoginDocumentController.userType.equals("admin")) {
            discountCheck.setVisible(false);
            discountTxt.setDisable(true);
        }
    }
}
