package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.card_view_layout.*
import kotlinx.android.synthetic.main.card_view_layout.view.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener,
    RecyclerViewAdapter.OnLongItemClickListener {

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
            startActivity(Intent(this, AddNoteActivity::class.java))
            finish()
        }
        archivedLists.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }

        listRV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

        getAllNotes()

    }

    override fun onItemClick(position: Int) {
        MySharedPreferences(this).setIdValue(listRV[position].id_view.text.toString())
        MySharedPreferences(this).setTitleValue(listRV[position].title_view.text.toString())
        MySharedPreferences(this).setListValue(listRV[position].list_view.text.toString())

        val idInt = MySharedPreferences(this).getIdText()
        Toast.makeText(this, "item clicked: $idInt", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, EditNoteActivity::class.java))
        finish()
    }

    override fun onLongItemClick(position: Int) {
        val idInt: Int = listRV[position].id_view.text.toString().toInt()
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Are You sure ?")
            .setMessage("Do You want to delete shopping list ?")
            .setPositiveButton("Yes") { _, _ ->
                delPosition(realm, idInt)
                startActivity(Intent(this, MainActivity::class.java))
            }
            .setNegativeButton("No", null)
            .show()
        Toast.makeText(this, "item selected: $position", Toast.LENGTH_SHORT).show()

    }

    private fun getAllNotes() {
        shopList = ArrayList()
        val results: RealmResults<Notes> = realm.where<Notes>(Notes::class.java).sort("id", Sort.DESCENDING).findAll()
        listRV.adapter = RecyclerViewAdapter(this, results, this, this)
        listRV.adapter!!.notifyDataSetChanged()
    }

    private fun delPosition(realm: Realm, id: Int): Boolean {
        return try {
            realm.beginTransaction()
            realm.where(Notes::class.java).equalTo("id", id)
                .findFirst()?.deleteFromRealm()
            realm.commitTransaction()

            Toast.makeText(this, "deleted!: $id", Toast.LENGTH_SHORT).show()
            true
        }catch (e:Exception){
            Toast.makeText(this, "not this time $e", Toast.LENGTH_SHORT).show()
            false
        }
    }

}





