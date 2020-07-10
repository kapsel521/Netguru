package com.example.netguruapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.card_view_layout.view.*

class ArchiveRecyclerViewAdapter (
    private val context: Context?,
    private val archivedList: RealmResults<ArchivedNotes>,
    private val listener: RecyclerViewAdapter.OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return archivedList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.title_view.text = archivedList[position]!!.title
        holder.itemView.list_view.text = archivedList[position]!!.shopList
        holder.itemView.id_view.text = archivedList[position]!!.id.toString()
    }

    inner class ViewHolder(v: View?): RecyclerView.ViewHolder(v!!), View.OnClickListener{
        val title = itemView.title_view
        val list = itemView.list_view
        val id = itemView.id_view


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

}