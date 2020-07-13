package com.example.netguruapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.RealmResults

class ArchivedShoppingListsActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    private lateinit var currentList: Button
    private lateinit var listRV: RecyclerView
    private lateinit var archivedList: ArrayList<ArchivedNotes>
    private lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_shopping_lists)

        realm = Realm.getInstance(MyApp().archiveConfiguration())
        currentList = findViewById(R.id.currentList)
        listRV = findViewById(R.id.recyclerArchivedView)


        currentList.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        listRV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

        getAllNotes()
    }

    override fun onItemClick(position: Int) {
        startActivity(Intent(this, ArchivedNoteDetailsActivity::class.java))
        finish()
    }

    private fun getAllNotes() {
        archivedList = ArrayList()
        val resultsArchive: RealmResults<ArchivedNotes> = realm.where<ArchivedNotes>(ArchivedNotes::class.java).findAll()
        listRV.adapter = ArchiveRecyclerViewAdapter(this, resultsArchive, this)
        listRV.adapter!!.notifyDataSetChanged()
    }
}
