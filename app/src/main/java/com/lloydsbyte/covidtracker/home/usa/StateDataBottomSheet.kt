package com.lloydsbyte.covidtracker.home.usa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.StateModel
import com.lloydsbyte.covidtracker.network.UsaDataApiService
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottomsheet_data.*
import kotlinx.android.synthetic.main.bottomsheet_data.view.*
import timber.log.Timber

class StateDataBottomSheet: BottomSheetDialogFragment() {

    lateinit var stateModel: StateModel
    var connectionDisposable: Disposable? = null
    val stateDataApiService by lazy {
        UsaDataApiService.ApiService.create()
    }

    fun newInstance(state: StateModel): StateDataBottomSheet {
        val fragment = StateDataBottomSheet()
        fragment.stateModel = state
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            data_close_fab.setOnClickListener {
                dialog?.dismiss()
            }
            data_name.text = stateModel.stateName
//            data_population.text = resources.getString(R.string.country_population, AppUtilz.insertCommas(countryModel.population))
            data_updated_at.text = stateModel.updatedAt//AppUtilz.convertDate(stateModel.updatedAt)
            data_confirmed.text = resources.getString(R.string.data_confirmed, AppUtilz.abbreviateNumber(stateModel.totalConfirmed))//AppUtilz.insertCommas(countryModel.totalConfirmed)
            data_recovered.text = resources.getString(R.string.data_recovered, AppUtilz.abbreviateNumber(stateModel.totalRecovered))
            //Deaths are used for increase in state bottom sheet
            data_deaths.text = resources.getString(R.string.data_increase, AppUtilz.abbreviateNumber(stateModel.increase))
            //Bottom Two items are Hospitalized | Deaths
            data_recovery_rate.text = resources.getString(R.string.data_hospitalized, AppUtilz.insertCommas(stateModel.hospitalized))
            data_death_rate.text = resources.getString(R.string.data_deaths, AppUtilz.insertCommas(stateModel.totalDeaths))
        }
        pullCountryHistoryData()
    }

    fun pullCountryHistoryData() {
        connectionDisposable = stateDataApiService.pullStateData(stateModel.stateCode.toLowerCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    prepareChart(result.asReversed())
                },
                { error ->
                    Timber.d("JL_ error: ${error.message}")
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG)
                        .show()
                }
            )
    }

    fun prepareChart(history: List<UsaDataApiService.StateHistory>) {
        var entries: MutableList<Entry> = mutableListOf()
        for ((count, data) in history.withIndex()) {

            // turn your data into Entry objects
            entries.add(
                Entry(
                    count.toFloat(),
                    data.newConfirmed.toFloat()
                )
            )
        }
        var dataSet = LineDataSet(entries, "Count")
        data_linechart.data = LineData(dataSet)
        data_linechart.invalidate()
    }
}