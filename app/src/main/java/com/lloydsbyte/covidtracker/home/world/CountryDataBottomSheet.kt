package com.lloydsbyte.covidtracker.home.world

import android.R.attr.entries
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lloydsbyte.covidtracker.MainActivity
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.network.WorldDataApiService
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import com.lloydsbyte.covidtracker.utilz.ModelConverter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottomsheet_data.*
import kotlinx.android.synthetic.main.bottomsheet_data.view.*
import timber.log.Timber


class CountryDataBottomSheet : BottomSheetDialogFragment() {

    lateinit var countryModel: CountryModel
    var connectionDisposable: Disposable? = null
    val countryDataApiService by lazy {
        WorldDataApiService.ApiService.create()
    }

    fun newInstance(country: CountryModel): CountryDataBottomSheet {
        val fragment = CountryDataBottomSheet()
        fragment.countryModel = country
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
            data_name.text = countryModel.countryName
            data_population.text = resources.getString(
                R.string.data_population,
                AppUtilz.insertCommas(countryModel.population)
            )
            data_updated_at.text = AppUtilz.convertDate(countryModel.updatedAt)
            data_confirmed.text = resources.getString(
                R.string.data_confirmed,
                AppUtilz.abbreviateNumber(countryModel.totalConfirmed)
            )//AppUtilz.insertCommas(countryModel.totalConfirmed)
            data_recovered.text = resources.getString(
                R.string.data_recovered,
                AppUtilz.abbreviateNumber(countryModel.totalRecovered)
            )
            data_deaths.text = resources.getString(
                R.string.data_deaths,
                AppUtilz.abbreviateNumber(countryModel.totalDeaths)
            )
            data_recovery_rate.text = resources.getString(
                R.string.data_recover_rate,
                AppUtilz.convertToPercentage(countryModel.recoveryRate)
            )
            data_death_rate.text = resources.getString(
                R.string.data_death_rate,
                AppUtilz.convertToPercentage(countryModel.deathRate)
            )

            pullCountryHistoryData()
//            prepareChart(listOf("one","one","one","one","one","one","one","one","one"))
        }
    }

    fun pullCountryHistoryData() {
        connectionDisposable = countryDataApiService.pullCountryData(countryModel.countryCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    prepareChart(result.data.timeline.asReversed())
                },
                { error ->
                    Timber.d("JL_ error: ${error.message}")
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG)
                        .show()
                }
            )
    }

    fun prepareChart(countries: List<WorldDataApiService.Timeline>) {
        var entries: MutableList<Entry> = mutableListOf()
        for ((count, data) in countries.withIndex()) {

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