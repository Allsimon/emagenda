package mines.ales.agenda.api;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mines.ales.agenda.api.callback.Notifier;
import mines.ales.agenda.api.callback.OnCourseListener;
import mines.ales.agenda.api.callback.OnPromotionListener;
import mines.ales.agenda.api.callback.OnStudentListener;
import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.api.pojo.Student;

@EBean
public abstract class API_agenda extends Notifier {

    public abstract void getAllPromotions();

    public abstract void getAllStudents();

    public abstract void getAllCourses(Date startDate, Date endDate);

    public abstract void getAllCoursesByPromotion(Promotion promotion, Date startDate, Date endDate);

    public abstract void getStudentByPromotion(Promotion promotion);

    public abstract void getAllCoursesByStudent(Student student, Date startDate, Date endDate);
}
