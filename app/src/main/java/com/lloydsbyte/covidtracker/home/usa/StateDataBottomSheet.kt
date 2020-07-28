package com.lloydsbyte.covidtracker.home.usa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.StateModel
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import kotlinx.android.synthetic.main.bottomsheet_data.view.*

class StateDataBottomSheet: BottomSheetDialogFragment() {

    lateinit var stateModel: StateModel

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
    }
}