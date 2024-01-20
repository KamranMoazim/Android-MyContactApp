package com.example.mycontactapp.utils

import android.content.Intent
import com.example.mycontactapp.data.Contact

object MyUtils {

    fun saveContactToIntent(intent: Intent, contact: Contact) {
        // Save contact details to the intent
        intent.putExtra("id", contact.id)
        intent.putExtra("name", contact.name)
        intent.putExtra("phoneNumber", contact.phoneNumber)
    }

    fun getContactFromIntent(intent: Intent): Contact? {
        // Retrieve contact details from the intent
        val contactId = intent.getIntExtra("id", -1)
        val contactName = intent.getStringExtra("name")
        val contactPhoneNumber = intent.getStringExtra("phoneNumber")

        // Check if all required data is available
        if (contactId != -1 && !contactName.isNullOrEmpty() && !contactPhoneNumber.isNullOrEmpty()) {
            return Contact(contactId, contactName, contactPhoneNumber)
        }

        return null
    }

    fun getSubStr(input: String, size:Int): String {
        return if (input.length <= size) {
            input
        } else {
            input.substring(0, size) + "..."
        }
    }

}
