package com.example.netguruapplication

import android.content.Context

class MySharedPreferences(context: Context) {
    val NOTE_ID_NAME = "IdTitle"
    val NOTE_ID_TEXT = "IdText"
    val NOTE_TITLE_NAME = "NoteTitle"
    val NOTE_TITLE_TEXT = "TitleText"
    val NOTE_LIST_NAME = "ListTitle"
    val NOTE_LIST_TEXT = "ListText"

    val preferenceId = context.getSharedPreferences(NOTE_ID_NAME, Context.MODE_PRIVATE)
    val preferenceTitle = context.getSharedPreferences(NOTE_TITLE_NAME, Context.MODE_PRIVATE)
    val preferenceList = context.getSharedPreferences(NOTE_LIST_NAME, Context.MODE_PRIVATE)

    fun getIdText() : String? {
        return preferenceId.getString(NOTE_ID_TEXT, "")
    }

    fun setIdValue(title: String){
        val editor = preferenceId.edit()
        editor.putString(NOTE_ID_TEXT, title)
        editor.apply()
    }

    fun getTitleText() : String? {
        return preferenceTitle.getString(NOTE_TITLE_TEXT, "")
    }

    fun setTitleValue(title: String){
        val editor = preferenceTitle.edit()
        editor.putString(NOTE_TITLE_TEXT, title)
        editor.apply()
    }

    fun getListText() : String? {
        return preferenceList.getString(NOTE_LIST_TEXT, "")
    }

    fun setListValue(title: String){
        val editor = preferenceList.edit()
        editor.putString(NOTE_LIST_TEXT, title)
        editor.apply()
    }
}