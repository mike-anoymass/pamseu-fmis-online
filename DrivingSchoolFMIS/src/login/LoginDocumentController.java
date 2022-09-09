/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import users.UserCredentials;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import connection.SqlConnection;
import settings.ControlledScreen;
import drivingschoolfmis.DrivingSchoolFMIS;
import home.MainDocumentController;
import settings.ScreensController;
import settings.StageManager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import trail.TrailQueries;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author ANOYMASS
 */
public class LoginDocumentController implements Initializable, ControlledScreen {

    @FXML
    private ImageView imageview;
    int count;
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private JFXTextField usernameTxtField;
    @FXML
    private Label pcNameLbl;
    @FXML
    private Label title;
    @FXML
    private JFXButton closeBtn;
    @FXML
    private JFXPasswordField passwordTxtField;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private Text txt;
    
    public static String userName;
    public static String userType;
    public static String firstname;
    public static String lastname;

    ScreensController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setApplicationCloseConfirmation();

        progressBar.setVisible(false);

        title.setText(DrivingSchoolFMIS.schoolName + " school pamseu  FMIS");
        UserCredentials user = new UserCredentials();
        pcNameLbl.textProperty().bind(user.userPcNameProperty());

        closeBtn.setTooltip(new Tooltip("Close the Application"));

        user.passwordProperty().bind(passwordTxtField.textProperty());
        user.userNameProperty().bind(usernameTxtField.textProperty());

        slideShow();

        anchorpane.getStylesheets().add(getClass().getResource("/css/loginCss.css").toExternalForm());
    }

    @Override
    public void setScreenParent1(ScreensController screen) {
        controller = screen;
    }

    public void slideShow() {
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(new Image("img/p1.jpg"));
        images.add(new Image("img/p2.jpg"));
        images.add(new Image("img/p3.jpg"));
        images.add(new Image("img/p4.jpg"));
        images.add(new Image("img/p5.jpeg"));
        images.add(new Image("img/p6.jpg"));
        images.add(new Image("img/p7.jpg"));
        images.add(new Image("img/p8.jpg"));
        images.add(new Image("img/p9.jpg"));
        images.add(new Image("img/p10.jpg"));
        images.add(new Image("img/p11.jpg"));
        images.add(new Image("img/p12.jpg"));
        images.add(new Image("img/p13.jpg"));
        images.add(new Image("img/p14.jpg"));
        images.add(new Image("img/p15.jpeg"));
        final DoubleProperty op = imageview.opacityProperty();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5.0), event -> {
            imageview.setImage(images.get(count));
            count++;

            if (count == 15) {
                count = 0;
            }
        }, new KeyValue(op, 0.0)));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    @FXML
    private void quit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void loadMainPage(ActionEvent event) {

        progressBar.setVisible(true);

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                updateMessage("Loading...");
                boolean b = checkCredentials(usernameTxtField.getText(), passwordTxtField.getText());
                updateMessage("Finished Loading");
                return b;
            }
        };

        task.setOnSucceeded(e -> {
            boolean loggedIn = task.getValue();

            if (loggedIn) {
                usernameTxtField.setText("");
                passwordTxtField.setText("");
                progressBar.setVisible(false);
                recordTrail();
                loadMainWindow();
            } else {
                alertError("LOGIN INFORMATION", "->You have entered Invalid User Name or Password"
                        + "\nKindly check your input and try again\nOr your account is inactive");

                progressBar.setVisible(false);
            }
        });

        task.setOnFailed(e -> {
            alertError("Failed to load", "Login Failed\nTry Logging in again");
        });

        progressBar.progressProperty().bind(task.progressProperty());
        txt.textProperty().bind(task.messageProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    private boolean checkCredentials(String username, String passwd) {
        Connection conn = new SqlConnection().DbConnector();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM users where username=? AND"
                    + " password=? and isActive=?");
            pst.setString(1, username);
            pst.setString(2, passwd);
            pst.setBoolean(3, true);
            rs = pst.executeQuery();

            while (rs.next()) {
                userName = rs.getString("userName");
                userType = rs.getString("role");
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname");
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(LoginDocumentController.class.getName()).log(Level.SEVERE, null, ex);

            return false;

        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }

                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void loadMainWindow() {
        anchorpane.getScene().getWindow().hide();
        TrayNotification tray = new TrayNotification("Anoymass Programs",
                "Welcome " + userName + " to " + DrivingSchoolFMIS.schoolName + " Pamseu FMIS",
                NotificationType.SUCCESS);

        tray.setAnimationType(AnimationType.SLIDE);

        tray.showAndDismiss(Duration.seconds(3));
        anchorpane.getScene().getWindow().hide();
        try {

            FXMLLoader loader = new FXMLLoader();

            AnchorPane root = loader.load(getClass().getResource("/home/MainDocument.fxml").openStream());
            MainDocumentController controller = (MainDocumentController) loader.getController();
            controller.setNameLbl(userName);

            //controller.setAlertWindow(alertWindow);
            Scene scene = new Scene(root);
            StageManager.ParentStage.setScene(scene);
            StageManager.ParentStage.setTitle(DrivingSchoolFMIS.schoolName + " Pamseu FMIS");
            StageManager.ParentStage.show();

        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.exit(1);
        }
    }

    private void setApplicationCloseConfirmation() {
        StageManager.loginStage.getIcons().add(new Image("img/driving_16.png"));
        ///confirm closing the App
        StageManager.loginStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "! Are you sure to exit the IMS");

            Button exitButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            exitButton.setText("Exit");

            alert.setTitle("Application Close Confirmation");
            alert.setHeaderText("Confirm Exit");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(StageManager.loginStage);
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

    private void alertError(String headerText, String contextText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("img/warning_32.png"));
        alert.show();
    }

    private void recordTrail() {
        new TrailQueries().add(userName);
    }

}
