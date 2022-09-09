package users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import login.LoginDocumentController;
import settings.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import static settings.AlertClass.makeAlert;
import static settings.AlertClass.makePromptAlert;

import static settings.NotificationClass.showNotification;

public class UsersController implements Initializable {

    @FXML
    private JFXCheckBox activeCheck;

    @FXML
    private Label userLbl;

    @FXML
    private AnchorPane ancho;

    @FXML
    private Label excelFileTxt;

    @FXML
    private JFXTextField fnameTxt;

    @FXML
    private JFXTextField lnameTxt;

    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXTextField phoneTxt;

    @FXML
    private JFXTextField emailTxt;

    @FXML
    private JFXComboBox<String> userTypeCombo;

    @FXML
    private JFXPasswordField passwordTxt;

    @FXML
    private JFXTextField passwdTxt;

    @FXML
    private Label imageLbl;

    @FXML
    private Circle imageCircle;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TextField filtersTxt;

    @FXML
    private TableView<User> userTable;

    @FXML
    private MenuItem refreshBtn;

    private FileInputStream fis;
    private FileChooser fileChooser;
    private File file;
    private Image image;
    private File prevImgFile;
    private final Image defaultImg = new Image("img/defaultImg3.png");
    private ObservableList<User> data = FXCollections.observableArrayList();
    private final ObservableList<String> types = FXCollections.observableArrayList();
    private FilteredList<User> filteredData;

    @FXML
    void browseFileAction(MouseEvent event) {
        browserImage();

    }

    @FXML
    void deleteAction(ActionEvent event) {

        User user = userTable.getSelectionModel().getSelectedItem();
        boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure; You wanna delete this User ?");
        if (user != null) {

            if (LoginDocumentController.userType.equals(user.getUsertype())) {
                makeAlert("warning", "You cannot delete this user!\nThis User is an administrator");
            } else {
                int response = new UserQueries().deleteUser(user.getUsername(), action);

                if (response != 404) {
                    loadData();
                    setLabels();
                    clearTextFields();
                    showNotification("User Deleted Successfully");
                }
            }

        } else {
            makeAlert("warning", "Please Select a user from the table");
        }
    }

    @FXML
    void excelFileAction(MouseEvent event) {

    }

