package in.ghostreborn.wanpisu.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register the listener
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_DUB:
                WanPisuConstants.preferences.edit()
                        .putBoolean(WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_DUB, sharedPreferences.getBoolean(key, false))
                        .apply();
            case WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_UNKNOWN:
                WanPisuConstants.preferences.edit()
                        .putBoolean(WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_UNKNOWN, sharedPreferences.getBoolean(key, false))
                        .apply();
        }
    }
}