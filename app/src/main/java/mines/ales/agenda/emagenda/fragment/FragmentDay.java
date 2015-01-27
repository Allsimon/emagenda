package mines.ales.agenda.emagenda.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.emagenda.R;
import mines.ales.agenda.emagenda.adapter.AdapterCourse;

@EFragment(R.layout.fragment_day)
public class FragmentDay extends Fragment {
    private static final String ID = "COURSES";
    @ViewById
    ListView lvDay;
    List<Course> mCourses;
    AdapterCourse mAdapterCourse;

    public FragmentDay() {
    }

    public static FragmentDay newInstance(Serializable courses) {
        FragmentDay fragment = new FragmentDay_();
        Bundle args = new Bundle();
        args.putSerializable(ID, courses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCourses = new ArrayList<>();
        if (getArguments() != null) {
            Object serialized = getArguments().getSerializable(ID);
            if (serialized instanceof List) {
                mCourses = (ArrayList<Course>) serialized;
                Collections.sort(mCourses);
            }
        }
    }

    @AfterViews
    public void initAdapter() {
        mAdapterCourse = new AdapterCourse(getActivity(), mCourses);
        lvDay.setAdapter(mAdapterCourse);
    }
}
