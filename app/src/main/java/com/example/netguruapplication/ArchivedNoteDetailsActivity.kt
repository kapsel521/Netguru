package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.scaleMatrix
import io.realm.Realm
import java.lang.Exception

class ArchivedNoteDetailsActivity : AppCompatActivity() {

    private lateinit var idTV: TextView
    private lateinit var titleTV: TextView
    private lateinit var listTV: TextView
    private lateinit var removeList: Button
    private lateinit var restoreList: Button
    private lateinit var currentLists: Button
    private lateinit var archivedLists: Button
    private lateinit var realm: Realm
    private lateinit var realmArchive: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_note_details)

        idTV = findViewById(R.id.id_view)
        titleTV = findViewById(R.id.title_view)
        listTV = findViewById(R.id.list_view)
        removeList = findViewById(R.id.remove_list)
        restoreList = findViewById(R.id.restore_list)
        currentLists = findViewById(R.id.current_lists)
        archivedLists = findViewById(R.id.archived_lists)
        realm = Realm.getDefaultInstance()
        realmArchive = Realm.getInstance(MyApp().archiveConfiguration())

        idTV.text = MySharedPreferences(this).getIdText()
        titleTV.text = MySharedPreferences(this).getTitleText()
        listTV.text = MySharedPreferences(this).getListText()
        listTV.movementMethod = ScrollingMovementMethod()

        removeList.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to remove list from archive?")
                .setPositiveButton("Yes") { _, _ ->
                    delPositionArchive(realmArchive, idTV.text.toString().toInt())
                    startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }

        restoreList.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to restore list ?")
                .setPositiveButton("Yes") { _, _ ->
                    delPositionArchive(realmArchive, idTV.text.toString().toInt())
                    saveListToDB()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }

        currentLists.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        archivedLists.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }
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

    private fun saveListToDB() {
        try {
            realm.beginTransaction()
            val currentIdNumber:Number? = realm.where<Notes>(Notes::class.java).findAll().max("id")
            val nextID:Int
            nextID = if (currentIdNumber == null){
                0
            }else{
                currentIdNumber.toInt() + 1
            }
            val notes = Notes()
            notes.title = titleTV.text.toString()
            notes.shopList = listTV.text.toString()
            notes.id = nextID

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this, "List restored sucessfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error $e", Toast.LENGTH_SHORT).show()
        }
    }
}
