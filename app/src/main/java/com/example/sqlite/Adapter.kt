package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ContactsDataBase"
        const val TABLE_NAME = "Contacts"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_SURNAME = "surname"
        const val KEY_BIRTHDATA = "birthData"
        const val KEY_PHONENUMBER = "phoneNumber"
        const val KEY_IS_DONE = "is_done"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_NAME TEXT NOT NULL,
                $KEY_SURNAME TEXT NOT NULL,
                $KEY_BIRTHDATA TEXT NOT NULL,
                $KEY_PHONENUMBER TEXT NOT NULL,
                $KEY_IS_DONE INTEGER NOT NULL
            )""")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getContacts(): MutableList<Contact> {
        val result = mutableListOf<Contact>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val name_id: Int = cursor.getColumnIndex(KEY_NAME)
            val surname_id: Int = cursor.getColumnIndex(KEY_SURNAME)
            val birthData_id: Int = cursor.getColumnIndex(KEY_BIRTHDATA)
            val phoneNumber_id: Int = cursor.getColumnIndex(KEY_PHONENUMBER)
            val isDoneIndex: Int = cursor.getColumnIndex(KEY_IS_DONE)
            do {
                val contact = Contact(
                    cursor.getInt(idIndex),
                    cursor.getString(name_id),
                    cursor.getString(surname_id),
                    cursor.getString(birthData_id),
                    cursor.getString(phoneNumber_id),
                    cursor.getInt(isDoneIndex) == 1
                )
                result.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun addContact(name: String,surname: String,birthData: String,phoneNumber: String, isDone: Boolean = false) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_SURNAME, surname)
        contentValues.put(KEY_BIRTHDATA, birthData)
        contentValues.put(KEY_PHONENUMBER, phoneNumber)
        contentValues.put(KEY_IS_DONE, if (isDone) 1 else 0)
        database.insert(TABLE_NAME, null, contentValues)
        close()
    }

    fun updateContact(id: Int, name: String,surname: String,birthData: String,phoneNumber: String, isDone: Boolean) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_SURNAME, surname)
        contentValues.put(KEY_BIRTHDATA, birthData)
        contentValues.put(KEY_PHONENUMBER, phoneNumber)
        contentValues.put(KEY_IS_DONE, if (isDone) 1 else 0)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun removeContact(id: Int) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun removeAllContacts() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, null, null)
        close()
    }
}