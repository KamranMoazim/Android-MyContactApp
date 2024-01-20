package com.example.mycontactapp.repos

// ContactRepository.kt

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.mycontactapp.data.Contact
import com.example.mycontactapp.dbhelper.DatabaseHelper

class ContactRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertContact(contact: Contact): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, contact.name)
            put(DatabaseHelper.COLUMN_PHONE_NUMBER, contact.phoneNumber)
        }
        return db.insert(DatabaseHelper.TABLE_CONTACTS, null, values)
    }

    fun updateContact(contact: Contact): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, contact.name)
            put(DatabaseHelper.COLUMN_PHONE_NUMBER, contact.phoneNumber)
        }
        return db.update(
            DatabaseHelper.TABLE_CONTACTS,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(contact.id.toString())
        )
    }

    fun deleteContact(contactId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_CONTACTS,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(contactId.toString())
        )
    }

    fun getContactById(contactId: Int): Contact? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_CONTACTS,
            null,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(contactId.toString()),
            null,
            null,
            null
        )

        var contact: Contact? = null

        if (cursor.moveToFirst()) {
            val idColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)
            val nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)
            val phoneNumberColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER)

            // Check if the column indices are valid
            if (idColumnIndex >= 0 && nameColumnIndex >= 0 && phoneNumberColumnIndex >= 0) {
                val id = cursor.getInt(idColumnIndex)
                val name = cursor.getString(nameColumnIndex)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)
                contact = Contact(id, name, phoneNumber)
            } else {
                // Handle the case where one or more columns are not found
                // You might want to log an error or handle it in another appropriate way
            }
        }

        cursor.close()
        return contact
    }


    fun getAllContacts(): List<Contact> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_CONTACTS} ORDER BY ${DatabaseHelper.COLUMN_NAME}", null)
        val contacts = mutableListOf<Contact>()

        while (cursor.moveToNext()) {

            val idColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)
            val nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)
            val phoneNumberColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER)

            // Check if the column indices are valid
            if (idColumnIndex >= 0 && nameColumnIndex >= 0 && phoneNumberColumnIndex >= 0) {
                val id = cursor.getInt(idColumnIndex)
                val name = cursor.getString(nameColumnIndex)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)
                contacts.add(Contact(id, name, phoneNumber))
            } else {
                // Handle the case where one or more columns are not found
                // You might want to log an error or handle it in another appropriate way
            }
        }

        cursor.close()
        return contacts
    }

    fun searchContact(query: String): List<Contact> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_CONTACTS} WHERE " +
                    "${DatabaseHelper.COLUMN_NAME} LIKE '%$query%' OR " +
                    "${DatabaseHelper.COLUMN_PHONE_NUMBER} LIKE '%$query%'" +
                    "ORDER BY ${DatabaseHelper.COLUMN_NAME}",
            null
        )
        val contacts = mutableListOf<Contact>()

        while (cursor.moveToNext()) {
            val idColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)
            val nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)
            val phoneNumberColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER)

            // Check if the column indices are valid
            if (idColumnIndex >= 0 && nameColumnIndex >= 0 && phoneNumberColumnIndex >= 0) {
                val id = cursor.getInt(idColumnIndex)
                val name = cursor.getString(nameColumnIndex)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)
                contacts.add(Contact(id, name, phoneNumber))
            } else {
                // Handle the case where one or more columns are not found
                // You might want to log an error or handle it in another appropriate way
            }
        }

        cursor.close()
        return contacts
    }
}
