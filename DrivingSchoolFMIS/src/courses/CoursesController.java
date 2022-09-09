package courses;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.concurrent.Task;

import static settings.AlertClass.makeAlert;
import static settings.AlertClass.makePromptAlert;
import static settings.NotificationClass.showNotification;

public class CoursesController {

    private final ObservableList<Course> courseData = FXCollections.observableArrayList();
    private final ObservableList<CourseType> courseTypeData = FXCollections.observableArrayList();
    private final ObservableList<String> coursesForCombo = FXCollections.observableArrayList();
    private final ObservableList<String> courseTypesForCombo = FXCollections.observableArrayList();
    private final ObservableList<CourseDuration> ccData = FXCollections.observableArrayList();
    private String[] courseArray, durationArray;
    private String courseID, durationID;
    FilteredList<Course> filteredCourseData = new FilteredList<>(courseData, e -> true);
    FilteredList<CourseType> filteredCourseTypeData = new FilteredList<>(courseTypeData, e -> true);
    FilteredList<CourseDuration> filteredCcData = new FilteredList<>(ccData, e -> true);

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label courseLbl;

    @FXML
    private JFXTextField courseCodeTxt;

    @FXML
    private JFXTextField courseNameTxt;

    @FXML
    private TextField filterCoursesTxt;

    @FXML
    private TextField filterCoursesTypeTxt;

    @FXML
    private TextField filterCcTxt;

    @FXML
    private JFXTextField govtFeeTxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private MenuItem refreshMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private JFXTextField durationNameTxt;

    @FXML
    private JFXTextField daysTxt;

    @FXML
    private JFXButton saveBtn1;

    @FXML
    private JFXButton updateBtn1;

    @FXML
    private JFXButton deleteBtn1;

    @FXML
    private Label courseTypeLbl;

    @FXML
    private TableView<CourseType> courseTypeTable;

    @FXML
    private MenuItem refreshMenuItem1;

    @FXML
    private MenuItem deleteMenuItem1;

    @FXML
    private ComboBox<String> courseCombo;

    @FXML
    private ComboBox<String> durationCombo;

    @FXML
    private JFXTextField amountTxt;

    @FXML
    private JFXButton updateBtn11;

    @FXML
    private TableView<CourseDuration> ccDurationTable;

    @FXML
    private MenuItem refreshMenuItem11;

    @FXML
    private MenuItem deleteMenuItem11;

    @FXML
    private Label courseInfoLbl;

    @FXML
    void ccOnKeyRelAction(KeyEvent event) {

    }

