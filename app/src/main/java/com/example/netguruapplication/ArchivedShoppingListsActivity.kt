package com.example.netguruapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_archived_shopping_lists.*
import kotlinx.android.synthetic.main.card_view_layout.view.*

class ArchivedShoppingListsActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener,
    RecyclerViewAdapter.OnLongItemClickListener {

    private lateinit var archivedList: ArrayList<ArchivedNotes>
    private lateinit var archive: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_shopping_lists)

        archive = Realm.getInstance(MyApp().archiveConfiguration())

        current_list_arc.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        recyclerArchivedView.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

        RealmOperations().getAllArchivedNotes(archive, recyclerArchivedView, this, this, this)
    }

    override fun onItemClick(position: Int) {
        MySharedPreferences(this).setIdValue(recyclerArchivedView[position].id_view.text.toString())
        MySharedPreferences(this).setTitleValue(recyclerArchivedView[position].title_view.text.toString())
        MySharedPreferences(this).setListValue(recyclerArchivedView[position].list_view.text.toString())

        startActivity(Intent(this, ArchivedNoteDetailsActivity::class.java))
        finish()
    }

    override fun onLongItemClick(position: Int) {
        val idInt: Int = recyclerArchivedView[position].id_view.text.toString().toInt()
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Are You sure ?")
            .setMessage("Do You want to delete shopping list ?")
            .setPositiveButton("Yes") { _, _ ->
                RealmOperations().delPositionArchive(archive, this, idInt)
                startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            }
            .setNegativeButton("No", null)
            .show()
    }
}
