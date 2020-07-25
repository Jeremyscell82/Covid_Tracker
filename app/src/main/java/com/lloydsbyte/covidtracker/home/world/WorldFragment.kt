package com.lloydsbyte.covidtracker.home.world

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lloydsbyte.covidtracker.R
import kotlinx.android.synthetic.main.fragment_world.view.*

class WorldFragment: Fragment() {

    lateinit var worldAdapter: WorldAdapter

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

                }
            }
        }
    }
}