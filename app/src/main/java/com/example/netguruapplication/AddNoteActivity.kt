package com.example.netguruapplication

import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.netguruapplication.databinding.ActivityAddNoteBinding
import com.example.netguruapplication.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_add_note.*
import java.lang.Exception

class AddNoteActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var archive: Realm
    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_note)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        realm = Realm.getDefaultInstance()
        archive = Realm.getInstance(MyApp().archiveConfiguration())

        binding.currentListsAn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.archivedListsAn.setOnClickListener{
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }
        binding.saveListToArchiveAn.setOnClickListener{
            RealmOperations().saveListToArchiveDB(archive, this, title_edit_text_an, list_edit_text_an)
            finish()
        }

        binding.saveListAn.setOnClickListener {
            RealmOperations().saveListToDB(realm, this, title_edit_text_an.text.toString(), list_edit_text_an.text.toString())
        }
    }
}
