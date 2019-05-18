package com.example.words_learning.database


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteCursorDriver
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQuery


class DBHelper(context: Context,
               name: String = DATABASE_NAME,
               factory: SQLiteDatabase.CursorFactory = Factory(),
               version: Int = DATABASE_VERSION
) : SQLiteOpenHelper(context, name, factory, version) {

    private class Factory : SQLiteDatabase.CursorFactory {
        override fun newCursor(sqLiteDatabase: SQLiteDatabase, sqLiteCursorDriver: SQLiteCursorDriver, s: String?, sqLiteQuery: SQLiteQuery): Cursor {
            return SQLiteCursor(sqLiteCursorDriver, s, sqLiteQuery)
        }
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_WORDS_TABLE)
        sqLiteDatabase.execSQL(CREATE_SETS_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, j: Int) {
        //val db = writableDatabase
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY)
    }

    fun clearElement() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DICTIONARY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SETS")
    }
    fun addElement(table : String, values: ContentValues) {
        val db = this.writableDatabase

        db.insert(table, null, values)
        db.close()
    }

    fun removeElementID(table : String, id: Int) {
        val db = writableDatabase
        db.delete(table, "$COLUMN_TABLE_ID=?", arrayOf((id).toString())).toLong()
    }

    fun removeElementWord(table: String, name: String) {
        val db = writableDatabase
        db.delete(table, "$COLUMN_TABLE_WORD=?", arrayOf((name))).toLong()

    }

    @SuppressLint("Recycle")
    fun findWord(table: String, name : String) : Boolean {
        val db = readableDatabase

        val selectQuery = "SELECT * FROM $table WHERE $COLUMN_TABLE_WORD = '$name'"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.let {
                return (cursor.count > 0)
            }
        return false
    }


    fun getDictionary() : ArrayList<ArrayList<String>>? {
        val db = readableDatabase

        val selectQuery = "SELECT  * FROM $TABLE_DICTIONARY"
        val allData = ArrayList<ArrayList<String>>()

        val cursor = db.rawQuery(selectQuery, null)
        cursor?.let {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                var numRole = 0

                    do {
                    val data = ArrayList<String>()
                    var i = 0
                    while ( i < cursor.columnCount) {
                        data.add(cursor.getString(i))
                        i++
                    }
                    allData.add(data)
                    numRole += cursor.columnCount
                    //cursor.moveToNext()
                } while (cursor.moveToNext())
                cursor.close()
                return allData
            }
            cursor.close()
        }

        return null
    }

    fun getCursor(table: String) : Cursor? {
        val db = readableDatabase
        val selectQuery = "SELECT  * FROM $table"
        val cursor = db.rawQuery(selectQuery, null)
        return cursor
    }


    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "MyDB.db"

        const val TABLE_DICTIONARY = "dictionary"

        const val COLUMN_TABLE_ID = "table_id"
        const val COLUMN_TABLE_WORD = "table_word"
        const val COLUMN_TABLE_TRANSLATION = "table_translation"

        const val TABLE_SETS = "sets"
        const val COLUMN_SET_ID = "set_id"
        const val COLUMN_SET_SET = "set_set"
        const val COLUMN_SET_WORD = "set_word"
        const val COLUMN_SET_TRANSLATION = "set_translation"

        const val CREATE_WORDS_TABLE = ("CREATE TABLE "
                + TABLE_DICTIONARY + "("
                + COLUMN_TABLE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TABLE_WORD + " TEXT,"
                + COLUMN_TABLE_TRANSLATION + " TEXT" + ");")


        const val CREATE_SETS_TABLE = ("CREATE TABLE "
                + TABLE_SETS + "("
                + COLUMN_SET_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_SET_SET + " TEXT,"
                + COLUMN_SET_WORD + " TEXT,"
                + COLUMN_SET_TRANSLATION + " TEXT" + ");")
    }

}


//for (name in cursor.getString(0)) {
//    data.add(name)
//}