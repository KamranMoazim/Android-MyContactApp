package com.example.mycontactapp.dbhelper

// DatabaseHelper.kt
// Implement SQLiteOpenHelper for database operations

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "contacts.db"
        // Define the table and columns
        const val TABLE_CONTACTS = "contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE_NUMBER = "phone_number"
    }

    // Create table query
    private val CREATE_CONTACTS_TABLE = ("CREATE TABLE $TABLE_CONTACTS ("
            + "$COLUMN_ID INTEGER PRIMARY KEY,"
            + "$COLUMN_NAME TEXT,"
            + "$COLUMN_PHONE_NUMBER TEXT)")

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }
}
