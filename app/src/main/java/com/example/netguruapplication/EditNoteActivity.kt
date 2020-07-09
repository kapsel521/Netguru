package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresPermission
import io.realm.Realm
import java.lang.Exception

class EditNoteActivity : AppCompatActivity() {

    private lateinit var titleED: EditText
    private lateinit var idED: EditText
    private lateinit var listED: EditText
    private lateinit var saveListBtn: Button
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        realm = Realm.getDefaultInstance()
        titleED = findViewById(R.id.title_edit_text)
        idED = findViewById(R.id.id)
        listED = findViewById(R.id.list_edit_text)
        saveListBtn = findViewById(R.id.save_list)

        saveListBtn.setOnClickListener {
            saveListToDB()
        }
    }

    private fun saveListToDB() {

        try {
            realm.beginTransaction()
            val currentIdNumber:Number? = realm.where(Notes::class.java).max("id")
            val nextID:Int
            nextID = if (currentIdNumber == null){
                1
            }else{
                currentIdNumber.toInt() + 1
            }

            val notes = Notes()
            notes.title = titleED.text.toString()
            notes.shopList = listED.text.toString()
            notes.id = nextID

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this,"List Added Sucessfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }catch (e:Exception){
            Toast.makeText(this,"Error $e", Toast.LENGTH_SHORT).show()
        }

    }
}
