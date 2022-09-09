package fees;

import courses.Course;
import courses.CourseDuration;
import courses.CourseQueries;
import courses.CourseType;

public class CategoryFees {

    public Double getCategoryFees(String courseCode, String category) {
        Course course = new CourseQueries().getCourse(courseCode);
        CourseType ctype = new CourseQueries().getCourseType(category);
        CourseDuration cd = new CourseQueries().getCourseInfo(course.getId(), ctype.getId());

        return Double.parseDouble(cd.getAmount());

    }
}
