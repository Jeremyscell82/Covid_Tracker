package com.lloydsbyte.covidtracker.home.world

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.lloydsbyte.covidtracker.R
import kotlinx.android.synthetic.main.item_home.view.*

class WorldAdapter: RecyclerView.Adapter<WorldAdapter.WorldViewHolder>() {

    var adapterItems: List<WorldModel> = emptyList()
    var onItemClicked: ((WorldModel) -> Unit)? = null

    fun initAdapter(items: List<WorldModel>, recyclerView: RecyclerView){
        adapterItems = items
        val anim = AnimationUtils.loadLayoutAnimation(recyclerView.context,
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

    override fun getItemCount() = 15//adapterItems.size

    override fun onBindViewHolder(holder: WorldViewHolder, position: Int) {
//        holder.bind(adapterItems[position])
        holder.bind(null)
    }

    inner class WorldViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(worldModel: WorldModel?) {
            itemView.apply {
                item_title.text = "United States of America"
                item_confirmed.text = "1.89M"
                item_deaths.text = "444k"
                setOnClickListener {
//                    onItemClicked?.invoke(worldModel)
                }
            }
        }
    }

}