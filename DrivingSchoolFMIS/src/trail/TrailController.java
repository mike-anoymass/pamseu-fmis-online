/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trail;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ANOYMASS
 */
public class TrailController implements Initializable {
    
    @FXML
    private AnchorPane anchor;

    @FXML
    private Label receiptLbl;
    @FXML
    private TextField filterTxt;
    @FXML
    private TableView<Trail> table;
    
    private ObservableList<Trail> data = FXCollections.observableArrayList();
    
    private FilteredList<Trail> filteredData = new FilteredList<>(data, e -> true);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        anchor.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());
        
        loadData();
        setTableView();
        
    }   
    
     public void setTableView() {
        table.setItems(data);

        TableColumn col1 = new TableColumn("Name of the user");
        TableColumn col2 = new TableColumn("Role");
        TableColumn col22 = new TableColumn("Phone Number");
        TableColumn col3 = new TableColumn("Login Date");
   
        col1.setMinWidth(250);
        col2.setMinWidth(200);
        col22.setMinWidth(200);
        col3.setMinWidth(250);
       

        table.getColumns().addAll(col1, col2, col22, col3);

        col1.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        col22.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col2.setCellValueFactory(new PropertyValueFactory<>("type"));
        col3.setCellValueFactory(new PropertyValueFactory<>("date"));
       
    }

    public void loadData() {
        data.clear();

        data.addAll(new TrailQueries().getAll());
    }

    @FXML
    private void filterDataOnkeyReleased(KeyEvent event) {
         filterTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Trail>) receipt -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (receipt.getFullname().contains(newValue)) {
                    return true;
                } else if (receipt.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (receipt.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (receipt.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } 
                return false;

            });
        });

        SortedList<Trail> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
       
    }

    @FXML
    private void viewPaymentHistory(ActionEvent event) {
    }
    
}
