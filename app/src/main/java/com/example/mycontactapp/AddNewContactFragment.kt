package com.example.mycontactapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mycontactapp.data.Contact
import com.example.mycontactapp.repos.ContactRepository


class AddNewContactFragment : Fragment() {

    private lateinit var contactRepository: ContactRepository

    private lateinit var userContactName:EditText
    private lateinit var contactUserNumber:EditText
    private lateinit var addContactBtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactRepository = ContactRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_new_contact, container, false)


        userContactName = view.findViewById(R.id.user_contact_name)
        contactUserNumber = view.findViewById(R.id.contact_user_number)
        addContactBtn = view.findViewById(R.id.add_contact_btn)


        addContactBtn.setOnClickListener {
            val contactName = userContactName.text.toString()
            val contactNumber = contactUserNumber.text.toString()

            if (contactName.isNotEmpty() && contactNumber.isNotEmpty()) {
                // Create a Contact object
                val newContact = Contact(id = 0, name = contactName, phoneNumber = contactNumber)

                // Insert the new contact into the database
                val insertedContactId = contactRepository.insertContact(newContact)

                if (insertedContactId != -1L) {
                    // Contact added successfully
                    Toast.makeText(requireContext(), "Contact added successfully", Toast.LENGTH_SHORT).show()

                    // Clear the input fields
                    userContactName.text.clear()
                    contactUserNumber.text.clear()
                } else {
                    // Handle the case where the contact couldn't be added
                    Toast.makeText(requireContext(), "Failed to add contact", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle the case where either the name or the number is empty
                Toast.makeText(requireContext(), "Please provide both name and number", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}