package mines.ales.agenda.emagenda;

import android.os.Bundle;

import com.activeandroid.ActiveAndroid;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import mines.ales.agenda.api.API_agenda;
import mines.ales.agenda.api.API_emagenda;
import mines.ales.agenda.api.callback.OnCourseListener;
import mines.ales.agenda.api.callback.OnPromotionListener;
import mines.ales.agenda.api.callback.OnStudentListener;
import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.api.pojo.Student;
import mines.ales.agenda.emagenda.adapter.NavDrawerItem;
import mines.ales.agenda.emagenda.fragment.FragmentAgenda_;
import mines.ales.agenda.emagenda.fragment.FragmentSettings_;

@EActivity
public class MainActivity extends AbstractActivity implements OnStudentListener, OnPromotionListener, OnCourseListener {
    private final String TAG = "MAIN ACTIVITY";
    @Bean(API_emagenda.class)
    API_agenda api_agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api_agenda.addOnStudentListener(this);
        api_agenda.addOnPromotionListener(this);
        api_agenda.addOnCourseListener(this);
        api_agenda.getAllPromotions();

    }

    @Override
    public void prepareNavDrawerItems() {
        mNavDrawerItems.add(new NavDrawerItem(getResources().getString(R.string.action_settings), R.drawable.ic_action_settings) {
            public void onClick() {
                changeFragment(new FragmentSettings_());

            }
        });
        mNavDrawerItems.add(new NavDrawerItem(getResources().getString(R.string.agenda), R.drawable.ic_action_event) {
            @Override
            public void onClick() {
                changeFragment(new FragmentAgenda_());
            }
        });
    }


    @Override
    public void initFragment() {
        changeFragment(new FragmentSettings_());
    }

    @Override
    protected void onStop() {
        super.onStop();
        api_agenda.removeOnStudentListener(this);
        api_agenda.removeOnPromotionListener(this);
        api_agenda.removeOnCourseListener(this);
    }

    /**
     * http://webdfd.ema.fr/cybema/cgi-bin/cgiempt.exe?TYPE=planning_txt&DATEDEBUT=20150101&DATEFIN=20150303&VALCLE=37&TYPECLE=p0cleunik
     * http://webdfd.ema.fr/cybema/cgi-bin/cgiempt.exe?TYPE=eleves_txt
     * http://webdfd.ema.fr/cybema/cgi-bin/cgiempt.exe?TYPE=cours_txt
     * http://webdfd.ema.fr/cybema/cgi-bin/cgiempt.exe?TYPE=promos_txt
     */


    @Override
    @Background
    public void onStudentsFound(List<Student> students) {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < students.size(); i++)
                students.get(i).save();
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    @Background
    public void onPromotionsFound(List<Promotion> promotions) {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < promotions.size(); i++)
                promotions.get(i).save();
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        api_agenda.getAllStudents();
    }


    @Override
    @Background
    public void onCoursesFound(List<Course> courses) {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < courses.size(); i++) {
                courses.get(i).save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
