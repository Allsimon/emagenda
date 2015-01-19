package mines.ales.agenda.emagenda;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.Date;
import java.util.List;

import mines.ales.agenda.api.API_agenda;
import mines.ales.agenda.api.API_emagenda;
import mines.ales.agenda.api.callback.OnCourseListener;
import mines.ales.agenda.api.callback.OnPromotionListener;
import mines.ales.agenda.api.callback.OnStudentListener;
import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.api.pojo.Student;

@EActivity
public class MainActivity extends ActionBarActivity implements OnStudentListener, OnPromotionListener, OnCourseListener {
    @Bean(API_emagenda.class)
    API_agenda api_agenda;
    private final String TAG = "MAIN ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api_agenda.addOnStudentListener(this);
        api_agenda.addOnPromotionListener(this);
        api_agenda.addOnCourseListener(this);
        api_agenda.getAllPromotions();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
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
        api_agenda.getAllCoursesByPromotion(promotions.get(3), new Date(), new Date());
    }

    @Override
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
