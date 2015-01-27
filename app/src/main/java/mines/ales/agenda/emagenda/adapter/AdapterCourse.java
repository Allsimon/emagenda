package mines.ales.agenda.emagenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.emagenda.R;

public class AdapterCourse extends BaseAdapter {

    private final Context mContext;
    List<Course> courses;
    LayoutInflater inflater;

    public AdapterCourse(Context context, List<Course> courses) {
        inflater = LayoutInflater.from(context);
        this.courses = courses;
        mContext = context;
    }

    public int getCount() {
        return courses.size();
    }

    public Object getItem(int position) {
        return courses.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_course, null);
            holder.tvCourseName = (TextView) convertView
                    .findViewById(R.id.tvCourseName);
            holder.tvType = (TextView) convertView
                    .findViewById(R.id.tvType);
            holder.tvCourseRoomName = (TextView) convertView
                    .findViewById(R.id.tvCourseRoomName);
            holder.tvCourseTeacherName = (TextView) convertView
                    .findViewById(R.id.tvCourseTeacherName);
            holder.tvCourseHours = (TextView) convertView.findViewById(R.id.tvCourseHours);
            holder.tvCourseNote = (TextView) convertView
                    .findViewById(R.id.tvCourseNote);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCourseName.setText(courses.get(position).getName());
        holder.tvCourseRoomName.setText(courses.get(position).getRoom());
        holder.tvCourseTeacherName.setText(courses.get(position).getTeacher());
        holder.tvCourseHours.setText(courses.get(position).getDuration());
        String note = courses.get(position).getNote();
        if (note != null) {
            holder.tvCourseNote.setText(note);
            holder.tvCourseNote.setVisibility(View.VISIBLE);
        }
        if (courses.get(position).isControle()) {
            holder.tvType.setText(mContext.getResources().getString(R.string.courseTest));
            holder.tvType.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvCourseName;
        TextView tvCourseRoomName;
        TextView tvCourseTeacherName;
        TextView tvCourseHours;
        TextView tvCourseNote;
        TextView tvType;
    }
}