package com.example.netguruapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.netguruapplication.databinding.ActivityArchivedNoteDetailsBinding
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_archived_note_details.*

class ArchivedNoteDetailsActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var archive: Realm
    private lateinit var binding: ActivityArchivedNoteDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_archived_note_details)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_archived_note_details)

        realm = Realm.getDefaultInstance()
        archive = Realm.getInstance(MyApp().archiveConfiguration())

        binding.idViewAnd.text = MySharedPreferences(this).getIdText()
        binding.titleViewAnd.text = MySharedPreferences(this).getTitleText()
        binding.listViewAnd.text = MySharedPreferences(this).getListText()
        binding.listViewAnd.movementMethod = ScrollingMovementMethod()

        binding.removeListAnd.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to remove list from archive?")
                .setPositiveButton("Yes") { _, _ ->
                    RealmOperations().delPositionArchive(archive, this, id_view_and.text.toString().toInt())
                    startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }

        binding.restoreListAnd.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You sure ?")
                .setMessage("Do You want to restore list ?")
                .setPositiveButton("Yes") { _, _ ->
                    RealmOperations().delPositionArchive(archive, this, id_view_and.text.toString().toInt())
                    RealmOperations().saveListToDB(realm, this, title_view_and.text.toString(), list_view_and.text.toString())
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }

        binding.currentListsAnd.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.archivedListsAnd.setOnClickListener {
            startActivity(Intent(this, ArchivedShoppingListsActivity::class.java))
            finish()
        }
    }
}
