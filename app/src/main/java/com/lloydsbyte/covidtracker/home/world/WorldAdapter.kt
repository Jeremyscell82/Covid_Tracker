package com.lloydsbyte.covidtracker.home.world

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.lloydsbyte.covidtracker.R
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import kotlinx.android.synthetic.main.item_home.view.*

class WorldAdapter: RecyclerView.Adapter<WorldAdapter.WorldViewHolder>() {

    var fullListBU: List<CountryModel> = emptyList()
    var adapterItems: List<CountryModel> = emptyList()
    var onItemClicked: ((CountryModel) -> Unit)? = null

    fun initAdapter(items: List<CountryModel>, recyclerView: RecyclerView){
        //Sort the list
        fullListBU = items.sortedByDescending {
            it.totalConfirmed
        }
        //Remove non-reporting/ zero count countries
        fullListBU.filter {
            it.totalConfirmed != 0
        }
        adapterItems = fullListBU
        refreshAdapter(recyclerView)
    }

    fun filterAdapter(filter: String, recyclerView: RecyclerView){
        adapterItems = if (filter.isEmpty()){
            fullListBU
        } else {
            val filteredItems = fullListBU.filter {
                it.countryName.toLowerCase().contains(filter.toLowerCase())
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WorldViewHolder(inflater.inflate(R.layout.item_home, parent, false))
    }

    override fun getItemCount() = adapterItems.size

    override fun onBindViewHolder(holder: WorldViewHolder, position: Int) {
        holder.bind(adapterItems[position])
    }

    inner class WorldViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(worldModel: CountryModel) {
            itemView.apply {
                item_title.text = worldModel.countryName
                item_confirmed.text = AppUtilz.insertCommas(worldModel.totalConfirmed)
                item_deaths.text = AppUtilz.insertCommas(worldModel.totalDeaths)
                setOnClickListener {
                    onItemClicked?.invoke(worldModel)
                }
            }
        }
    }

}