package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_add_note.*

class EditNoteActivity : AppCompatActivity() {

    private lateinit var titleED: EditText
    private lateinit var idED: EditText
    private lateinit var listED: EditText
    private lateinit var savedListBtn: Button
    private lateinit var currentLists: Button
    private lateinit var archivedLists: Button
    private lateinit var saveToArchive: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        titleED = findViewById(R.id.title_edit_text)
        idED = findViewById(R.id.id_ed)
        listED = findViewById(R.id.list_edit_text)
        currentLists = findViewById(R.id.current_lists)
        archivedLists = findViewById(R.id.archived_lists)

        titleED.setText(MySharedPreferences(this).getTitleText())

        currentLists.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        archivedLists.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }
    }
}
