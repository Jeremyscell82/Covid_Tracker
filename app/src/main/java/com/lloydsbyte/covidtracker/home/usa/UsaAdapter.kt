package com.lloydsbyte.covidtracker.home.usa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.lloydsbyte.covidtracker.R
import kotlinx.android.synthetic.main.item_home.view.*

class UsaAdapter: RecyclerView.Adapter<UsaAdapter.UsaViewHolder>() {

    var adapterItems: List<UsaModel> = emptyList()
    var onItemClicked: ((UsaModel?) -> Unit)? = null

    fun initAdapter(items: List<UsaModel>, recyclerView: RecyclerView){
        adapterItems = items
        val anim = AnimationUtils.loadLayoutAnimation(recyclerView.context,
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

    override fun getItemCount() = 15//adapterItems.size

    override fun onBindViewHolder(holder: UsaViewHolder, position: Int) {
        holder.bind(null)//adapterItems[position])
    }

    inner class UsaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(usaModel: UsaModel?) {
            itemView.apply {
                item_title.text = "United States of America"
                item_confirmed.text = "1.89M"
                item_deaths.text = "444k"
                setOnClickListener {
                    onItemClicked?.invoke(usaModel)
                }
            }
        }
    }
}