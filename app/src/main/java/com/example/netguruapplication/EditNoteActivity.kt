package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        idED = findViewById(R.id.id_ed)
        titleED = findViewById(R.id.title_edit_text)
        listED = findViewById(R.id.list_edit_text)
        savedListBtn = findViewById(R.id.save_list)
        currentLists = findViewById(R.id.current_lists)
        archivedLists = findViewById(R.id.archived_lists)

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

        realm = Realm.getDefaultInstance()
    }

    private fun saveListToDB() {
        try {
            realm.beginTransaction()
            val currentIdNumber:Number? = realm.where<Notes>(Notes::class.java).findAll().count()
            val nextID:Int
            nextID = if (currentIdNumber == null){
                0
            }else{
                currentIdNumber.toInt() + 1
            }
            val notes = Notes()
            notes.title = titleED.text.toString()
            notes.shopList = listED.text.toString()
            notes.id = nextID

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this,"List Edited Sucessfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }catch (e: Exception){
            Toast.makeText(this,"Error $e", Toast.LENGTH_SHORT).show()
        }
    }


}
