package com.lloydsbyte.covidtracker.home.usa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.StateModel
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import kotlinx.android.synthetic.main.item_home.view.*

class UsaAdapter: RecyclerView.Adapter<UsaAdapter.UsaViewHolder>() {

    var fullListBU: List<StateModel> = emptyList()
    var adapterItems: List<StateModel> = emptyList()
    var onItemClicked: ((StateModel) -> Unit)? = null

    fun initAdapter(items: List<StateModel>, recyclerView: RecyclerView){
        //Sort the list
        fullListBU = items.sortedByDescending {
            it.increase
        }
        adapterItems = fullListBU
        refreshAdapter(recyclerView)
    }

    fun filterAdapter(filter: String, recyclerView: RecyclerView){
        adapterItems = if (filter.isEmpty()){
            fullListBU
        } else {
            val filteredItems = fullListBU.filter {
                it.stateName.toLowerCase().contains(filter.toLowerCase())
            }
            filteredItems
        }
        refreshAdapter(recyclerView)
    }

    private fun refreshAdapter(recyclerView: RecyclerView){
        val anim = AnimationUtils.loadLayoutAnimation(
            recyclerView.context,
            R.anim.recyclerview_animation
        )
        recyclerView.layoutAnimation = anim
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UsaViewHolder(inflater.inflate(R.layout.item_home, parent, false))
    }

    override fun getItemCount() = adapterItems.size

    override fun onBindViewHolder(holder: UsaViewHolder, position: Int) {
        holder.bind(adapterItems[position])
    }

    inner class UsaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(stateModel: StateModel) {
            itemView.apply {
                item_title.text = stateModel.stateName
                item_confirmed.text = AppUtilz.insertCommas(stateModel.totalConfirmed)
                item_deaths.text = AppUtilz.insertCommas(stateModel.totalDeaths)
                setOnClickListener {
                    onItemClicked?.invoke(stateModel)
                }
            }
        }
    }
}