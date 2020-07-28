package com.lloydsbyte.covidtracker.home.world

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import kotlinx.android.synthetic.main.bottomsheet_data.view.*

class CountryDataBottomSheet: BottomSheetDialogFragment() {

    lateinit var countryModel: CountryModel

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
            data_population.text = resources.getString(R.string.data_population, AppUtilz.insertCommas(countryModel.population))
            data_updated_at.text = AppUtilz.convertDate(countryModel.updatedAt)
            data_confirmed.text = resources.getString(R.string.data_confirmed, AppUtilz.abbreviateNumber(countryModel.totalConfirmed))//AppUtilz.insertCommas(countryModel.totalConfirmed)
            data_recovered.text = resources.getString(R.string.data_recovered, AppUtilz.abbreviateNumber(countryModel.totalRecovered))
            data_deaths.text = resources.getString(R.string.data_deaths, AppUtilz.abbreviateNumber(countryModel.totalDeaths))
            data_recovery_rate.text = resources.getString(R.string.data_recover_rate, AppUtilz.convertToPercentage(countryModel.recoveryRate))
            data_death_rate.text = resources.getString(R.string.data_death_rate, AppUtilz.convertToPercentage(countryModel.deathRate))
        }
    }
}