    @FXML
    void filterAction(KeyEvent event) {
        filtersTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super User>) u -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (u.getUsername().contains(newValue)) {
                    return true;
                } else if (u.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (u.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (u.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (u.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (u.getUsertype().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedData);

        setLabels();
    }

    @FXML
    void refreshAction(ActionEvent event) {
        loadData();
        clearTextFields();
    }

    @FXML
    void saveAction(ActionEvent event) {
        User user = getUser();

        if (validateUser(user)) {
            if (checkUser(user.getUsername())) {
                boolean added = new UserQueries().addUser(user);

                if (added == false) {
                    showNotification(user.getFirstName() + " Has been added Succesfully");

                    clearTextFields();
                    loadData();
                    setLabels();
                }
            } else {
                makeAlert("warning", "The User you entered Exist");
            }
        }

    }

    private void setLabels() {
        userLbl.setText("Users -> " + new UserQueries().countUsers());
        activeCheck.setSelected(true);
    }

    private void clearTextFields() {
        prevImgFile = null;
        file = null;
        usernameTxt.setText("");
        fnameTxt.setText("");
        lnameTxt.setText("");
        phoneTxt.setText("");
        emailTxt.setText("");
        userTypeCombo.getSelectionModel().select(null);
        passwordTxt.setText("");
        passwdTxt.setText("");
        imageLbl.setText("");
        setDefaultImage();
        activeCheck.setSelected(false);
    }

    private boolean checkUser(String username) {

        return new UserQueries().getUser(username) == null;
    }

    private boolean validateUser(User user) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Pattern emailP = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");

        if (!user.getUsername().isEmpty() & !user.getFirstName().isEmpty() & !user.getLastName().isEmpty()
                & !user.getPhone().isEmpty() & !user.getEmail().isEmpty()
                & user.getUsertype() != null & !user.getPassword().isEmpty()) {

            if (pattern.matcher(user.getPhone()).matches()) {
                if (emailP.matcher(user.getEmail()).matches()) {
                    if (user.getPassword().equals(passwdTxt.getText())) {

                        if (!user.getUsertype().equals("admin")) {
                            return true;
                        } else {
                            makeAlert("warning", "You can not create another administrator");
                        }

                    } else {
                        makeAlert("warning", "Passwords dont match");
                    }
                } else {
                    makeAlert("warning", "Invalid Email Address");
                }

            } else {
                makeAlert("warning", "Phone number should be numeric");
            }
        } else {
            makeAlert("warning", "Please complete the fields");
        }

        return false;
    }

    private void autoFillFields() {
        User user = userTable.getSelectionModel().getSelectedItem();

        if (user != null) {
            usernameTxt.setText(user.getUsername());
            fnameTxt.setText(user.getFirstName());
            lnameTxt.setText(user.getLastName());
            phoneTxt.setText(user.getPhone());
            emailTxt.setText(user.getEmail());
            userTypeCombo.getSelectionModel().select(user.getUsertype());
            passwordTxt.setText(user.getPassword());
            passwdTxt.setText(user.getPassword());
            activeCheck.setSelected(user.isActive());

            if (user.getImage().length() > 0) {
                image = new Image("file:" + user.getImage(), 150, 200, true, true);
                imageLbl.setText(user.getImage());

                if (!image.isError()) {
                    imageCircle.setFill(new ImagePattern(image));
                    imageCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
                } else {
                    setDefaultImage();
                }

            } else {
                setDefaultImage();
            }

        }
    }

    @FXML
    void studentTableOnMouseClickedAction(MouseEvent event) {
        autoFillFields();
    }

    @FXML
    void studentsTableOnKeyReleasedAction(KeyEvent event) {
        if (event.getCode() == KeyCode.UP | event.getCode() == KeyCode.DOWN) {
            autoFillFields();
        }
    }

    @FXML
    void updateAction(ActionEvent event) {

        User user = getUser();

        if (LoginDocumentController.userType.equals(user.getUsertype())) {
            makeAlert("warning", "You cannot edit this user!\nThis User is an administrator");
        } else if (validateUser(user) & !checkUser(user.getUsername())) {
            boolean edited = new UserQueries().editUser(user);

            if (!edited) {
                showNotification(user.getFirstName() + " Has been Edited Succesfully");
                loadData();
                clearTextFields();
                //setLabels();
            }
        } else {
            makeAlert("error", "User not Found");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ancho.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());
        setUserTypes();
        setDefaultImage();
        loadData();
        setUserTable();
        setLabels();

        filteredData = new FilteredList<>(data, e -> true);
    }

    private void setDefaultImage() {
        imageCircle.setFill(new ImagePattern(defaultImg));
        imageCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        imageLbl.setText("");
    }

    private void setUserTypes() {
        types.clear();
        types.addAll("Receptionist", "Accountant", "Other");
        userTypeCombo.setItems(types);
    }

    private void setUserTable() {
        userTable.setItems(data);

        TableColumn col1 = new TableColumn("Username");
        TableColumn col2 = new TableColumn("First Name");
        TableColumn col3 = new TableColumn("Last Name");
        TableColumn col4 = new TableColumn("Phone");
        TableColumn col5 = new TableColumn("Email");
        TableColumn col6 = new TableColumn("User Type");
        //TableColumn col7 = new TableColumn("Password");
        TableColumn col8 = new TableColumn("Date Added");

        col1.setMinWidth(70);
        col2.setMinWidth(70);
        col3.setMinWidth(70);
        col4.setMinWidth(80);
        col5.setMinWidth(150);
        col6.setMinWidth(70);
        //col7.setMinWidth(70);
        col8.setMinWidth(150);

        userTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, /*col7,*/ col8);
        userTable.setEditable(true);

        col1.setCellValueFactory(new PropertyValueFactory<>("username"));
        col2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col4.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col5.setCellValueFactory(new PropertyValueFactory<>("email"));
        col6.setCellValueFactory(new PropertyValueFactory<>("usertype"));
        //col7.setCellValueFactory(new PropertyValueFactory<>("password"));
        col8.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadData() {
        data.clear();

        data.addAll(new UserQueries().getUsers());
    }

    private void browserImage() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        file = fileChooser.showOpenDialog(StageManager.browsingStage);

        if (file != null) {
            imageLbl.setText(file.getAbsolutePath());
            image = new Image(file.toURI().toString(), 170,
                    200, true, true);
            imageCircle.setFill(new ImagePattern(image));
            imageCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        }

    }

    private User getUser() {
        boolean active = false;
        String imagePath = imageLbl.getText();

        if (activeCheck.isSelected()) {
            active = true;
        }

        if (file != null) {
            imagePath = file.getAbsolutePath();
        }

        User user = new User(
                fnameTxt.getText(), lnameTxt.getText(), usernameTxt.getText(),
                phoneTxt.getText(), emailTxt.getText(), passwordTxt.getText(),
                userTypeCombo.getSelectionModel().getSelectedItem(), "", imagePath, active
        );

        return user;
    }

    @FXML
    void removeImage(MouseEvent event) {
        imageLbl.setText("");
        setDefaultImage();
    }

}
