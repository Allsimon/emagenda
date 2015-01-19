package mines.ales.agenda.api.callback;

import java.util.ArrayList;
import java.util.List;

import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.api.pojo.Student;

public class Notifier {
    List<OnCourseListener> onCourseListeners;
    List<OnPromotionListener> onPromotionListeners;
    List<OnStudentListener> onStudentListeners;

    public void addOnCourseListener(OnCourseListener onCourseListener) {
        if (onCourseListeners == null)
            onCourseListeners = new ArrayList<>();
        onCourseListeners.add(onCourseListener);
    }

    public void addOnPromotionListener(OnPromotionListener onPromotionListener) {
        if (onPromotionListeners == null)
            onPromotionListeners = new ArrayList<>();
        onPromotionListeners.add(onPromotionListener);
    }

    public void addOnStudentListener(OnStudentListener onStudentListener) {
        if (onStudentListeners == null)
            onStudentListeners = new ArrayList<>();
        onStudentListeners.add(onStudentListener);
    }

    public void removeOnCourseListener(OnCourseListener onCourseListener) {
        if (onCourseListeners != null)
            onCourseListeners.remove(onCourseListener);
    }

    public void removeOnPromotionListener(OnPromotionListener onPromotionListener) {
        if (onPromotionListeners != null)
            onPromotionListeners.remove(onPromotionListener);
    }

    public void removeOnStudentListener(OnStudentListener onStudentListener) {
        if (onStudentListeners != null)
            onStudentListeners.remove(onStudentListener);
    }

    public void notifyCoursesFound(List<Course> courses) {
        if (onCourseListeners != null)
            for (OnCourseListener onCourseListener : onCourseListeners) {
                onCourseListener.onCoursesFound(courses);
            }
    }

    public void notifyPromotionsFound(List<Promotion> promotions) {
        if (onPromotionListeners != null)
            for (OnPromotionListener onPromotionListener : onPromotionListeners) {
                onPromotionListener.onPromotionsFound(promotions);
            }
    }

    public void notifyStudentsFound(List<Student> students) {
        if (onStudentListeners != null)
            for (OnStudentListener onStudentListener : onStudentListeners) {
                onStudentListener.onStudentsFound(students);
            }
    }
}
