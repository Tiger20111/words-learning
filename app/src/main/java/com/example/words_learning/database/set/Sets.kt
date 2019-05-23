package com.example.words_learning.database.set

import android.content.ContentValues
import android.content.Context
import com.example.words_learning.adapters.SpiritualTeacher
import com.example.words_learning.database.DBHelper
import com.example.words_learning.database.DBHelper.Companion.COLUMN_SET_SET
import com.example.words_learning.database.DBHelper.Companion.TABLE_SETS


class Sets(context : Context) {

    private val dbHelper = DBHelper(context)

    fun getAllElements(): ArrayList<Set>? {
        val sets = ArrayList<Set>()
        val data = dbHelper.getDictionary(TABLE_SETS)
        return if (data != null) {
            for (array in data) {
                sets.add(Set(array[0].toInt(), array[1], array[2], array[3]))
            }
            sets
        } else {
            null
        }
    }

    fun remove(set : Set) {
        dbHelper.removeElementID(TABLE_SETS, set.id)
    }

    fun findSet(set : Set) : Boolean {
        return dbHelper.findWord(TABLE_SETS, set.set)
    }

    fun removeSet(set: String) {
        return dbHelper.removeElementsByName(TABLE_SETS, COLUMN_SET_SET, set)
    }

    fun addValue(set: Set) {
        val values = ContentValues()
        values.put(DBHelper.COLUMN_SET_SET, set.set)
        values.put(DBHelper.COLUMN_SET_WORD, set.word)
        values.put(DBHelper.COLUMN_SET_TRANSLATION, set.traslation)

        dbHelper.addElement(TABLE_SETS, values)
    }

    fun newSet(name : String, sets : ArrayList<SpiritualTeacher>) {
        for (set in sets) {
            addValue(Set(-1, name, set.name, set.quote))
        }
    }


    fun getSetsNames() : ArrayList<String>?{
        return dbHelper.getDiffNames(TABLE_SETS, 1)
    }


}