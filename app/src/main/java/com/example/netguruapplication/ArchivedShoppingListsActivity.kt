package com.example.netguruapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class ArchivedShoppingListsActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    private lateinit var currentList: Button
    private lateinit var shopList: ArrayList<ArchivedNotes>
    private lateinit var archive: Realm
    private lateinit var listRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_shopping_lists)

        currentList = findViewById(R.id.currentList)
        listRV = findViewById(R.id.recyclerArchivedView)

        val archiveConfig = RealmConfiguration.Builder()
            .name("ArchivedList.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        archive = Realm.getInstance(archiveConfig)

        currentList.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        getAllNotes()
    }

    override fun onItemClick(position: Int) {
        startActivity(Intent(this, ArchivedNoteDetailsActivity::class.java))
        finish()
    }

    private fun getAllNotes() {
        shopList = ArrayList()
        val resultsArchive: RealmResults<ArchivedNotes> = archive.where<ArchivedNotes>(ArchivedNotes::class.java).findAll()
        listRV.adapter = ArchiveRecyclerViewAdapter(this, resultsArchive, this)
        listRV.adapter!!.notifyDataSetChanged()
    }
}
