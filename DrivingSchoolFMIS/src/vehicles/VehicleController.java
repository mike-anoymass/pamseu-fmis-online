/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles;

import data.CheckExistence;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static settings.AlertClass.makeAlert;
import static settings.AlertClass.makePromptAlert;
import static settings.NotificationClass.showNotification;
import tests.Test;
import validation.ValidateFieldsClass;

/**
 * FXML Controller class
 *
 * @author ANOYMASS
 */
public class VehicleController implements Initializable {
    
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField regNoTxt;
    
    @FXML
    private Label countLbl;
    @FXML
    private TextField filtersTxt;
    @FXML
    private TableView<Vehicle> vehicleTable;
    
    private ObservableList<Vehicle> data = FXCollections.observableArrayList();
    private FilteredList<Vehicle> filteredData;
    @FXML
    private DatePicker cof_exp;
    @FXML
    private DatePicker insurance_exp;

    /**
     * Initialises the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        anchor.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());
        
        loadData();
        setTable();
        
        filteredData = new FilteredList<>(data, e -> true);
    }
    
    private void setTable() {
        vehicleTable.setItems(data);
        setCount();
        
        TableColumn col1 = new TableColumn("ID");
        TableColumn col2 = new TableColumn("Registration Number");
        TableColumn col3 = new TableColumn("COF Expiry Date");
        TableColumn col33 = new TableColumn("Insurance Expiry Date");
        TableColumn col4 = new TableColumn("Date Added");
        
        col1.setMinWidth(150);
        col2.setMinWidth(200);
        col3.setMinWidth(300);
        col33.setMinWidth(300);
        col4.setMinWidth(300);
        
        vehicleTable.getColumns().addAll(col1, col2, col3, col33, col4);
        vehicleTable.setEditable(true);
        
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        col3.setCellValueFactory(new PropertyValueFactory<>("cofDate"));
        col33.setCellValueFactory(new PropertyValueFactory<>("insuranceDate"));
        col4.setCellValueFactory(new PropertyValueFactory<>("date"));
        
    }
    
    private void loadData() {
        data.clear();
        
        data.addAll(new VehicleQueries().getVehicles());
    }
    
    private void setCount() {
        countLbl.setText("#" + vehicleTable.getItems().size());
    }
    
    private void clearTextFields() {
        
        regNoTxt.setText("");
        cof_exp.setValue(null);
        insurance_exp.setValue(null);
        
    }
    
    private void autoFillFields() {
        Vehicle vehicl = vehicleTable.getSelectionModel().getSelectedItem();
        
        if (vehicl != null) {
            regNoTxt.setText(vehicl.getRegNo());
            cof_exp.setValue(LocalDate.parse(vehicl.getCofDate()));
            insurance_exp.setValue(LocalDate.parse(vehicl.getInsuranceDate()));
        }
    }
    
    @FXML
    private void saveAction(ActionEvent event) {
        
        try {
            Vehicle vehicle = new Vehicle(regNoTxt.getText(), cof_exp.getValue().toString(), insurance_exp.getValue().toString());
            
            if (new ValidateFieldsClass().validateVehicle(vehicle)) {
                if (new CheckExistence().checkVehicleExist(vehicle.getRegNo())) {
                    boolean added = VehicleQueries.addVehicle(vehicle);
                    
                    if (!added) {
                        showNotification(vehicle.getRegNo() + " Has been added Succesfully");
                        loadData();
                        clearTextFields();
                        setCount();
                    }
                } else {
                    makeAlert("warning", "The Vehicle you entered Exist");
                }
            }
        } catch (NullPointerException ex) {
            makeAlert("error", "Complete filling all the fields");
        }
        
    }
    
    @FXML
    private void updateAction(ActionEvent event) {
        
        Vehicle selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
        
        if (selectedVehicle != null) {
            Vehicle vehicle = new Vehicle(regNoTxt.getText(), cof_exp.getValue().toString(), insurance_exp.getValue().toString());
            
            if (new ValidateFieldsClass().validateVehicle(vehicle)) {
                
                boolean edited = VehicleQueries.editVehicle(vehicle, selectedVehicle.getId());
                
                if (!edited) {
                    showNotification(selectedVehicle.getRegNo() + " Has been edited Succesfully");
                    loadData();
                    clearTextFields();
                    setCount();
                }
                
            }
        } else {
            makeAlert("warning", "Please select from the table the vehicle you want to edit");
        }
        
    }
    
    @FXML
    private void filterOnKeyReleased(KeyEvent event) {
        filtersTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Vehicle>) v -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (v.getRegNo().contains(newValue)) {
                    return true;
                } else if (v.getInsuranceDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (v.getCofDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (v.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }                
                return false;
            });
        });
        SortedList<Vehicle> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(vehicleTable.comparatorProperty());
        vehicleTable.setItems(sortedData);
        
        setCount();
    }
    
    @FXML
    private void refreshAction(ActionEvent event) {
    }
    
    @FXML
    private void deleteAction(ActionEvent event) {
        Vehicle vehicle = vehicleTable.getSelectionModel().getSelectedItem();
        boolean action = makePromptAlert("Please Confirm Deletion", "Are you sure; You wanna delete this Vehicle ?");
        if (vehicle != null) {
            
            int response = new VehicleQueries().deleteVehicle(vehicle.getId(), action);
            
            if (response != 404) {
                loadData();
                setCount();
                clearTextFields();
                showNotification("Vehicle Deleted Successfully");
            }
            
        } else {
            makeAlert("warning", "Please Select a user from the table");
        }
    }
    
    @FXML
    private void tableOnMouseClickedAction(MouseEvent event) {
        autoFillFields();
    }
    
    @FXML
    private void tableOnKeyReleasedAction(KeyEvent event) {
        if (event.getCode() == KeyCode.UP | event.getCode() == KeyCode.DOWN) {
            autoFillFields();
        }
    }
    
}
