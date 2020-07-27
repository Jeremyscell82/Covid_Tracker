package com.lloydsbyte.covidtracker.home.usa

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lloydsbyte.covidtracker.MainActivity
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.home.HomeViewPagerFragment
import com.lloydsbyte.covidtracker.home.world.CountryDataBottomSheet
import com.lloydsbyte.covidtracker.network.UsaDataApiService
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import com.lloydsbyte.covidtracker.utilz.ModelConverter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_usa.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import timber.log.Timber

class UsaFragment: Fragment() {

    private var keyboardDisplayed: Boolean = false
    lateinit var usaAdapter: UsaAdapter
    var databaseDisposable: Disposable? = null
    var connectionDisposable: Disposable? = null
    val usaDataApiService by lazy {
        UsaDataApiService.ApiService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usaAdapter = UsaAdapter()
        return inflater.inflate(R.layout.fragment_usa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            Timber.d("JL_ adapter item has been created")
            toolbar_item_title.text = resources.getString(R.string.toolbar_state)
            usa_recyclerview.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = usaAdapter
                usaAdapter.onItemClicked = {
                    Timber.d("JL_ adapter item has been clicked")
                }
            }

            toolbar_search_fab.setOnClickListener {
                toolbar_search_fab.isExpanded = true
                AppUtilz.showHideKeyboardForSearch(requireContext(), true, search_field)
                keyboardDisplayed = true
            }

            search_close_icon.setOnClickListener {
                closeSearchBar()
            }

            search_field.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    usaAdapter.filterAdapter(p0.toString(), usa_recyclerview)
                }

            })
            search_field.setOnFocusChangeListener { view, b ->
                keyboardDisplayed = b
            }

            usa_recyclerview.setOnScrollChangeListener { view, i, i2, i3, i4 ->
                val accidentalScroll: Boolean = (i == 0 && i2 == 0 && i3 == 0 && i4 == 0)
                if (keyboardDisplayed && !accidentalScroll) {
                    AppUtilz.showHideKeyboardForSearch(
                        requireContext(),
                        false,
                        search_field
                    )
                    keyboardDisplayed = false
                }

            }

            //Setup recyclerview
            databaseDisposable =
                HomeViewPagerFragment.homeViewModel.getUsaList((requireActivity() as MainActivity).appDatabase)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            Timber.d("JL_ New entries have been added, reload the list with ${result.size} states")
                            //Set toolbar title (usa count)
                            toolbar_title.text = resources.getString(R.string.toolbar_count_title, AppUtilz.calculateUsaCount(result))
                            usaAdapter.initAdapter(
                                result,
                                usa_recyclerview
                            )
                        },
                        { error ->
                            Timber.d("JL_ Something has gone wrong $error")
                        }
                    )
        }
        if (usaAdapter.adapterItems.isEmpty()) pullUSAData()
    }

    fun pullUSAData() {
        connectionDisposable = usaDataApiService.pullUsaData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Timber.d("JL_ success, retrieved ${result.size}} states")
                    val convertedResults = ModelConverter.USAConverter(result)
                    (requireActivity() as MainActivity).saveUsaData(convertedResults)
                },
                { error ->
                    Timber.d("JL_ error: ${error.message}")
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG)
                        .show()
                }
            )
    }

    private fun launchDataSheet(country: CountryModel) {
        val bottomsheet = CountryDataBottomSheet().newInstance(country)
        bottomsheet.show(requireActivity().supportFragmentManager, bottomsheet.tag)
    }

    fun closeSearchBar(){
        toolbar_search_fab.isExpanded = false
        search_field.setText("") //Clear entry on closing of search bar
        AppUtilz.showHideKeyboardForSearch(requireContext(), false, search_field)
        keyboardDisplayed = false
    }

    override fun onDestroy() {
        super.onDestroy()
        connectionDisposable?.dispose()
        databaseDisposable?.dispose()
    }

    override fun onResume() {
        super.onResume()
        if (keyboardDisplayed)closeSearchBar()
    }
}