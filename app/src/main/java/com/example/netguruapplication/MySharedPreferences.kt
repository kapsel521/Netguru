package com.example.netguruapplication

import android.content.Context

class MySharedPreferences(context: Context) {
    val NOTE_TITLE_NAME = "NoteTitle"
    val NOTE_TITLE_TEXT = "TitleText"

    val preference = context.getSharedPreferences(NOTE_TITLE_NAME, Context.MODE_PRIVATE)

    fun getTitleText() : String? {
        return preference.getString(NOTE_TITLE_TEXT, "Dziadostwo")
    }

    fun setTitleValue(title: String){
        val editor = preference.edit()
        editor.putString(NOTE_TITLE_TEXT, title)
        editor.apply()
    }
}