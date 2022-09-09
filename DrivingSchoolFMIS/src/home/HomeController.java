package home;

import com.jfoenix.controls.JFXProgressBar;
import data.LoadData;
import payments.Payment;
import courses.CourseQueries;
import courses.Course;
import receipts.Receipts;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.text.Text;
import students.StudentCourse;
import students.StudentQueries;

public class HomeController implements Initializable {

    @FXML
    AnchorPane anchor;

    @FXML
    private ImageView pieChartImg;

    @FXML
    private VBox pieChartBox;

    @FXML
    private HBox gaugeHBox;

    @FXML
    private ImageView barchartImg;

    @FXML
    private HBox studentBox;

    @FXML
    private HBox coursesBox;

    @FXML
    private HBox courseTypeBox;

    @FXML
    private Text progressText;

    @FXML
    private JFXProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchor.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                updateMessage("Loading modules...");
                loadGauges();
                createBarChart();
                loadPieChart();
                updateMessage("");
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            progressBar.setVisible(false);
        });

        progressBar.progressProperty().bind(task.progressProperty());
        progressText.textProperty().bind(task.messageProperty());

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.run();

    }

    private void loadPieChart() {
        Task<ObservableList<Double>> task = new Task<ObservableList<Double>>() {
            ObservableList<Double> ob = FXCollections.observableArrayList();

            @Override
            protected ObservableList<Double> call() throws Exception {
                updateMessage("loading Payments...");
                ob.add(getPayments());

                updateMessage("loading Receipts...");
                ob.add(getReceipts());
                updateMessage("Finished Loading");

                return ob;
            }
        };

        task.setOnSucceeded(e -> {
            ObservableList<Double> ob = task.getValue();
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            pieDataset.setValue("Payments", ob.get(0));
            pieDataset.setValue("Receipts", ob.get(1));

            JFreeChart pieChart = ChartFactory.createPieChart3D("",
                    pieDataset, true, true, false);

            try {
                ChartUtilities.saveChartAsJPEG(new File(
                        FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\pieChart.jpg"),
                        pieChart, 700, 350
                );
                pieChartImg.setImage(new Image("file:"
                        + FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\pieChart.jpg")
                );

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.run();

    }

    private double getReceipts() {
        double totalReceipts = 0.0;
        LocalDate curr = LocalDate.now();
        ObservableList<Receipts> receiptData = new LoadData().loadReceipts();
        ObservableList<Receipts> receiptsForThisMonth = FXCollections.observableArrayList();

        for (Receipts receipt : receiptData) {

            if (receipt.getDate().startsWith(curr.toString().split("-")[0])
                    & receipt.getDate().split("-")[1].equals(curr.toString().split("-")[1])) {

                receiptsForThisMonth.add(receipt);
            }
        }

        if (receiptsForThisMonth.size() > 0) {
            totalReceipts = 0.0;
            for (int i = 0; i < receiptsForThisMonth.size(); i++) {
                totalReceipts += Double.parseDouble(receiptsForThisMonth.get(i).getAmount());
            }

            return totalReceipts;
        }

        return 0.0;
    }

    private double getPayments() {
        double totalPayments = 0.0;
        LocalDate curr = LocalDate.now();
        ObservableList<Payment> paymentData = new LoadData().loadPayments();
        ObservableList<Payment> paymentsForThisMonth = FXCollections.observableArrayList();

        for (Payment payment : paymentData) {

            if (payment.getDate().startsWith(curr.toString().split("-")[0])
                    & payment.getDate().split("-")[1].equals(curr.toString().split("-")[1])) {

                paymentsForThisMonth.add(payment);
            }
        }

        if (paymentsForThisMonth.size() > 0) {
            for (int i = 0; i < paymentsForThisMonth.size(); i++) {
                totalPayments += Double.parseDouble(paymentsForThisMonth.get(i).getAmount());
            }

            return totalPayments;
        }

        return 0.0;
    }

    private void createBarChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Task<ObservableList<StudentCourse>> task = new Task<ObservableList<StudentCourse>>() {
            @Override
            protected ObservableList<StudentCourse> call() throws Exception {
                updateMessage("Loading Students and Courses...");
                ObservableList<StudentCourse> sc = loadCourseStudentsData();
                return sc;
            }
        };

        task.setOnSucceeded(e -> {
            ObservableList<StudentCourse> sc = task.getValue();
            if (sc.size() > 0) {
                for (StudentCourse s : sc) {
                    dataset.setValue(s.getNumberOfStudents(), "Students", s.getCourse());
                }
            }

            JFreeChart chart = ChartFactory.createBarChart3D("",
                    "Course", "Students", dataset, PlotOrientation.VERTICAL,
                    true, true, false);
            try {
                ChartUtilities.saveChartAsJPEG(new File(
                        FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\barchart.jpg"),
                        chart, 900, 300
                );

                barchartImg.setImage(new Image("file:"
                        + FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\barchart.jpg")
                );

            } catch (IOException ex) {
                System.err.println("Problem occurred creating chart." + ex);

            }
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.run();

    }

    private ObservableList<StudentCourse> loadCourseStudentsData() {
        ObservableList<StudentCourse> sc = FXCollections.observableArrayList();
        ObservableList<Course> courses = new LoadData().loadCourseTableData();
        int studentCount = 0;

        if (courses.size() > 0) {
            for (Course c : courses) {
                studentCount = StudentQueries.countStudentsInThisCourse(c.getCode());
                sc.add(new StudentCourse(c.getCode(), studentCount));

                studentCount = 0;
            }
        }

        return sc;
    }

    private void loadGauges() {
        Task<ObservableList<Integer>> task = new Task<ObservableList<Integer>>() {
            ObservableList<Integer> ob = FXCollections.observableArrayList();

            @Override
            protected ObservableList<Integer> call() throws Exception {
                updateMessage("loading Students...");
                ob.add(new StudentQueries().countStudents());

                updateMessage("loading Courses...");
                ob.add(new CourseQueries().countCourses());

                updateMessage("loading Categoris...");
                ob.add(new CourseQueries().countCourseTypes());

                return ob;
            }
        };

        task.setOnSucceeded(e -> {
            ObservableList<Integer> ob = task.getValue();
            studentBox.getChildren().add(makeGauge("Students", ob.get(0)));
            coursesBox.getChildren().add(makeGauge("Courses", ob.get(1)));
            courseTypeBox.getChildren().add(makeGauge("Categories", ob.get(2)));
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.run();
    }

    private Gauge makeGauge(String title, int value) {
        Gauge gauge = new Gauge();
        gauge.setSkinType(Gauge.SkinType.SIMPLE);
        gauge.setTitle(title);  //title
        gauge.setTitleColor(Color.WHITE);
        gauge.setUnit("");  //unit
        gauge.setUnitColor(Color.GREY);
        gauge.setDecimals(0);
        gauge.setValue(0); //deafult position of needle on gauage
        gauge.setAnimated(true);
        gauge.setAnimationDuration(5000);
        gauge.setValueColor(Color.WHITE);
        gauge.setSubTitleColor(Color.WHITE);
        gauge.setBarColor(Color.BLACK);
        gauge.setPrefSize(200, 150);
        gauge.setNeedleColor(Color.GREY);
        gauge.setNeedleBorderColor(Color.WHITE);
        gauge.setNeedleShape(Gauge.NeedleShape.ROUND);
        gauge.setThresholdColor(Color.RED);
        //gauge.setThreshold(thr);
        gauge.setThresholdVisible(true);
        gauge.setTickLabelColor(Color.rgb(151, 151, 151));
        gauge.setTickMarkColor(Color.WHITE);
        gauge.setTickLabelOrientation(TickLabelOrientation.TANGENT);
        gauge.setBorderPaint(Paint.valueOf("grey"));
        gauge.setForegroundPaint(Paint.valueOf("white"));
        gauge.setForegroundBaseColor(Color.DARKGRAY);
        gauge.setBarBackgroundColor(Color.WHITE);
        gauge.setBackgroundPaint(Paint.valueOf("white"));
        gauge.setSmoothing(true);
        System.out.println(value);
        gauge.setLedColor(Color.WHITE);
        gauge.setValue(new Double(value));
        switch (title) {
            case "Students":
                gauge.setMaxValue(1000);
                break;
            case "Courses":
            case "Categories":
                gauge.setMaxValue(10);
                break;
        }

        gauge.setValueColor(Color.WHITE);

        return gauge;
    }
}
