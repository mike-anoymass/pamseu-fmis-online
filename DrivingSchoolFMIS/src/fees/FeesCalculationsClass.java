package fees;

import courses.Course;
import courses.CourseDuration;
import discount.DiscountQueries;
import courses.CourseQueries;
import courses.CourseType;
import discount.Discount;
import receipts.ReceiptQueries;

import java.sql.ResultSet;
import javafx.collections.ObservableList;
import receipts.Receipts;
import students.Student;

public class FeesCalculationsClass {

    double totalPaid, balance;
    String courseID, durationID;
    ResultSet rsForCourse = null;
    Double totalFees;
    CourseQueries courseQuery = new CourseQueries();
    FeesResults results = new FeesResults();

    public FeesResults FeesCalculations(Student student) {
        totalPaid = balance = 0.0;

        Course course = courseQuery.getCourse(student.getCourse());

        courseID = course.getId();

        CourseType ctype = courseQuery.getCourseType(student.getCourseType());

        durationID = ctype.getId();

        CourseDuration cd = courseQuery.getCourseInfo(courseID, durationID);

        results.setCourseFee(cd.getAmount());

        if (student.isAnyDiscount()) {

            Discount discount = new DiscountQueries().getDiscount(student.getStudent_id());

            results.setDiscountFee(discount.getAmount());

        } else {
            results.setDiscountFee("0.0");
        }

        if (student.isAnyGovtFee()) {

            Course courseByID = new CourseQueries().getCourseById(courseID);

            results.setGovtFee(courseByID.getGovernmentFee());

        } else {
            results.setGovtFee("0.0");
        }

        totalFees = calculateTotalFees(results.getCourseFee(), results.getGovtFee(), results.getDiscountFee());
        results.setTotalFee("" + totalFees);

        ObservableList<Receipts> receipts = new ReceiptQueries().getReceiptsFor(student.getStudent_id());

        for (Receipts receipt : receipts) {
            totalPaid += Double.parseDouble(receipt.getAmount());
        }

        results.setTotalPaid("" + totalPaid);

        balance = totalFees - totalPaid;

        results.setBalance("" + balance);

        return results;
    }

    public Double calculateTotalFees(String courseFee, String govtFee, String discountFee) {
        System.out.println(Double.parseDouble(govtFee));
        return Double.parseDouble(courseFee) + Double.parseDouble(govtFee) - Double.parseDouble(discountFee);
    }
}
