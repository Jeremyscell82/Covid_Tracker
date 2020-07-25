package com.lloydsbyte.covidtracker.home.usa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lloydsbyte.covidtracker.R
import kotlinx.android.synthetic.main.fragment_usa.view.*

class UsaFragment: Fragment() {

    lateinit var usaAdapter: UsaAdapter

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
            usa_recyclerview.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = usaAdapter
                usaAdapter.onItemClicked = {

                }
            }
        }
    }
}