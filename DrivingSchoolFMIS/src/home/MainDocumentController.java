/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import settings.FxmlLoader;
import settings.StageManager;
import login.LoginDocumentController;
import users.UserQueries;
import com.jfoenix.controls.JFXButton;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import static settings.AlertClass.makeAlert;
import users.User;

/**
 * FXML Controller class
 *
 * @author ANOYMASS
 */
public class MainDocumentController implements Initializable {

    @FXML
    private Label userFullNameLbl;

    @FXML
    private Label userTypeLbl;

    private Window loginWindow;

    String username;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane anchorPane;
    private Window alertWindow;
    @FXML
    private JFXButton loginOutBtn;

    @FXML
    private Button btnCommision;

    @FXML
    private Circle imageCircle;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnCourses;

    @FXML
    private Button btnTests;

    @FXML
    private Button btnStaff;

    @FXML
    private Button btnReceipts;

    @FXML
    private Button btnPayments;

    @FXML
    private Button btnBalances;

    @FXML
    private Button btnVehicles;

    @FXML
    private Button btnAllocations;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnAudit;

    @FXML
    private Button btnLogout;

    private final Image defaultImg = new Image("img/defaultImg3.png");
    private Image image;
    private File prevImgFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setInitialAppScreenSize();

        setApplicationCloseConfirmation();

        setLabels();

        loadHome();

