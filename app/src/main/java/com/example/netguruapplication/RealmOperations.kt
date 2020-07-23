package com.example.netguruapplication

import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.lang.Exception

class RealmOperations {

    fun delPosition(context: Context, realm: Realm, id: Int): Boolean {
        return try {
            realm.beginTransaction()
            realm.where(Notes::class.java).equalTo("id", id)
                .findFirst()?.deleteFromRealm()
            realm.commitTransaction()

            Toast.makeText(context, "deleted!: $id", Toast.LENGTH_SHORT).show()
            true
        } catch (e: Exception) {
            Toast.makeText(context, "not this time $e", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun delPositionArchive(archive: Realm, context: Context, id: Int): Boolean {
        return try {
            archive.beginTransaction()
            archive.where(ArchivedNotes::class.java).equalTo("id", id)
                .findFirst()?.deleteFromRealm()
            archive.commitTransaction()
            true
        } catch (e: Exception) {
            Toast.makeText(context, "not this time $e", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun getAllNotes(
        realm: Realm,
        recyclerView: RecyclerView,
        context: Context,
        listener: RecyclerViewAdapter.OnItemClickListener,
        listenerLong: RecyclerViewAdapter.OnLongItemClickListener
    ) {
        val results: RealmResults<Notes> =
            realm.where<Notes>(Notes::class.java).sort("id", Sort.DESCENDING).findAll()
        recyclerView.adapter = RecyclerViewAdapter(context, results, listener, listenerLong)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    fun getAllArchivedNotes(
        archive: Realm,
        recyclerView: RecyclerView,
        context: Context,
        listener: RecyclerViewAdapter.OnItemClickListener,
        listenerLong: RecyclerViewAdapter.OnLongItemClickListener
    ) {
        val resultsArchive: RealmResults<ArchivedNotes> =
            archive.where<ArchivedNotes>(ArchivedNotes::class.java).sort("id", Sort.DESCENDING)
                .findAll()
        recyclerView.adapter =
            ArchiveRecyclerViewAdapter(context, resultsArchive, listener, listenerLong)
        recyclerView.adapter!!.notifyDataSetChanged()
    }


    fun saveListToDB(realm: Realm, context: Context, titleED: String, listED: String) {
        try {
            realm.beginTransaction()
            val currentIdNumber: Number? = realm.where<Notes>(Notes::class.java).findAll().max("id")
            val nextID: Int
            nextID = if (currentIdNumber == null) {
                0
            } else {
                currentIdNumber.toInt() + 1
            }

            val notes = Notes()
            notes.title = titleED
            notes.shopList = listED
            notes.id = nextID

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()
            Toast.makeText(context, "List Added Sucessfully", Toast.LENGTH_SHORT).show()

            startActivity(context, Intent(context, MainActivity::class.java), null)

        } catch (e: Exception) {
            Toast.makeText(context, "Error $e", Toast.LENGTH_SHORT).show()
        }
    }

    fun editListInDB(realm: Realm, context: Context, titleED: String, listED: String, id: Int) {
        try {
            realm.beginTransaction()

            val notes = Notes()
            notes.title = titleED
            notes.shopList = listED
            notes.id = id

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()
            Toast.makeText(context, "List Added Sucessfully", Toast.LENGTH_SHORT).show()

            startActivity(context, Intent(context, MainActivity::class.java), null)

        } catch (e: Exception) {
            Toast.makeText(context, "Error $e", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveListToArchiveDB(
        archive: Realm,
        context: Context,
        titleED: EditText,
        listED: EditText
    ) {
        try {
            archive.beginTransaction()
            val currentIdNumber: Number? =
                archive.where<ArchivedNotes>(ArchivedNotes::class.java).findAll().max("id")
            val nextID: Int
            nextID = if (currentIdNumber == null) {
                0
            } else {
                currentIdNumber.toInt() + 1
            }

            val notes = ArchivedNotes()
            notes.title = titleED.text.toString()
            notes.shopList = listED.text.toString()
            notes.id = nextID

            archive.copyToRealmOrUpdate(notes)
            archive.commitTransaction()

            Toast.makeText(context, "List Added Sucessfully to archive", Toast.LENGTH_SHORT).show()

            startActivity(context, Intent(context, ArchivedShoppingListsActivity::class.java), null)

        } catch (e: Exception) {
            Toast.makeText(context, "Error $e", Toast.LENGTH_LONG).show()
        }
    }
}