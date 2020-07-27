package com.lloydsbyte.covidtracker.home

import androidx.lifecycle.ViewModel
import com.lloydsbyte.covidtracker.database.AppDatabase
import com.lloydsbyte.covidtracker.database.CountryModel
import io.reactivex.Flowable

class HomeViewModel(): ViewModel() {

    fun getWorldList(appDatabase: AppDatabase): Flowable<List<CountryModel>> {
        return appDatabase.WorldDao().getWorldList()
    }
}