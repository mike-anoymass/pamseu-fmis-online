package financial_report;

import receipts.Receipts;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import commision.Commission;
import commision.CommissionQueries;
import data.LoadData;
import login.LoginDocumentController;
import payments.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.swing.filechooser.FileSystemView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import static settings.AlertClass.makeAlert;
import static drivingschoolfmis.DrivingSchoolFMIS.schoolName;
import vehicles.Allocation;

public class FinancialReportController implements Initializable {

    @FXML
    private Label rcLbl;

    @FXML
    private Label pyLbl;

    @FXML
    private Label profitLbl;

    @FXML
    private Label pLabel;

    @FXML
    private AnchorPane anchor;

    @FXML
    private ComboBox<String> yearCombo;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private Label receiptLbl;

    @FXML
    private Label paymentsLbl;

    @FXML
    private TableView<Receipts> receiptsTable;

    @FXML
    private TableView<Payment> paymentsTable;

    @FXML
    private ComboBox<String> dayCombo;
    @FXML
    private Label commisionLbl;
    @FXML
    private TableView<Commission> commisionTable;

    private ObservableList<String> years = FXCollections.observableArrayList();

    private ObservableList<String> months = FXCollections.observableArrayList();

    private ObservableList<Receipts> receiptData = FXCollections.observableArrayList();

    private ObservableList<Receipts> receiptForYear = FXCollections.observableArrayList();

    private ObservableList<Payment> payment = FXCollections.observableArrayList();

    private ObservableList<Payment> paymentsForYear = FXCollections.observableArrayList();

    private ObservableList<Commission> commisions = FXCollections.observableArrayList();

    private ObservableList<Commission> commisionsForYear = FXCollections.observableArrayList();

    private String[] monthArray;

    String label;

    private ResultSet rs;

    private Double totalPayments = 0.0;
    private Double totalReceipts = 0.0;
    private Double totalCommisions = 0.0;
    private Double pl = 0.0;
    @FXML
    private Label commLbl;

    @FXML
    void monthAction(ActionEvent event) {

    }

