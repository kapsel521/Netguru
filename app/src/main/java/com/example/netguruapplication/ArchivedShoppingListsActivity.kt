package com.example.netguruapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.card_view_layout.view.*

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
        MySharedPreferences(this).setIdValue(listRV[position].id_view.text.toString())
        MySharedPreferences(this).setTitleValue(listRV[position].title_view.text.toString())
        MySharedPreferences(this).setListValue(listRV[position].list_view.text.toString())

        startActivity(Intent(this, ArchivedNoteDetailsActivity::class.java))
        finish()
    }

    private fun getAllNotes() {
        archivedList = ArrayList()
        val resultsArchive: RealmResults<ArchivedNotes> = realm.where<ArchivedNotes>(ArchivedNotes::class.java).sort("id", Sort.DESCENDING).findAll()
        listRV.adapter = ArchiveRecyclerViewAdapter(this, resultsArchive, this)
        listRV.adapter!!.notifyDataSetChanged()
    }
}
