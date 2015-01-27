package mines.ales.agenda.emagenda.fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.activeandroid.query.Select;
import com.viewpagerindicator.TitlePageIndicator;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mines.ales.agenda.api.API_agenda;
import mines.ales.agenda.api.API_emagenda;
import mines.ales.agenda.api.callback.OnCourseListener;
import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.emagenda.R;
import mines.ales.agenda.emagenda.Utils;
import mines.ales.agenda.emagenda.adapter.AdapterAgendaFragmentPager;


@EFragment(R.layout.fragment_agenda)
public class FragmentAgenda extends Fragment implements TitlePageIndicator.OnCenterItemClickListener, OnCourseListener, ViewPager.OnPageChangeListener {
    public static final String NAME = "name";
    public static final String NAME_SEARCH = "name_search";
    public static final String PROMOTION = "promotion";
    @ViewById(R.id.vpAgenda)
    ViewPager mViewPager;
    @ViewById(R.id.tpiAgenda)
    TitlePageIndicator mIndicator;
    @Bean(API_emagenda.class)
    API_agenda mApiAgenda;
    boolean firstTime = true;
    Map<Integer, List<Course>> mCourses;
    private AdapterAgendaFragmentPager mFragmentPagerAdapter;

    @AfterViews
    public void init() {
        mIndicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
        mIndicator.setOnCenterItemClickListener(this);
        mFragmentPagerAdapter = new AdapterAgendaFragmentPager(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mIndicator.setViewPager(mViewPager);
    }

    @AfterInject
    @Background
    public void initAgenda() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean searchByName = preferences.getBoolean(NAME_SEARCH, false);
        if (!searchByName) {
            Promotion promotion = new Select().from(Promotion.class).where("app_id = ?", preferences.getString(PROMOTION, "")).executeSingle();
            if (promotion != null) {
                mApiAgenda.getAllCoursesByPromotion(promotion, Utils.addDays(new Date(), -15), Utils.addDays(new Date(), 15));
            }
        } else {
        }
    }

    @Override
    public void onCenterItemClick(int position) {
        DialogFragment newFragment = new FragmentTimePicker();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    /**
     * The first time update courses list, it should show today's date
     * If the list of courses need to be updated, it will keep the day we were before the update
     */
    @Override
    public void onCoursesFound(List<Course> courses) {
        if (courses.size() == 0)
            return;
        mCourses = list2Map(courses);
        if (firstTime) {
            addCourses(mCourses);
            setCurrentItem(new Date());
            firstTime = false;
        } else {
            Date oldDate = mFragmentPagerAdapter.getDateAtPosition(mViewPager.getCurrentItem());
            addCourses(mCourses);
            setCurrentItem(oldDate);
        }
    }

    public Map<Integer, List<Course>> list2Map(List<Course> courses) {
        Map<Integer, List<Course>> mCourses = new HashMap<>();
        Integer position = 0;
        mCourses.put(position, new ArrayList<Course>());
        mCourses.get(position).add(courses.get(position));
        for (int i = 1; i < courses.size(); i++) {
            if (!compareDays(courses.get(i - 1).getStartTime(), courses.get(i).getStartTime())) {
                position++;
                mCourses.put(position, new ArrayList<Course>());
            }
            mCourses.get(position).add(courses.get(i));
        }
        return mCourses;
    }

    public int getPositionForDate(Date date) {
        if (mCourses != null)
            for (Integer integer : mCourses.keySet()) {
                if (closestDay(mCourses.get(integer).get(0).getStartTime(), date)) {
                    return integer;
                }
            }
        return 0;
    }

    @UiThread
    public void setCurrentItem(Date date) {
        mViewPager.setCurrentItem(getPositionForDate(date), true);
    }

    /**
     * @param date1
     * @param date2
     * @return true if the day of the first date >= second date, it's useful to set the date for
     * the days following date1 (sunday => monday)
     */
    public boolean closestDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) >= cal2.get(Calendar.DAY_OF_YEAR);
    }

    public boolean compareDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    @UiThread
    public void addCourses(Map<Integer, List<Course>> courses) {
        mFragmentPagerAdapter.addCourses(courses);
    }

    @Override
    public void onStop() {
        super.onStop();
        mApiAgenda.removeOnCourseListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiAgenda.addOnCourseListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
