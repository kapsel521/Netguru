package com.example.netguruapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_edit_note.view.*
import kotlinx.android.synthetic.main.card_view_layout.view.*

class RecyclerViewAdapter(private val context: Context?, private val shopList: RealmResults<Notes>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.title_view.text = shopList[position]!!.title
        holder.itemView.list_view.text = shopList[position]!!.shopList
        holder.itemView.id_view.text = shopList[position]!!.id.toString()
    }

    class ViewHolder(v:View?): RecyclerView.ViewHolder(v!!){
        val title = itemView.title_view
        val list = itemView.list_view
        val id = itemView.id_view

    }

}