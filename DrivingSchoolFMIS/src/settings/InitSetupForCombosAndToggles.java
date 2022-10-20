package settings;

import courses.Course;
import fees.CategoryFees;
import courses.CourseQueries;
import courses.CourseType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.sql.ResultSet;

public class InitSetupForCombosAndToggles {

    ResultSet rs;

    public void loadIntoCourses(ComboBox<String> courseCombo) {
        ObservableList<String> courseData = FXCollections.observableArrayList();
        courseData.clear();

        ObservableList<Course> courses = new CourseQueries().getAllCourses();
        courses.stream().forEach((course) -> {
            courseData.add(course.getId() + "~" + course.getCode());
        });

        courseCombo.setItems(courseData);
    }

    public void setGenderToggleGroup(RadioButton maleRadioBtn, RadioButton femaleRadioBtn) {
        ToggleGroup gender = new ToggleGroup();

        maleRadioBtn.setToggleGroup(gender);
        femaleRadioBtn.setToggleGroup(gender);
    }

    public void loadCategoriesForThisCourse(String courseID, ComboBox<String> categoryCombo) {
        if (courseID != null && courseID.length() > 0) {
            String[] courseArray;
            ObservableList<String> categoryData = FXCollections.observableArrayList();

            courseArray = courseID.split("~");

            courseID = courseArray[0];

            ObservableList<CourseType> categories = new CourseQueries().getDurationsForThisCourse(courseID);
            categories.stream().forEach((category) -> {
                categoryData.add(category.getName());
            });

            if (categoryData.size() > 0) {
                categoryCombo.setItems(categoryData);
            } else {
                categoryCombo.getItems().clear();
            }
        }
    }

    public void categoryComboAction(ComboBox<String> courseCombo, ComboBox<String> categoryCombo, Label feesLbl) {
        String[] courseArray;
        String category, courseCode;
        Double fees;
        courseCode = "";

        if (courseCombo.getSelectionModel().getSelectedItem() != null) {
            courseArray = courseCombo.getSelectionModel().getSelectedItem().split("~");
            courseCode = courseArray[1];
        }

        category = categoryCombo.getSelectionModel().getSelectedItem();

        if (courseCode.length() > 0 && category != null) {
            fees = new CategoryFees().getCategoryFees(courseCode, category);
            feesLbl.setVisible(true);
            feesLbl.setText("MK" + fees);
        } else {
            feesLbl.setText("");
        }
    }

    public void loadIntoDepartments(ComboBox<String> departmentCombo) {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Driver");
        options.add("Instructor");
        options.add("Accountant");
        options.add("Admin");
        options.add("Receptionist");
        options.add("Guard");
        options.add("Cleaner");
        departmentCombo.setItems(options);
    }

    public void loadIntoStatus(ComboBox<String> statusCombo) {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Full Time");
        options.add("Part Time");
        statusCombo.setItems(options);
    }

    public void loadPaymentsMode(ComboBox<String> pmCombo) {
        ObservableList<String> pm = FXCollections.observableArrayList();

        pm.add("Cash");
        pm.add("Cheque");
        pm.add("From other Banks");
        pm.add("Mo626");

        pmCombo.setItems(pm);
    }
}
