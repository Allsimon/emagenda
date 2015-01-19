package mines.ales.agenda.api;

import org.androidannotations.annotations.EBean;

import java.util.Date;
import java.util.List;

import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.api.pojo.Student;

@EBean
public interface API_agenda {

    public List<Promotion> getAllPromotions();

    public List<Student> getAllStudents();

    public List<Course> getAllCourses(Date startDate, Date endDate);

    public List<Course> getAllCoursesByPromotion(Promotion promotion, Date startDate, Date endDate);

    public List<Student> getStudentByPromotion(Promotion promotion);

    public List<Course> getAllCoursesByStudent(Student student, Date startDate, Date endDate);
}
