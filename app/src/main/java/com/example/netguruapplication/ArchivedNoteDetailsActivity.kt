package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ArchivedNoteDetailsActivity : AppCompatActivity() {

    private lateinit var idTV: TextView
    private lateinit var titleTV: TextView
    private lateinit var listTV: TextView
    private lateinit var removeList: Button
    private lateinit var currentLists: Button
    private lateinit var archivedLists: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_note_details)

        idTV = findViewById(R.id.id_view)
        titleTV = findViewById(R.id.title_view)
        listTV = findViewById(R.id.list_view)
        removeList = findViewById(R.id.remove_list)
        currentLists = findViewById(R.id.current_lists)
        archivedLists = findViewById(R.id.archived_lists)

        idTV.text = MySharedPreferences(this).getIdText()
        titleTV.text = MySharedPreferences(this).getTitleText()
        listTV.text = MySharedPreferences(this).getListText()

        currentLists.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        archivedLists.setOnClickListener{
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }
    }
}
