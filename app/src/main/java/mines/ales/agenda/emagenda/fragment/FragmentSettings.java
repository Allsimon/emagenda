package mines.ales.agenda.emagenda.fragment;


import android.os.Bundle;
import android.preference.ListPreference;

import com.github.machinarius.preferencefragment.PreferenceFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mines.ales.agenda.api.API_agenda;
import mines.ales.agenda.api.API_emagenda;
import mines.ales.agenda.api.callback.OnPromotionListener;
import mines.ales.agenda.api.callback.OnStudentListener;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.api.pojo.Student;
import mines.ales.agenda.emagenda.R;
import mines.ales.agenda.emagenda.view.AutoCompletePreference;

@EFragment
public class FragmentSettings extends PreferenceFragment implements OnPromotionListener, OnStudentListener {
    public static final String PROMOTION = "promotion";
    public static final String NAME = "name";
    ListPreference mLpPromotions;
    AutoCompletePreference mACPNames;
    @Bean(API_emagenda.class)
    API_agenda mApiAgenda;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mLpPromotions = (ListPreference) findPreference(PROMOTION);
        mACPNames = (AutoCompletePreference) findPreference(NAME);
    }

    @Override
    @UiThread
    public void onPromotionsFound(List<Promotion> promotions) {
        CharSequence[] entries = new CharSequence[promotions.size()];
        CharSequence[] entryValues = new CharSequence[promotions.size()];
        int i = 0;
        for (Promotion promotion : promotions) {
            entries[i] = promotion.getName();
            entryValues[i] = promotion.getApp_id() + "";
            i++;
        }
        mLpPromotions.setEntries(entries);
        mLpPromotions.setEntryValues(entryValues);
        mLpPromotions.setEnabled(true);
    }

    @Override
    @UiThread
    public void onStudentsFound(List<Student> students) {
        Map<String, Long> values = new HashMap<>();
        for (Student student : students) {
            values.put(student.getLastName() + " " + student.getFirstName(), student.getApp_id());
        }
        mACPNames.setPossibleValues(values);
    }

    @Override
    public void onStop() {
        super.onStop();
        mApiAgenda.removeOnPromotionListener(this);
        mApiAgenda.removeOnStudentListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiAgenda.addOnPromotionListener(this);
        mApiAgenda.addOnStudentListener(this);
        mApiAgenda.getAllPromotions();
        mApiAgenda.getAllStudents();
    }

}
