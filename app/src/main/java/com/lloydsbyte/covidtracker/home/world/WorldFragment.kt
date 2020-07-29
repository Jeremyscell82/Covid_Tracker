package com.lloydsbyte.covidtracker.home.world

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
import com.afollestad.materialdialogs.MaterialDialog
import com.lloydsbyte.covidtracker.BuildConfig
import com.lloydsbyte.covidtracker.MainActivity
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.home.HomeViewPagerFragment
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.utilz.ModelConverter
import com.lloydsbyte.covidtracker.network.WorldDataApiService
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import com.lloydsbyte.covidtracker.utilz.AppUtilz.Companion.showHideKeyboardForSearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_world.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import timber.log.Timber

class WorldFragment : Fragment() {

    private var keyboardDisplayed: Boolean = false //used for auto dismissal of keyboard
    lateinit var worldAdapter: WorldAdapter
    var databaseDisposable: Disposable? = null
    var connectionDisposable: Disposable? = null
    val worldDataApiService by lazy {
        WorldDataApiService.ApiService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        worldAdapter = WorldAdapter()
        return inflater.inflate(R.layout.fragment_world, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            world_recyclerview.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = worldAdapter
                worldAdapter.onItemClicked = {
                    launchDataSheet(it)
                }
            }

            toolbar_search_fab.setOnClickListener {
                toolbar_search_fab.isExpanded = true
                showHideKeyboardForSearch(requireContext(), true, search_field)
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
                    worldAdapter.filterAdapter(p0.toString(), world_recyclerview)
                }

            })
            search_field.setOnFocusChangeListener { view, b ->
                keyboardDisplayed = b
            }

            world_info_fab.setOnClickListener {
                showInfoDialog()
            }
            world_recyclerview.setOnScrollChangeListener { view, i, i2, i3, i4 ->
                val accidentalScroll: Boolean = (i == 0 && i2 == 0 && i3 == 0 && i4 == 0)
                if (keyboardDisplayed && !accidentalScroll) {
                    showHideKeyboardForSearch(
                        requireContext(),
                        false,
                        search_field
                    )
                    keyboardDisplayed = false
                }

            }

            //Setup recyclerview
            databaseDisposable =
                HomeViewPagerFragment.homeViewModel.getWorldList((requireActivity() as MainActivity).appDatabase)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            Timber.d("New entries have been added, reload the list with ${result.size} items")
                            //Set toolbar title (world count)
                            toolbar_title.text = resources.getString(R.string.toolbar_count_title, AppUtilz.calculateWorldCount(result))
                            worldAdapter.initAdapter(
                                result,
                                world_recyclerview
                            )
                        },
                        { error ->
                            Timber.d("Something has gone wrong $error")
                        }
                    )
        }
        if (worldAdapter.adapterItems.isEmpty()) pullWorldData()

        //Set version number
        version_view.text = BuildConfig.VERSION_NAME
    }

    fun pullWorldData() {
        connectionDisposable = worldDataApiService.pullWorldData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Timber.d("JL_ success, retrieved ${result.data.size}} countries")
                    val convertedResults = ModelConverter.WorldConverter(result.data)
                    (requireActivity() as MainActivity).saveWorldData(convertedResults)
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

    private fun showInfoDialog() {
        MaterialDialog(requireActivity()).show {
            title(R.string.dialog_title)
            message(R.string.dialog_world_message)
            positiveButton(R.string.dialog_ok) { dialog ->
                dialog.dismiss()
            }
        }
    }

    fun closeSearchBar(){
        toolbar_search_fab.isExpanded = false
        search_field.setText("") //Clear entry on closing of search bar
        showHideKeyboardForSearch(requireContext(), false, search_field)
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