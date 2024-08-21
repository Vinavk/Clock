package com.example.alarm.ViewModel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DataFactory(
    private val dataStore: DataStore<Preferences>,
    private val alarmdataStore: DataStore<Preferences>,
    private val context: Context,

    ) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmViewModel(dataStore,alarmdataStore,context) as T
    }
}