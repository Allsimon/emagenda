package mines.ales.agenda.api.callback;

import java.util.List;

import mines.ales.agenda.api.pojo.Course;

public interface OnCourseListener {
    void onCoursesFound(List<Course> courses);
}
