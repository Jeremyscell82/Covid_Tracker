package com.lloydsbyte.covidtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lloydsbyte.covidtracker.network.WorldDataApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottomsheet_debug.view.*
import timber.log.Timber

class DebugBottomSheet: BottomSheetDialogFragment()  {

    var disposable: Disposable? = null
    val createApiService by lazy {
        WorldDataApiService.ApiService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_debug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {

            debug_button_one.setOnClickListener {
                makeApiCall()
            }

            debug_button_two.setOnClickListener {

            }
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun makeApiCall(){
        disposable = createApiService.pullWorldData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Timber.d("JL_ success ${result}}")
                },
                { error ->
                    Timber.d("JL_ error: ${error.message}")
                }
            )
    }
}