    @FXML
    void printAction(ActionEvent event) {
        ObservableList<Receipts> rec = FXCollections.observableArrayList();
        ObservableList<Payment> pay = FXCollections.observableArrayList();
        ObservableList<Commission> comm = FXCollections.observableArrayList();
        rec = receiptsTable.getItems();
        pay = paymentsTable.getItems();
        comm = commisionTable.getItems();

        if (rec.size() > 0 || pay.size() > 0) {
            try {
                Document doc = new Document();
                String fileName = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
                        + "\\" + schoolName
                        + " FMIS\\financial Report" + System.currentTimeMillis() + ".pdf";

                PdfWriter.getInstance(doc, new FileOutputStream(fileName));
                doc.open();
                Image image = Image.getInstance("img/driving_32.png");
                image.scaleToFit(120, 90);
                doc.add(image);
                doc.add(new Paragraph(schoolName + " Driving School - Financial Report for " + label,
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.BOLD, BaseColor.DARK_GRAY))
                );
                doc.add(new Paragraph("Printed by "
                        + LoginDocumentController.firstname + " " + LoginDocumentController.lastname
                        + " on " + new Date().toString()
                ));
                doc.add(Chunk.NEWLINE);
                doc.add(new LineSeparator());
                doc.add(Chunk.NEWLINE);

                PdfPTable table0 = new PdfPTable(2);
                PdfPCell titleCell0 = new PdfPCell(new Paragraph("Report Summary"));
                titleCell0.setColspan(4);
                titleCell0.setBorder(Rectangle.NO_BORDER);
                titleCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell0.setBackgroundColor(BaseColor.LIGHT_GRAY);

                table0.addCell(titleCell0);
                table0.addCell("Total Receipts:");
                table0.addCell(rcLbl.getText());
                table0.addCell("Total Payments:");
                table0.addCell(String.valueOf(totalPayments + totalCommisions));
                table0.addCell(profitLbl.getText());
                table0.addCell(pLabel.getText());

                PdfPTable table1 = new PdfPTable(2);
                PdfPCell titleCell1 = new PdfPCell(new Paragraph("Report Details"));
                titleCell1.setColspan(4);
                titleCell1.setBorder(Rectangle.NO_BORDER);
                titleCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);

                PdfPTable tabler = new PdfPTable(3);
                PdfPCell titleCellr = new PdfPCell(new Paragraph(receiptLbl.getText()));
                titleCellr.setColspan(6);
                titleCellr.setBorder(Rectangle.NO_BORDER);
                titleCellr.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCellr.setBackgroundColor(BaseColor.GREEN);

                tabler.addCell(titleCellr);
                tabler.addCell("Received From");
                tabler.addCell("Date");
                tabler.addCell("Amount (MK)");

                for (Receipts r : rec) {
                    tabler.addCell(r.getFullname());
                    tabler.addCell(r.getDateOfReceipt());
                    tabler.addCell(r.getAmount());
                }

                PdfPTable tablep = new PdfPTable(3);
                PdfPCell titleCellp = new PdfPCell(new Paragraph(paymentsLbl.getText()));
                titleCellp.setColspan(6);
                titleCellp.setBorder(Rectangle.NO_BORDER);
                titleCellp.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCellp.setBackgroundColor(BaseColor.RED);

                tablep.addCell(titleCellp);
                tablep.addCell("Expense");
                tablep.addCell("Date");
                tablep.addCell("Amount (MK)");

                for (Payment p : pay) {
                    tablep.addCell(p.getExpense());
                    tablep.addCell(p.getDateOfPayment());
                    tablep.addCell(p.getAmount());
                }

                for (Commission c : comm) {
                    tablep.addCell(c.getStaff());
                    tablep.addCell(c.getDatePaid());
                    tablep.addCell(String.valueOf(c.getCommision()));
                }

                table1.addCell(titleCell1);
                table1.addCell(tabler);
                table1.addCell(tablep);

                doc.add(table0);
                doc.add(Chunk.NEWLINE);
                doc.add(table1);
                doc.add(Chunk.NEWLINE);

                doc.add(new Paragraph("Pamseu FMIS @ " + LocalDate.now().getYear(),
                        FontFactory.getFont(FontFactory.TIMES_ITALIC, 12, Font.NORMAL, BaseColor.DARK_GRAY))
                );

                doc.add(new Paragraph("*Anoymass Programs",
                        FontFactory.getFont(FontFactory.TIMES_ITALIC, 9, Font.NORMAL, BaseColor.DARK_GRAY))
                );

                doc.close();

                makeAlert("information", "Report Saved Successfully !\n\n"
                        + "Location of the report: *" + fileName + "*");
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            makeAlert("warning", "Nothing to print \nLoad data first from the top");
        }
    }

    @FXML
    void yearCombo(ActionEvent event) {

    }

    @FXML
    void refreshAction(ActionEvent event) {
        yearCombo.getSelectionModel().select(null);
        monthCombo.getSelectionModel().select(null);
        dayCombo.getSelectionModel().select(null);
    }

    @FXML
    void generateAction(ActionEvent event) {
        String selectedYear = yearCombo.getSelectionModel().getSelectedItem();
        String selectedMonth = monthCombo.getSelectionModel().getSelectedItem();
        String day = dayCombo.getSelectionModel().getSelectedItem();

        if (selectedYear == null) {
            makeAlert("warning", "Please Select Year");
        } else {
            loadReceipts();
            loadPayments();
            loadCommisions();
            if (selectedMonth != null && day != null) {
                monthArray = selectedMonth.split("~");
                for (Receipts receipt : receiptData) {
                    if (receipt.getDateOfReceipt().startsWith(selectedYear)
                            & receipt.getDateOfReceipt().split("-")[1].equals(monthArray[0])
                            & receipt.getDateOfReceipt().split("-")[2].equals(day)) {

                        receiptForYear.add(receipt);
                    }
                }

                for (Payment payment : payment) {

                    if (payment.getDateOfPayment().startsWith(selectedYear)
                            & payment.getDateOfPayment().split("-")[1].equals(monthArray[0])
                            & payment.getDateOfPayment().split("-")[2].equals(day)) {

                        paymentsForYear.add(payment);
                    }
                }

                for (Commission commision : commisions) {

                    if (commision.getDatePaid().startsWith(selectedYear)
                            & commision.getDatePaid().split("-")[1].equals(monthArray[0])
                            & commision.getDatePaid().split("-")[2].split(" ")[0].equals(day)) {

                        commisionsForYear.add(commision);
                    }
                }

            } else if (selectedMonth != null) {
                monthArray = selectedMonth.split("~");
                for (Receipts receipt : receiptData) {

                    if (receipt.getDateOfReceipt().startsWith(selectedYear)
                            & receipt.getDateOfReceipt().split("-")[1].equals(monthArray[0])) {

                        receiptForYear.add(receipt);
                    }
                }

                for (Payment payment : payment) {

                    if (payment.getDateOfPayment().startsWith(selectedYear)
                            & payment.getDateOfPayment().split("-")[1].equals(monthArray[0])) {

                        paymentsForYear.add(payment);
                    }
                }

                for (Commission commision : commisions) {

                    if (commision.getDatePaid().startsWith(selectedYear)
                            & commision.getDatePaid().split("-")[1].equals(monthArray[0])) {

                        commisionsForYear.add(commision);
                    }
                }
            } else {
                for (Receipts receipt : receiptData) {
                    if (receipt.getDate().startsWith(selectedYear)) {
                        receiptForYear.add(receipt);
                    }
                }

                for (Payment payment : payment) {
                    if (payment.getDate().startsWith(selectedYear)) {
                        paymentsForYear.add(payment);
                    }
                }

                for (Commission commision : commisions) {

                    if (commision.getDatePaid().startsWith(selectedYear)) {
                        commisionsForYear.add(commision);
                    }
                }

            }

            if (selectedMonth != null && day != null) {
                receiptLbl.setText("Receipts in " + selectedYear + " " + monthArray[1] + " " + day);
                label = selectedYear + " " + monthArray[1] + " " + day;
                paymentsLbl.setText("Payments in " + selectedYear + " " + monthArray[1] + " " + day);
                commisionLbl.setText("Commisions in " + selectedYear + " " + monthArray[1] + " " + day);
            } else if (selectedMonth == null) {
                receiptLbl.setText("Receipts in " + selectedYear);
                label = selectedYear;
                paymentsLbl.setText("Payments in " + selectedYear);
                commisionLbl.setText("Commisions in " + selectedYear);
            } else {
                receiptLbl.setText("Receipts in " + selectedYear + " " + monthArray[1]);
                label = selectedYear + " " + monthArray[1];
                paymentsLbl.setText("Payments in " + selectedYear + " " + monthArray[1]);
                commisionLbl.setText("Commisions in " + selectedYear + " " + monthArray[1]);
            }

            pyLbl.setText("");
            rcLbl.setText("");
            pLabel.setText("");

            int count = 0;

            if (receiptForYear.size() > 0) {
                loadReceiptsDataInTable();
                setReceiptLabel();
                count++;
            } else {
                setReceiptLabel();
                makeAlert("warning", "There are no Receipts for this Period");

            }

            if (commisionsForYear.size() > 0) {
                loadCommisionDataInTable();
                setCommisionLabel();
                count++;
            } else {
                setCommisionLabel();
                makeAlert("warning", "There are no paid Commisions for this Period");

            }

            if (paymentsForYear.size() > 0) {
                loadPaymentsDataInTable();
                setPaymentLabels();
                count++;
            } else {
                setPaymentLabels();
                makeAlert("warning", "There are no Payments for this Period");
            }

            if (count > 0) {
                calculateProfitOrLoss();
            }

        }

    }

    private void calculateProfitOrLoss() {
        pl = 0.0;
        pl = totalReceipts - (totalPayments + totalCommisions);

        if (pl > 0) {
            profitLbl.setText("Profit: ");
        } else {
            profitLbl.setText("Loss: ");
        }

        pLabel.setText(pl + " MK");
    }

    private void setPaymentLabels() {
        totalPayments = 0.0;
        for (int i = 0; i < paymentsForYear.size(); i++) {
            totalPayments += Double.parseDouble(paymentsForYear.get(i).getAmount());
        }

        pyLbl.setText(totalPayments + " MK");
    }

    private void setReceiptLabel() {
        totalReceipts = 0.0;
        for (int i = 0; i < receiptForYear.size(); i++) {
            totalReceipts += Double.parseDouble(receiptForYear.get(i).getAmount());
        }

        rcLbl.setText(totalReceipts + " MK");
    }

    private void loadPaymentsDataInTable() {
        paymentsTable.setItems(paymentsForYear);
    }

    private void setPaymentsTable() {
        paymentsTable.setEditable(true);

        TableColumn col1 = new TableColumn("Payment ID");
        TableColumn col2 = new TableColumn("Payment For");
        TableColumn col3 = new TableColumn("Cost/Amount (MK)");
        TableColumn col6 = new TableColumn("Date of Payment");

        col1.setMinWidth(70);
        col2.setMinWidth(130);
        col3.setMinWidth(130);
        col6.setMinWidth(130);

        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("expense"));
        col3.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col6.setCellValueFactory(new PropertyValueFactory<>("dateOfPayment"));

        paymentsTable.getColumns().addAll(col1, col2, col3, col6);
    }

    private void loadReceiptsDataInTable() {
        receiptsTable.setItems(receiptForYear);
    }

    private void setReceiptsTable() {
        receiptsTable.setEditable(true);

        TableColumn col1 = new TableColumn("Receipt No.");
        TableColumn col2 = new TableColumn("Student Name");
        TableColumn col22 = new TableColumn("Amount (MK)");
        TableColumn col3 = new TableColumn("Date");

        col1.setMinWidth(80);
        col2.setMinWidth(140);
        col22.setMinWidth(100);
        col3.setMinWidth(130);

        receiptsTable.getColumns().addAll(col1, col2, col22, col3);

        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col22.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col2.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("dateOfReceipt"));

    }

    private void loadPayments() {
        payment.clear();
        paymentsForYear.clear();

        payment = new LoadData().loadPayments();

    }

    private void loadReceipts() {
        receiptData.clear();
        receiptForYear.clear();

        receiptData = new LoadData().loadReceipts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchor.getStylesheets().add(getClass().getResource("/css/students.css").toExternalForm());
        setYearCombo();
        setMonthCombo();
        setDayCombo();
        setPaymentsTable();
        setReceiptsTable();
        setCommisionTable();
    }

    private void setYearCombo() {
        years.addAll("2021", "2022", "2023", "2024", "2025", "2026");

        yearCombo.setItems(years);
    }

    private void setMonthCombo() {
        months.addAll("01~January", "02~February", "03~March", "04~April", "05~May", "06~June",
                "07~July", "08~August", "09~September", "10~October", "11~November", "12~December");

        monthCombo.setItems(months);
    }

    private void setDayCombo() {
        ObservableList<String> days = FXCollections.observableArrayList();
        days.addAll("01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
                "27", "28", "29", "30", "31");

        dayCombo.setItems(days);
    }

    private void setCommisionTable() {
        commisionTable.setEditable(true);

        TableColumn col1 = new TableColumn("Commision For");
        TableColumn col2 = new TableColumn("Amount (MK)");
        TableColumn col22 = new TableColumn("Date of payment");

        col1.setMinWidth(100);
        col2.setMinWidth(100);
        col22.setMinWidth(100);

        commisionTable.getColumns().addAll(col1, col2, col22);

        col1.setCellValueFactory(new PropertyValueFactory<>("staff"));
        col2.setCellValueFactory(new PropertyValueFactory<>("commision"));
        col22.setCellValueFactory(new PropertyValueFactory<>("datePaid"));
    }

    private void loadCommisions() {
        commisions.clear();
        commisionsForYear.clear();

        commisions.addAll(new CommissionQueries().getPaidCommisions());
    }

    private void loadCommisionDataInTable() {
        commisionTable.setItems(commisionsForYear);
    }

    private void setCommisionLabel() {
        totalCommisions = 0.0;
        for (int i = 0; i < commisionsForYear.size(); i++) {
            totalCommisions += commisionsForYear.get(i).getCommision();
        }

        commLbl.setText(totalCommisions + " MK");
    }
}
