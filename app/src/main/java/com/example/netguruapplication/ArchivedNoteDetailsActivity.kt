package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ArchivedNoteDetailsActivity : AppCompatActivity() {

    private lateinit var removeList: Button
    private lateinit var currentLists: Button
    private lateinit var archivedLists: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_note_details)

        removeList = findViewById(R.id.remove_list)
        currentLists = findViewById(R.id.current_lists)
        archivedLists = findViewById(R.id.archived_lists)

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
