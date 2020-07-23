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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_view_layout.*
import kotlinx.android.synthetic.main.card_view_layout.view.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener,
    RecyclerViewAdapter.OnLongItemClickListener {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        add_list.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
            finish()
        }
        archived_lists.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }

        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

        RealmOperations().getAllNotes(realm, recyclerView, this, this, this)

    }

    override fun onItemClick(position: Int) {
        MySharedPreferences(this).setIdValue(recyclerView[position].id_view.text.toString())
        MySharedPreferences(this).setTitleValue(recyclerView[position].title_view.text.toString())
        MySharedPreferences(this).setListValue(recyclerView[position].list_view.text.toString())
        startActivity(Intent(this, EditNoteActivity::class.java))
        finish()
    }

    override fun onLongItemClick(position: Int) {
        val idInt: Int = recyclerView[position].id_view.text.toString().toInt()
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Are You sure ?")
            .setMessage("Do You want to delete shopping list ?")
            .setPositiveButton("Yes") { _, _ ->
                RealmOperations().delPosition(this, realm, idInt)
                startActivity(Intent(this, MainActivity::class.java))
            }
            .setNegativeButton("No", null)
            .show()
    }
}





