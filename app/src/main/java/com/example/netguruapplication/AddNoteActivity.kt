package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_add_note.*
import java.lang.Exception

class AddNoteActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var archive: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        realm = Realm.getDefaultInstance()
        archive = Realm.getInstance(MyApp().archiveConfiguration())

        current_lists_an.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        archived_lists_an.setOnClickListener{
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }
        save_list_to_archive_an.setOnClickListener{
            RealmOperations().saveListToArchiveDB(archive, this, title_edit_text_an, list_edit_text_an)
            finish()
        }

        save_list_an.setOnClickListener {
            RealmOperations().saveListToDB(realm, this, title_edit_text_an.text.toString(), list_edit_text_an.text.toString())
        }
    }
}
