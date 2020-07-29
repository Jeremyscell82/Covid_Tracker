package com.lloydsbyte.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.room.Room
import com.afollestad.materialdialogs.MaterialDialog
import com.lloydsbyte.covidtracker.database.AppDatabase
import com.lloydsbyte.covidtracker.home.HomeViewPagerFragment
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.database.StateModel
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "com.lloydsbyte.todoos.db"
        ).build()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.main_container,
                    HomeViewPagerFragment()
                )
                .commit()
        }

        if (AppUtilz.checkConnection(this)){
            displayLoadingScreen(true)
            clearData()
        } else {
            //Todo display dialog of no connection and to load last update (save date in shared preferences)
            displayInfoDialog(R.string.dialog_no_connection_title, R.string.dialog_no_connection_message)
        }
    }

    fun displayLoadingScreen(displayIt: Boolean) {
        loading_progress_view.visibility = if (displayIt) View.VISIBLE else View.GONE
    }

    fun displayInfoDialog(title: Int, message: Int) {
        MaterialDialog(this).show {
            title(title)
            message(message)
            positiveButton(R.string.dialog_ok) { dialog ->
                dialog.dismiss()
            }
        }
    }

    /**
     * Database controls
     */
    fun saveWorldData(countriesList: List<CountryModel>) {
        Timber.d("JL_ Saving ${countriesList.size} countries to the database")
        GlobalScope.launch {
            appDatabase.WorldDao().addWorldList(countriesList)
        }
        displayLoadingScreen(false)
    }

    fun saveUsaData(stateList: List<StateModel>) {
        Timber.d("JL_ Saving ${stateList.size} states to the database")
        GlobalScope.launch {
            appDatabase.UsaDao().addStatesList(stateList)
        }
        displayLoadingScreen(false) //This could cause issues, if one fails it will be dismissed by the other pull... fix coming soon!
    }

    private fun clearData(){
        Timber.d("JL_ clearing data")
        GlobalScope.launch {
            appDatabase.clearAllTables()
        }
    }
}