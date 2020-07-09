package com.example.netguruapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {

    private lateinit var addList: Button
    private lateinit var archivedLists: Button
    private lateinit var listRV: RecyclerView
    private lateinit var shopList: ArrayList<Notes>
    private lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        addList = findViewById(R.id.add_list)
        archivedLists = findViewById(R.id.archived_lists)
        listRV = findViewById(R.id.recyclerView)

        addList.setOnClickListener {
            startActivity(Intent(this, EditNoteActivity::class.java))
            finish()
        }
        archivedLists.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }

        listRV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

        getAllNotes()

    }

    private fun getAllNotes() {
        shopList = ArrayList()
        val results:RealmResults<Notes> = realm.where<Notes>(Notes::class.java).findAll()
        listRV.adapter = RecyclerViewAdapter(this, results)
        listRV.adapter!!.notifyDataSetChanged()
    }

}




