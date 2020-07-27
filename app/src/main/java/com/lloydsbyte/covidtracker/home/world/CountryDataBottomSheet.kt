package com.lloydsbyte.covidtracker.home.world

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import kotlinx.android.synthetic.main.bottomsheet_country_data.view.*

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
        return inflater.inflate(R.layout.bottomsheet_country_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            country_close_fab.setOnClickListener {
                dialog?.dismiss()
            }
            country_name.text = countryModel.countryName
            country_population.text = resources.getString(R.string.country_population, AppUtilz.insertCommas(countryModel.population))
            country_updated_at.text = countryModel.updatedAt //Todo format date
            //Todo convert to short hand
            country_confirmed.text = resources.getString(R.string.country_confirmed, AppUtilz.abbreviateNumber(countryModel.totalConfirmed))//AppUtilz.insertCommas(countryModel.totalConfirmed)
            country_recovered.text = resources.getString(R.string.country_recovered, AppUtilz.abbreviateNumber(countryModel.totalRecovered))
            country_deaths.text = resources.getString(R.string.country_deaths, AppUtilz.abbreviateNumber(countryModel.totalDeaths))
            country_recovery_rate.text = resources.getString(R.string.country_recover_rate, AppUtilz.convertToPercentage(countryModel.recoveryRate))
            country_death_rate.text = resources.getString(R.string.country_death_rate, AppUtilz.convertToPercentage(countryModel.deathRate))
        }
    }
}