package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.netguruapplication.databinding.ActivityEditNoteBinding
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit_note.*

class EditNoteActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var archive: Realm
    private lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_edit_note)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note)

        realm = Realm.getDefaultInstance()
        archive = Realm.getInstance(MyApp().archiveConfiguration())

        binding.idEdEn.setText(MySharedPreferences(this).getIdText())
        binding.titleEditTextEn.setText(MySharedPreferences(this).getTitleText())
        binding.listEditTextEn.setText(MySharedPreferences(this).getListText())

        binding.saveListEn.setOnClickListener {
            RealmOperations().editListInDB(
                realm,
                this,
                title_edit_text_en.text.toString(),
                list_edit_text_en.text.toString(),
                id_ed_en.text.toString().toInt()
            )
        }

        binding.saveListToArchiveEn.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to move list to archive ?")
                .setPositiveButton("Yes") { _, _ ->
                    RealmOperations().delPosition(this, realm, id_ed_en.text.toString().toInt())
                    RealmOperations().saveListToArchiveDB(
                        archive,
                        this,
                        title_edit_text_en,
                        list_edit_text_en
                    )
                    startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }

        binding.currentListsEn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.archivedListsEn.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }


        binding.deleteButtonEn.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to delete shopping list ?")
                .setPositiveButton("Yes") { _, _ ->
                    RealmOperations().delPosition(this, realm, id_ed_en.text.toString().toInt())
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}
