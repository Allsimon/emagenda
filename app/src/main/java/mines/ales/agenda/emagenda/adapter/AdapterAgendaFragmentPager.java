package mines.ales.agenda.emagenda.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.emagenda.fragment.FragmentDay_;

public class AdapterAgendaFragmentPager extends FragmentPagerAdapter {
    private final SimpleDateFormat formatter = new SimpleDateFormat("d MMMM");
    Map<Integer, List<Course>> mCourses;

    public AdapterAgendaFragmentPager(FragmentManager fm) {
        super(fm);
    }


    /**
     * This method sorts the courses, and put them in a  Map<Integer, List<Course>> :
     * it's going to assign every course of a day to an integer
     *
     * @param courses
     */
    public void addCourses(Map<Integer, List<Course>> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }


    public Date getDateAtPosition(Integer position) {
        return mCourses.get(position % mCourses.size()).get(0).getStartTime();
    }


    @Override
    public Fragment getItem(int position) {
        return FragmentDay_.newInstance(new ArrayList<>(mCourses.get(position)));
    }

    @Override
    public int getCount() {
        if (mCourses == null)
            return 0;
        return mCourses.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mCourses.get(position).size() == 0)
            return "";
        return formatter.format(mCourses.get(position).get(0).getStartTime());
    }
}
