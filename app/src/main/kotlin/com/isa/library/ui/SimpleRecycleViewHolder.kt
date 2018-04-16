package com.ent.live.library.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.isa.app.R

open class SimpleRecycleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    val icon: ImageView = itemView!!.findViewById(R.id.icon)
    val title: TextView = itemView!!.findViewById(R.id.title)
    val subTitle: TextView = itemView!!.findViewById(R.id.subTitle)
    val indicator: ImageView = itemView!!.findViewById(R.id.indicator)

}