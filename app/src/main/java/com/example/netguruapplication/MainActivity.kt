package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var addList: Button
    private lateinit var listRV: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addList = findViewById(R.id.addList)
        listRV = findViewById(R.id.recyclerView)

        addList.setOnClickListener {
            startActivity(Intent(this, EditNoteActivity::class.java))
            finish()
        }


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