        setTooltips();

    }

    private void setLabels() {
        userFullNameLbl.setText(LoginDocumentController.firstname + " " + LoginDocumentController.lastname);
        userTypeLbl.setText(LoginDocumentController.userType);

        User user = new UserQueries().getUser(LoginDocumentController.userName);

        if (user.getImage().length() > 0) {
            image = new Image("file:" + user.getImage(), 150, 200, true, true);

            if (!image.isError()) {
                imageCircle.setFill(new ImagePattern(image));
                imageCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            } else {
                seDefaulttImg();
            }

        } else {
            seDefaulttImg();
        }

    }

    private void seDefaulttImg() {
        imageCircle.setFill(new ImagePattern(defaultImg));
        imageCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    }

    public void setNameLbl(String name) {
        username = name;

    }

    @FXML
    private void openChildWindow(ActionEvent event) {
        StageManager.announcementStage.show();
    }

    @FXML
    private void goToLoginPage(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm Login out");
        //alert.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REMOVE, "20px"));
        alert.setHeaderText(null);
        alert.setContentText("Are you sure; You want to log out ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Window mainWindow = ((Node) event.getSource()).getScene().getWindow();
            mainWindow.hide();

            StageManager.loginStage.show();
        }

    }

    public void setLoginWindow(Window loginWindow) {

    }

    private void loadHome() {

        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getDocument("/home/Home");
        borderPane.setCenter(view);

    }

    @FXML
    private void gotoReceipts(ActionEvent event) {
        if (LoginDocumentController.userType.equals("admin") || LoginDocumentController.userType.equals("Accountant")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/receipts/Receipts");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }

    }

    @FXML
    private void goToBalances(ActionEvent event) {
        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getDocument("/balances/Balances");
        borderPane.setCenter(view);
    }

    @FXML
    private void goToTests(ActionEvent event) {

        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getDocument("/tests/Tests");
        borderPane.setCenter(view);
    }

    @FXML
    private void goToStaff(ActionEvent event) {
        if (LoginDocumentController.userType.equals("admin")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/staff/Staff");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }

    }

    private void setInitialAppScreenSize() {
        javafx.geometry.Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        anchorPane.setMinHeight(bounds.getHeight() - 100);
        anchorPane.setMinWidth(bounds.getWidth() - 60);
    }

    private void setApplicationCloseConfirmation() {
        StageManager.ParentStage.getIcons().add(new Image("img/driving_16.png"));
        ///confirm closing the App
        StageManager.ParentStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    username + "! Are you sure to exit the FMIS");

            Button exitButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            exitButton.setText("Exit");

            alert.setTitle("Application Close Confirmation");
            alert.setHeaderText("Confirm Exit");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(StageManager.ParentStage);
            //alert.setX(StageManager.ParentStage.getX());
            //alert.setY(StageManager.ParentStage.getY() + StageManager.ParentStage.getHeight());

            //setting an icon to alert Dialog- first thing, get he window of the alert dialog
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("img/warning_32.png"));

            Optional<ButtonType> closeOptional = alert.showAndWait();
            if (!ButtonType.OK.equals(closeOptional.get())) {
                event.consume();
                Platform.exit();
            }

        });
    }

    @FXML
    private void goToCourses(ActionEvent event) {
        if (LoginDocumentController.userType.equals("admin")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/courses/Courses");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }

    }

    @FXML
    public void goToStudents(ActionEvent event) {
        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getDocument("/students/Students");
        borderPane.setCenter(view);
    }

    @FXML
    private void goToPayments(ActionEvent event) {

        if (LoginDocumentController.userType.equals("admin") || LoginDocumentController.userType.equals("Accountant")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/payments/Payments");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }
    }

    @FXML
    private void goToReport(ActionEvent event) {
        if (LoginDocumentController.userType.equals("admin") || LoginDocumentController.userType.equals("Accountant")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/financial_report/FinancialReport");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }
    }

    @FXML
    private void goToHome(ActionEvent event) {
        loadHome();
    }

    @FXML
    void newStaffWindow(ActionEvent event) {
        if (LoginDocumentController.userType.equals("admin")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                AnchorPane root = loader.load(getClass().getResource("/staff/StaffFormAdd.fxml").openStream());

                Scene scene = new Scene(root);
                StageManager.staffAddStage.setScene(scene);
                StageManager.staffAddStage.getIcons().add(new Image("img/newstudenticon_16.png"));
                StageManager.staffAddStage.setTitle("New Staff");

                StageManager.staffAddStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }

    }

    @FXML
    void newStudentWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = loader.load(getClass().getResource("/students/StudentFormAdd.fxml").openStream());
            Scene scene = new Scene(root);
            StageManager.studentFormAddStage.setScene(scene);
            StageManager.studentFormAddStage.getIcons().add(new Image("img/newstudenticon_16.png"));
            StageManager.studentFormAddStage.setTitle("New Student");

            StageManager.studentFormAddStage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void goToUsers(ActionEvent actionEvent) {
        if (LoginDocumentController.userType.equals("admin")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/users/Users");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }
    }

    @FXML
    void auditTrail(ActionEvent event) {
        if (LoginDocumentController.userType.equals("admin")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/trail/Trail");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }
    }

    @FXML
    void closeTheApp(ActionEvent event) {

    }

    @FXML
    void goToCommision(ActionEvent event) {
        if (!LoginDocumentController.userType.equals("Receptionist")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/commision/Commision");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }
    }

    @FXML
    public void goToVehicles(ActionEvent actionEvent) {
        if (LoginDocumentController.userType.equals("admin")) {
            FxmlLoader loader = new FxmlLoader();
            Pane view = loader.getDocument("/vehicles/Vehicle");
            borderPane.setCenter(view);
        } else {
            makeAlert("error", "Access denied\nYou do not have access to this module");
        }
    }

    @FXML
    void goToAllocations(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("/vehicles/Allocations.fxml").openStream());

            Scene scene = new Scene(root);
            StageManager.allocationStage.setScene(scene);
            StageManager.allocationStage.getIcons().add(new Image("img/newstudenticon_16.png"));
            StageManager.allocationStage.setTitle("Student Allocation");

            StageManager.allocationStage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void setTooltips() {

        btnHome.setTooltip(new Tooltip("Dashboard"));

        btnStudent.setTooltip(new Tooltip("Manage Students"));

        btnCourses.setTooltip(new Tooltip("Manage Courses"));

        btnTests.setTooltip(new Tooltip("Manage Driving Tests"));

        btnStaff.setTooltip(new Tooltip("Manage Staff"));

        btnReceipts.setTooltip(new Tooltip("Manage Receipts"));

        btnPayments.setTooltip(new Tooltip("Manage Payments"));

        btnBalances.setTooltip(new Tooltip("Explore Fees Balances"));

        btnVehicles.setTooltip(new Tooltip("Manage Vehicles"));

        btnAllocations.setTooltip(new Tooltip("Manage Vehicle Allocations"));

        btnReport.setTooltip(new Tooltip("Financial Report"));

        btnUsers.setTooltip(new Tooltip("Manage Users"));

        btnAudit.setTooltip(new Tooltip("Audit Trail"));

        btnLogout.setTooltip(new Tooltip("Lock the application"));

        btnCommision.setTooltip(new Tooltip("Manage Commision"));
    }

}
