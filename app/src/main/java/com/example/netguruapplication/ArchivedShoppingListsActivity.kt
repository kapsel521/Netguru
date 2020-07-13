package com.example.netguruapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.card_view_layout.view.*
import java.lang.Exception

class ArchivedShoppingListsActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener,
    RecyclerViewAdapter.OnLongItemClickListener {

    private lateinit var currentList: Button
    private lateinit var listRV: RecyclerView
    private lateinit var archivedList: ArrayList<ArchivedNotes>
    private lateinit var realmArchive: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_shopping_lists)

        realmArchive = Realm.getInstance(MyApp().archiveConfiguration())
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

    override fun onLongItemClick(position: Int) {
        val idInt: Int = listRV[position].id_view.text.toString().toInt()
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Are You sure ?")
            .setMessage("Do You want to delete shopping list ?")
            .setPositiveButton("Yes") { _, _ ->
                delPositionArchive(realmArchive, idInt)
                startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun getAllNotes() {
        archivedList = ArrayList()
        val resultsArchive: RealmResults<ArchivedNotes> =
            realmArchive.where<ArchivedNotes>(ArchivedNotes::class.java).sort("id", Sort.DESCENDING)
                .findAll()
        listRV.adapter = ArchiveRecyclerViewAdapter(this, resultsArchive, this, this)
        listRV.adapter!!.notifyDataSetChanged()
    }

    private fun delPositionArchive(realmArchive: Realm, id: Int): Boolean {
        return try {
            realmArchive.beginTransaction()
            realmArchive.where(ArchivedNotes::class.java).equalTo("id", id)
                .findFirst()?.deleteFromRealm()
            realmArchive.commitTransaction()
            true
        } catch (e: Exception) {
            Toast.makeText(this, "not this time $e", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
