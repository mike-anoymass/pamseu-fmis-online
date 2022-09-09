package commision;

import com.jfoenix.controls.JFXButton;
import drivingschoolfmis.DrivingSchoolFMIS;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.filechooser.FileSystemView;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static settings.AlertClass.makeAlert;
import static settings.AlertClass.makePromptAlert;
import static settings.NotificationClass.showNotification;
import students.Student;

public class CommisionController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private TableView<Commission> table;

    @FXML
    private ComboBox<String> filterCombo;

    @FXML
    private TextField filtersTxt;

    @FXML
    private Label countLbl;

    @FXML
    private JFXButton exportBtn;

    private FilteredList<Commission> filteredData;

    private ObservableList<Commission> data = FXCollections.observableArrayList();

    public void initialize() {
        anchor.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());

        loadCommisionData(new CommissionQueries().getCommisions());
        setTable();
        setFilterCombo();
        sumUpCommisions();

        filteredData = new FilteredList<>(data, e -> true);
    }

    public void setFilterCombo() {
        ObservableList<String> filters = FXCollections.observableArrayList();
        filters.addAll("Paid", "Not Paid", "All");
        filterCombo.setItems(filters);
    }

    private void setTable() {
        table.setItems(data);

        TableColumn col0 = new TableColumn("Tick");
        TableColumn col1 = new TableColumn("Commision For");
        TableColumn col2 = new TableColumn("Student");
        TableColumn col3 = new TableColumn("Obtained On");
        TableColumn col4 = new TableColumn("Status");
        TableColumn col5 = new TableColumn("Commision (MK)");
        TableColumn col6 = new TableColumn("Recorded By");
        //TableColumn col7 = new TableColumn("Password");
        TableColumn col8 = new TableColumn("Date Paid");

        col0.setMinWidth(70);
        col1.setMinWidth(150);
        col2.setMinWidth(150);
        col3.setMinWidth(150);
        col4.setMinWidth(100);
        col5.setMinWidth(100);
        col6.setMinWidth(150);
        //col7.setMinWidth(70);
        col8.setMinWidth(150);

        table.getColumns().addAll(col0, col1, col2, col3, col4, col5, col6, /*col7,*/ col8);
        table.setEditable(true);

        col0.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        col1.setCellValueFactory(new PropertyValueFactory<>("staff"));
        col2.setCellValueFactory(new PropertyValueFactory<>("student"));
        col3.setCellValueFactory(new PropertyValueFactory<>("dateObtained"));
        col4.setCellValueFactory(new PropertyValueFactory<>("status"));
        col5.setCellValueFactory(new PropertyValueFactory<>("commision"));
        col6.setCellValueFactory(new PropertyValueFactory<>("user"));
        //col7.setCellValueFactory(new PropertyValueFactory<>("password"));
        col8.setCellValueFactory(new PropertyValueFactory<>("datePaid"));

    }

    private void sumUpCommisions() {
        Double commisions = 0.0;
        for (Commission c : table.getItems()) {
            commisions += c.getCommision();
        }

        countLbl.setText("Total -> MK " + commisions);
    }

    @FXML
    void exportAction(ActionEvent event) {

        exportBtn.setText("Loading... ");

        ObservableList<Commission> comms = table.getItems();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        XSSFWorkbook wb = new XSSFWorkbook();
                        XSSFSheet sheet = wb.createSheet("Commisions");
                        XSSFRow header = sheet.createRow(0);
                        header.createCell(0).setCellValue("Commision For");
                        header.createCell(1).setCellValue("Student");
                        header.createCell(2).setCellValue("Obtained On");
                        header.createCell(3).setCellValue("Status");
                        header.createCell(4).setCellValue("Commision (MK)");
                        header.createCell(5).setCellValue("Recorded By");
                        header.createCell(6).setCellValue("Date Paid");

                        sheet.setColumnWidth(0, 256 * 15);
                        sheet.setColumnWidth(1, 256 * 15);
                        sheet.setColumnWidth(2, 256 * 15);
                        sheet.setColumnWidth(3, 256 * 25);
                        sheet.setColumnWidth(4, 256 * 15);
                        sheet.setColumnWidth(5, 256 * 15);
                        sheet.setColumnWidth(6, 256 * 25);

                        sheet.setZoom(150);

                        int index = 1;

                        try {

                            for (Commission comm : comms) {
                                XSSFRow row = sheet.createRow(index);
                                row.createCell(0).setCellValue(comm.getStaff());
                                row.createCell(1).setCellValue(comm.getStudent());
                                row.createCell(2).setCellValue(comm.getDateObtained());
                                row.createCell(3).setCellValue(comm.getStatus());
                                row.createCell(4).setCellValue(comm.getCommision());
                                row.createCell(5).setCellValue(comm.getUser());
                                row.createCell(6).setCellValue(comm.getDatePaid());

                                index++;
                            }

                            String fileName = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
                                    + "\\" + DrivingSchoolFMIS.schoolName + " FMIS\\"
                                    + "Commisions" + System.currentTimeMillis() + ".xlsx";

                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            wb.write(fileOut);
                            fileOut.close();

                            exportBtn.setText("Export List to Excel");

                            showNotification("Data Exported Successfully\nFile Location: " + fileName);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void filterByAction(ActionEvent event) {
        String option = filterCombo.getSelectionModel().getSelectedItem();

        if (option != null) {
            switch (option) {
                case "Paid":
                    loadCommisionData(new CommissionQueries().getPaidCommisions());
                    break;
                case "Not Paid":
                    loadCommisionData(new CommissionQueries().getUnpaidCommisions());
                    break;
                case "All":
                    loadCommisionData(new CommissionQueries().getCommisions());
                    break;
            }

            sumUpCommisions();
        }
    }

    @FXML
    void filterOnKeyReleased(KeyEvent event) {
        filtersTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Commission>) u -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (u.getStaff().contains(newValue)) {
                    return true;
                } else if (u.getStudent().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (u.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (u.getDateObtained().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(u.getCommision()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (u.getUser().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Commission> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        sumUpCommisions();
    }

    @FXML
    void payCommisions(ActionEvent event) {
        ObservableList<Commission> selected = FXCollections.observableArrayList();

        for (Commission data : data) {
            if (data.getCheckBox().isSelected()) {
                selected.add(data);
            }
        }

        if (selected.size() > 0) {
            boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure you want to pay ? \n" + selected.size()
                    + " Commision(s) ?");

            Task task = new Task() {

                @Override
                protected Object call() throws Exception {
                    Platform.runLater(() -> {
                        int deleted = CommissionQueries.payCommisions(selected, action);

                        if (deleted != 404) {
                            loadCommisionData(new CommissionQueries().getCommisions());

                            showNotification("Commisions Paid Successfully");
                        }
                    });

                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        } else {
            makeAlert("warning", "No Commisions Selected\nPlease tick the checkboxes");
        }
    }

    @FXML
    void unPayCommisions(ActionEvent event) {
        ObservableList<Commission> selected = FXCollections.observableArrayList();

        for (Commission data : data) {
            if (data.getCheckBox().isSelected()) {
                selected.add(data);
            }
        }

        if (selected.size() > 0) {
            boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure you want to pay ? \n" + selected.size()
                    + " Commision(s) ?");

            Task task = new Task() {

                @Override
                protected Object call() throws Exception {
                    Platform.runLater(() -> {
                        int deleted = CommissionQueries.unPayCommisions(selected, action);

                        if (deleted != 404) {
                            loadCommisionData(new CommissionQueries().getCommisions());

                            showNotification("Commisions marked as not paid");
                        }
                    });

                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        } else {
            makeAlert("warning", "No Commisions Selected\nPlease tick the checkboxes");
        }
    }

    @FXML
    void refreshAction(ActionEvent event) {

    }

    @FXML
    void tableOnKeyReleasedAction(KeyEvent event) {

    }

    @FXML
    void tableOnMouseClickedAction(MouseEvent event) {

    }

    private void loadCommisionData(ObservableList<Commission> comms) {
        data.clear();

        data.addAll(comms);
    }

}
