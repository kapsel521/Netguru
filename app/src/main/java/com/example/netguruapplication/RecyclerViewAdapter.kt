package com.example.netguruapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.card_view_layout.view.*

class RecyclerViewAdapter(
    private val context: Context?,
    private val shopList: RealmResults<Notes>,
    private val listener: OnItemClickListener,
    private val listenerLong: OnLongItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    inner class ViewHolder(v:View?): RecyclerView.ViewHolder(v!!), View.OnClickListener,
    View.OnLongClickListener{
        val title = itemView.title_view
        val list = itemView.list_view
        val id = itemView.id_view


        init {
            itemView.setOnClickListener(this)
        }
        init{
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listenerLong.onLongItemClick(position)
                return true
            }
            return false
        }


    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnLongItemClickListener{
        fun onLongItemClick(position: Int)
    }

}