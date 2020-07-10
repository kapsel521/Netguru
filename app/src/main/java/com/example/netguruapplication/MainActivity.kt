package com.example.netguruapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.RealmResults

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnLongItemClickListener {

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

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "item clicked: $position", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, EditNoteActivity::class.java))
        finish()
    }

    override fun onLongItemClick(position: Int) {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Are You sure ?")
            .setMessage("Do You want to delete shopping list ?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                shopList.removeAt(position)
                listRV.adapter?.notifyDataSetChanged()
            })
            .setNegativeButton("No", null)
            .show()
        Toast.makeText(this, "item deleted: $position", Toast.LENGTH_SHORT).show()

    }

    private fun getAllNotes() {
        shopList = ArrayList()
        val results:RealmResults<Notes> = realm.where<Notes>(Notes::class.java).findAll()
        listRV.adapter = RecyclerViewAdapter(this, results,  this, this)
        listRV.adapter!!.notifyDataSetChanged()
    }

    fun getRVsize():Int = shopList.size

}





