package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_note.*
import java.lang.Exception

class EditNoteActivity : AppCompatActivity() {

    private lateinit var idED: EditText
    private lateinit var titleED: EditText
    private lateinit var listED: EditText
    private lateinit var savedListBtn: Button
    private lateinit var currentLists: Button
    private lateinit var archivedLists: Button
    private lateinit var saveToArchive: Button
    private lateinit var delBtn: Button
    private lateinit var realm: Realm
    private lateinit var realmArchive: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        idED = findViewById(R.id.id_ed)
        titleED = findViewById(R.id.title_edit_text)
        listED = findViewById(R.id.list_edit_text)
        savedListBtn = findViewById(R.id.save_list)
        currentLists = findViewById(R.id.current_lists)
        archivedLists = findViewById(R.id.archived_lists)
        saveToArchive = findViewById(R.id.save_list_to_archive)
        delBtn = findViewById(R.id.delete_button)
        realm = Realm.getDefaultInstance()
        realmArchive = Realm.getInstance(MyApp().archiveConfiguration())

        idED.setText(MySharedPreferences(this).getIdText())
        titleED.setText(MySharedPreferences(this).getTitleText())
        listED.setText(MySharedPreferences(this).getListText())

        savedListBtn.setOnClickListener {
            saveListToDB()
        }

        currentLists.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        archivedLists.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }

        saveToArchive.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to move list to archive ?")
                .setPositiveButton("Yes") { _, _ ->
                    delPosition(realm, idED.text.toString().toInt())
                    saveListToArchiveDB()
                    startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }

        delBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to delete shopping list ?")
                .setPositiveButton("Yes") { _, _ ->
                    delPosition(realm, idED.text.toString().toInt())
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun saveListToDB() {
        try {
            realm.beginTransaction()
            val notes = Notes()
            notes.title = titleED.text.toString()
            notes.shopList = listED.text.toString()
            notes.id = MySharedPreferences(this).getIdText()?.toInt()

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this,"List Edited Sucessfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }catch (e: Exception){
            Toast.makeText(this,"Error $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun delPosition(realm: Realm, id: Int): Boolean {
        return try {
            realm.beginTransaction()
            realm.where(Notes::class.java).equalTo("id", id)
                .findFirst()?.deleteFromRealm()
            realm.commitTransaction()

            Toast.makeText(this, "List deleted!: $id", Toast.LENGTH_SHORT).show()
            true
        }catch (e:Exception){
            Toast.makeText(this, "not this time $e", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun saveListToArchiveDB() {
        try {
            realmArchive.beginTransaction()
            val currentIdNumber:Number? = realm.where<ArchivedNotes>(ArchivedNotes::class.java).findAll().max("id")
            val nextID:Int
            nextID = if (currentIdNumber == null){
                0
            }else{
                currentIdNumber.toInt() + 1
            }

            val notes = ArchivedNotes()
            notes.title = titleED.text.toString()
            notes.shopList = listED.text.toString()
            notes.id = nextID
            realmArchive.copyToRealmOrUpdate(notes)
            realmArchive.commitTransaction()
            Toast.makeText(this,"List Added Sucessfully to archive", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }catch (e:Exception){
            Toast.makeText(this,"Error $e", Toast.LENGTH_LONG).show()
        }
    }


}
