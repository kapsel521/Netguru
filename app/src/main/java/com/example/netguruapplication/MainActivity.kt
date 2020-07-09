package com.example.netguruapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exampleList = generateDummyList(500)

        recyclerView.adapter = RecyclerViewAdapter(exampleList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    private fun generateDummyList(size: Int): List<CardDataClass>{
        val list = ArrayList<CardDataClass>()

        for(i in 0 until size){
            val item = CardDataClass("Item $i","Line2")
            list += item
        }
        return list
    }
}
