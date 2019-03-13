package com.pompouslypickled.venison.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.pompouslypickled.venison.R

class SettingsFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener{



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Settings"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

}