package jindal5.mayank.popular_movies_14ce10032_gsc;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class settings_activityFragment extends PreferenceFragment {

    public settings_activityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_activity, container, false);
       // addPreferencesFromResource(R.xml.pref_general);
    }
}
