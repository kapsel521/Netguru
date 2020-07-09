package com.example.netguruapplication

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view_layout.view.*

class RecyclerViewAdapter(private val exampleList: List<CardDataClass>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.textView1!!.text = currentItem.text1
        holder.textView2!!.text = currentItem.text2
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
         val textView1: TextView? = itemView.text_view_1
         val textView2: TextView? = itemView.text_view_2
    }
}