    @FXML
    void ccOnKeyReleasedAction(KeyEvent event) {
        filterCcTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredCcData.setPredicate((Predicate<? super CourseDuration>) course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (course.getCode().contains(newValue)) {
                    return true;
                } else if (course.getDuration().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (course.getAmount().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (course.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<CourseDuration> sortedData = new SortedList<>(filteredCcData);
        sortedData.comparatorProperty().bind(ccDurationTable.comparatorProperty());
        ccDurationTable.setItems(sortedData);
    }

    @FXML
    void ccTableMouseClicked(MouseEvent event) {

    }

    @FXML
    void courseDurationOnKeyReleased(KeyEvent event) {;
        if (event.getCode() == KeyCode.UP | event.getCode() == KeyCode.DOWN) {
            autoFillCourseTypeTxtFiels();
        }

    }

    @FXML
    void courseTableClickedAction(MouseEvent event) {
        autoFillCourseTxtFields();
    }

    @FXML
    void courseTableOnKeyReleasedAction(KeyEvent event) {
        if (event.getCode() == KeyCode.UP | event.getCode() == KeyCode.DOWN) {
            autoFillCourseTxtFields();
        }
    }

    @FXML
    void courseTypeTableOnMouseClickedAction(MouseEvent event) {
        autoFillCourseTypeTxtFiels();
    }

    @FXML
    void coursesOnKeyReleasedAction(KeyEvent event) {
        filterCoursesTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredCourseData.setPredicate((Predicate<? super Course>) course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (course.getCode().contains(newValue)) {
                    return true;
                } else if (course.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (course.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });
        SortedList<Course> sortedData = new SortedList<>(filteredCourseData);
        sortedData.comparatorProperty().bind(courseTable.comparatorProperty());
        courseTable.setItems(sortedData);
    }

    @FXML
    void categoryFilter(KeyEvent event) {
        filterCoursesTypeTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredCourseTypeData.setPredicate((Predicate<? super CourseType>) course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (course.getDays().contains(newValue)) {
                    return true;
                } else if (course.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });
        SortedList<CourseType> sortedData = new SortedList<>(filteredCourseTypeData);
        sortedData.comparatorProperty().bind(courseTypeTable.comparatorProperty());
        courseTypeTable.setItems(sortedData);
    }

    @FXML
    void deleteCourseAction(ActionEvent event) {
        String courseCode = courseCodeTxt.getText();
        boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure; wanna delete, " + courseCode + "\n"
                + "Note that deleting this course will delete all students belonging to this course\n"
                + "This procedure is irreversible- we hope you know what you are doing");

        if (checkCompletedFields()) {
            int response = new CourseQueries().deleteCourse(courseCode, action);

            if (response != 404) {
                clearTextFieldsFor("course");
                loadCourseTableData();
                setCourseComboBoxData();
                loadCcTableData();
                setLabels();
                showNotification("Course Deleted Successfully");
            }

        } else {
            makeAlert("warning", "Select course from the table");
        }
    }

    @FXML
    void deleteCourseInfoAction(ActionEvent event) {
        boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure; wanna delete this record? \n"
                + "Deleting this record will delete all students associated with it!\n"
                + "This procedure is irreversible- we hope that you know what you are doing");

        CourseDuration cd = ccDurationTable.getSelectionModel().getSelectedItem();
        if (cd != null) {
            int response = new CourseQueries().deleteCourseInfo(cd.getCourseID(), cd.getDurationID(), action);

            if (response != 404) {
                clearTextFieldsFor("courseInfo");
                loadCcTableData();
                setLabels();
                showNotification("Course Info Deleted Successfully");
            }
        } else {
            makeAlert("warning", "Nothing To Delete\nPlease Select the record you want to delete from the table");
        }
    }

    @FXML
    void deleteCourseTypeAction(ActionEvent event) {
        boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure; wanna delete this category ?\n"
                + "Note that deleting this category will delete all students and courses belonging to it\n"
                + "This procedure is irreversible- we hope you know what you are doing");

        CourseType cType = courseTypeTable.getSelectionModel().getSelectedItem();

        if (cType != null) {

            int response = new CourseQueries().deleteCourseType(cType.getId(), action);

            if (response != 404) {
                clearTextFieldsFor("courseType");
                loadCourseTypeTableData();
                setCourseTypeComboBoxData();
                loadCcTableData();
                setLabels();
                showNotification("Category Deleted Successfully");
            }

        } else {
            makeAlert("warning", "Select the course you want to delete from the table");
        }
    }

    @FXML
    void refreshCourseAction(ActionEvent event) {

    }

    @FXML
    void refreshCourseInfoAction(ActionEvent event) {

    }

    @FXML
    void refreshCourseTypeAction(ActionEvent event) {

    }

    @FXML
    void saveCcAction(ActionEvent event) {
        initializeCourseAndDurationVars();
        
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                Platform.runLater(() -> {

                    if (validateCourseInfo()) {
                        if (checkIfCourseInfoExist()) {
                            CourseDuration info = new CourseDuration(courseID, durationID, amountTxt.getText(), "date");

                            boolean added = new CourseQueries().addCourseInfo(info);

                            if (added == false) {
                                showNotification("Course Details Have been added Succesfully");

                                clearTextFieldsFor("courseInfo");
                                loadCcTableData();
                                setLabels();

                            }
                        } else {
                            makeAlert("warning", "The duration you entered Exist");
                        }
                    }
                });

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

    }

    @FXML
    void saveCourseAction(ActionEvent event) {

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                Platform.runLater(() -> {
                    if (checkCompletedFields()) {
                        if (checkIfCourseExist()) {
                            Course course = new Course(courseCodeTxt.getText(), courseNameTxt.getText(), govtFeeTxt.getText());
                            boolean added = new CourseQueries().addCourse(course);

                            if (added == false) {
                                showNotification(courseNameTxt.getText() + " Has been added Succesfully");

                                clearTextFieldsFor("course");
                                loadCourseTableData();
                                setCourseComboBoxData();
                                setLabels();

                            }
                        } else {
                            makeAlert("warning", "The course you entered Exist");
                        }
                    }
                });

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

    }

    @FXML
    void saveDurationAction(ActionEvent event) {

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        CourseType cType = new CourseType(durationNameTxt.getText(), daysTxt.getText());

                        if (validateDuration(cType)) {
                            if (checkIfCourseTypeExist()) {
                                boolean added = new CourseQueries().addCourseType(cType);

                                if (added == false) {
                                    showNotification(durationNameTxt.getText() + " Has been added Succesfully");

                                    clearTextFieldsFor("courseType");
                                    loadCourseTypeTableData();
                                    setCourseTypeComboBoxData();
                                    setLabels();
                                }
                            } else {
                                makeAlert("warning", "The duration you entered Exist");
                            }
                        }
                    }
                });

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

    }

    @FXML
    void updateCourseAction(ActionEvent event) {

        if (checkCompletedFields()) {
            Course c = courseTable.getSelectionModel().getSelectedItem();
            if (c != null) {
                Course course = new Course(
                        c.getId(),
                        courseCodeTxt.getText(),
                        courseNameTxt.getText(),
                        govtFeeTxt.getText());
                boolean edited = new CourseQueries().editCourse(course);

                if (edited == false) {
                    showNotification(courseNameTxt.getText() + " Has been Edited Succesfully");

                    clearTextFieldsFor("course");
                    loadCourseTableData();
                    setCourseComboBoxData();
                    loadCcTableData();
                }
            } else {
                makeAlert("warning", "Please click on the course you want to edit first from the table");
            }
        } else {
            makeAlert("error", "Course not Found");
        }

    }

    @FXML
    void updateCourseInfoAction(ActionEvent event) {
        initializeCourseAndDurationVars();
        if (!checkIfCourseInfoExist() & validateCourseInfo()) {
            CourseDuration cs = new CourseDuration(courseID, durationID, amountTxt.getText(), "date");
            boolean edited = new CourseQueries().editCourseInfo(cs);

            if (edited == false) {
                showNotification("Course Information has been Edited Succesfully");

                clearTextFieldsFor("courseInfo");
                loadCcTableData();
            }
        } else {
            makeAlert("error", "Course Information not Found");
        }
    }

    @FXML
    void updateDurationAction(ActionEvent event) {
        CourseType c = courseTypeTable.getSelectionModel().getSelectedItem();

        if (c != null) {
            CourseType cType = new CourseType(c.getId(), durationNameTxt.getText(), daysTxt.getText());
            if (validateDuration(cType)) {
                boolean edited = new CourseQueries().editCourseType(cType);

                if (edited == false) {
                    showNotification(durationNameTxt.getText() + " Has been Edited Succesfully");

                    clearTextFieldsFor("courseType");
                    loadCourseTypeTableData();
                    setCourseTypeComboBoxData();
                    loadCcTableData();
                }
            }
        } else {
            makeAlert("warning", "Please select the category you want to edit from the table");
        }
    }

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());

        setLabels();

        loadCourseTableData();
        setCourseTable();

        loadCcTableData();
        setCcTable();

        loadCourseTypeTableData();
        setCourseTypeTable();

        //setCourseComboBoxData();
        //setCourseTypeComboBoxData();
    }

    private void loadCourseTableData() {

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                Platform.runLater(() -> {
                    courseData.clear();

                    courseData.addAll(new CourseQueries().getAllCourses());

                    setCourseComboBoxData();
                });

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

    }

    public void setCourseTable() {
        courseTable.setItems(courseData);
        courseTable.setEditable(true);

        TableColumn col1 = new TableColumn("Course Code");
        TableColumn col2 = new TableColumn("Course Name");
        TableColumn col3 = new TableColumn("Government Fee");

        col1.setMinWidth(100);
        col2.setMinWidth(250);
        col3.setMinWidth(150);

        col1.setCellValueFactory(new PropertyValueFactory<>("code"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("governmentFee"));

        courseTable.getColumns().addAll(col1, col2, col3);
    }

    private void setCourseTypeTable() {
        courseTypeTable.setItems(courseTypeData);
        courseTypeTable.setEditable(true);

        TableColumn col1 = new TableColumn("Category Name");
        TableColumn col2 = new TableColumn("Days");

        col1.setMinWidth(250);
        col2.setMinWidth(150);

        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("days"));

        courseTypeTable.getColumns().addAll(col1, col2);
    }

    private void setCcTable() {
        ccDurationTable.setItems(ccData);
        ccDurationTable.setEditable(true);

        TableColumn col1 = new TableColumn("Course");
        TableColumn col2 = new TableColumn("Category");
        TableColumn col3 = new TableColumn("Total Fees (MK)");
        TableColumn col4 = new TableColumn("Date Added");

        col1.setMinWidth(100);
        col2.setMinWidth(150);
        col3.setMinWidth(100);
        col4.setMinWidth(150);

        col1.setCellValueFactory(new PropertyValueFactory<>("code"));
        col2.setCellValueFactory(new PropertyValueFactory<>("duration"));
        col3.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col4.setCellValueFactory(new PropertyValueFactory<>("date"));

        ccDurationTable.getColumns().addAll(col1, col2, col3, col4);

    }

    private boolean checkCompletedFields() {
        if (courseNameTxt.getText().isEmpty() | courseCodeTxt.getText().isEmpty() | govtFeeTxt.getText().isEmpty()) {
            makeAlert("warning", "Please Complete the form");
            return false;
        }
        return true;
    }

    private boolean checkIfCourseExist() {

        Course course = new CourseQueries().getCourse(courseCodeTxt.getText());
        return course == null;
    }

    private void clearTextFieldsFor(String tag) {
        switch (tag) {
            case "course":
                courseCodeTxt.setText("");
                courseNameTxt.setText("");
                govtFeeTxt.setText("");
                break;

            case "courseType":
                durationNameTxt.setText("");
                daysTxt.setText("");
                break;

            case "courseInfo":
                amountTxt.setText("");
                courseCombo.getSelectionModel().select(null);
                durationCombo.getSelectionModel().select(null);
                break;
        }
    }

    private void setCourseComboBoxData() {

        coursesForCombo.clear();
        for (int i = 0; i < courseData.size(); i++) {
            coursesForCombo.add(
                    courseData.get(i).getId() + "~"
                    + courseData.get(i).getCode() + "~"
                    + courseData.get(i).getName()
            );
        }

        courseCombo.setItems(coursesForCombo);

    }

    public void setLabels() {
        courseLbl.setText("Courses -> " + new CourseQueries().countCourses());
        courseTypeLbl.setText("Categories -> " + new CourseQueries().countCourseTypes());
        courseInfoLbl.setText("Course and their Categories -> " + new CourseQueries().countCourseInfo());
    }

    private void autoFillCourseTxtFields() {
        Course course = courseTable.getSelectionModel().getSelectedItem();

        if (course != null) {

            Course courseFrmDB = new CourseQueries().getCourse(course.getCode());

            courseCodeTxt.setText(courseFrmDB.getCode());
            courseNameTxt.setText(courseFrmDB.getName());
            govtFeeTxt.setText(courseFrmDB.getGovernmentFee());

        } else {
            makeAlert("warning", "Course not Selected");
        }

    }

    private void loadCcTableData() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                Platform.runLater(() -> {
                    ccData.clear();

                    ccData.addAll(new CourseQueries().getAllCourseDurations());
                });

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

    }

    private void loadCourseTypeTableData() {

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(() -> {
                    courseTypeData.clear();

                    courseTypeData.addAll(new CourseQueries().getAllCourseTypes());

                    setCourseTypeComboBoxData();
                });

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

    }

    public void autoFillCourseTypeTxtFiels() {
        CourseType courseType = courseTypeTable.getSelectionModel().getSelectedItem();

        if (courseType != null) {

            CourseType ctype = new CourseQueries().getCourseType(courseType.getName());
            durationNameTxt.setText(ctype.getName());
            daysTxt.setText(ctype.getDays());

        } else {
            makeAlert("warning", "Course Type not Selected");
        }

    }

    private boolean validateDuration(CourseType cType) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (!cType.getName().isEmpty() & !cType.getDays().isEmpty()) {
            if (pattern.matcher(cType.getDays()).matches()) {
                return true;
            } else {
                makeAlert("warning", "Days should be numeric");
            }
        } else {
            makeAlert("warning", "Please complete the fields");
        }
        return false;
    }

    private void setCourseTypeComboBoxData() {

        courseTypesForCombo.clear();
        for (int i = 0; i < courseTypeData.size(); i++) {
            courseTypesForCombo.add(courseTypeData.get(i).getId() + "~" + courseTypeData.get(i).getName());
        }

        durationCombo.setItems(courseTypesForCombo);

    }

    private boolean checkIfCourseTypeExist() {

        CourseType ctype = new CourseQueries().getCourseType(durationNameTxt.getText());

        return ctype == null;
    }

    public void initializeCourseAndDurationVars() {

        String selectedCourse = courseCombo.getSelectionModel().getSelectedItem();
        String selectedDuration = durationCombo.getSelectionModel().getSelectedItem();

        if (selectedCourse != null & selectedDuration != null) {
            courseArray = selectedCourse.split("~");
            durationArray = selectedDuration.split("~");

            courseID = courseArray[0];
            durationID = durationArray[0];
        } else {
            courseID = null;
            durationID = null;
        }

    }

    private boolean checkIfCourseInfoExist() {
        initializeCourseAndDurationVars();

        CourseDuration cd = new CourseQueries().getCourseInfo(courseID, durationID);
        return cd == null;
    }

    private boolean validateCourseInfo() {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (courseID != null & durationID != null & !amountTxt.getText().isEmpty()) {
            if (pattern.matcher(amountTxt.getText()).matches()) {
                if (Double.parseDouble(amountTxt.getText()) > 0) {
                    return true;
                } else {
                    makeAlert("warning", "Amount should not be less than zero");
                }
            } else {
                makeAlert("warning", "Amount should be numeric");
            }
        } else {
            makeAlert("warning", "Please complete the fields");
        }

        return false;
    }

}
