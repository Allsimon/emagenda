package mines.ales.agenda.emagenda.view;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Map;

public class AutoCompletePreference extends EditTextPreference {

    private static AutoCompleteTextView mEditText = null;
    Map<String, Long> mPreferences;
    private Context mContext;

    public AutoCompletePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mEditText = new AutoCompleteTextView(context, attrs);
        mEditText.setThreshold(0);
    }

    public void setPossibleValues(Map<String, Long> preferences) {
        mPreferences = preferences;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, new ArrayList<>(mPreferences.keySet()));
        mEditText.setAdapter(adapter);
    }

    @Override
    protected void onBindDialogView(View view) {
        AutoCompleteTextView editText = mEditText;
        editText.setText(getText());
        ViewParent oldParent = editText.getParent();
        if (oldParent != view) {
            if (oldParent != null) {
                ((ViewGroup) oldParent).removeView(editText);
            }
            onAddEditTextToDialogView(view, editText);
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            String value = mEditText.getText().toString();
            if (callChangeListener(mPreferences.get(value))) {
                setText(value);
            }
        }
    }
}
