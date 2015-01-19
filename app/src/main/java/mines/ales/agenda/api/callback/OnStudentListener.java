package mines.ales.agenda.api.callback;

import java.util.List;

import mines.ales.agenda.api.pojo.Student;

public interface OnStudentListener {
    void onStudentsFound(List<Student> students);
}
