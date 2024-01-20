package com.example.mycontactapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mycontactapp.AddNewContactFragment
import com.example.mycontactapp.R
import com.example.mycontactapp.UpdateContactActivity
import com.example.mycontactapp.data.Contact
import com.example.mycontactapp.repos.ContactRepository
import com.example.mycontactapp.utils.MyUtils

class ContactAdapter(private val context: Context, private var contacts: List<Contact>) : BaseAdapter() {

    private var contactRepository: ContactRepository = ContactRepository(this.context)

    fun updateContacts(newContacts: List<Contact>) {
        this.contacts = newContacts
        notifyDataSetChanged()
    }


    fun forNotifyDataSetChanged() {
        updateContacts(this.contactRepository.getAllContacts())
    }



    override fun getCount(): Int {
        return contacts.size
    }

    override fun getItem(position: Int): Any {
        return contacts[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val contact = getItem(position) as Contact
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.single_contact_view, parent, false)

        // Set values to views in the layout
        val contactNameTextView: TextView = view.findViewById(R.id.contact_number_name)
        val contactNumberTextView: TextView = view.findViewById(R.id.contact_number)
        val callButton: ImageButton = view.findViewById(R.id.call_btn)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_contact)
        val updateButton: ImageButton = view.findViewById(R.id.update_contact)

        contactNameTextView.text = MyUtils.getSubStr(contact.name, 9)
        contactNumberTextView.text = MyUtils.getSubStr(contact.phoneNumber, 11)


        // Handle the call button click if needed
        callButton.setOnClickListener {
            // You can use the contact.phoneNumber for the phone number
            val phoneNumber = contact.phoneNumber
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            context.startActivity(intent)
        }


        deleteButton.setOnClickListener {
            // You can use the contact.phoneNumber for the phone number
            val phoneNumber = contact.phoneNumber

            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Delete Contact")
            alertDialogBuilder.setMessage("Are you sure you want to delete this contact?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                // User clicked Yes, proceed with the delete action
                this.contactRepository.deleteContact(contact.id)
//                updateContacts(this.contactRepository.getAllContacts())
                forNotifyDataSetChanged();
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                // User clicked No, do nothing
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }



        updateButton.setOnClickListener {
            // You can use the contact.phoneNumber for the phone number
            val phoneNumber = contact.phoneNumber

            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Update Contact")
            alertDialogBuilder.setMessage("Are you sure you want to update this contact?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->

//                Toast.makeText(this.context, contact.id.toString(), Toast.LENGTH_LONG).show()

                val intent = Intent(context, UpdateContactActivity::class.java)
                MyUtils.saveContactToIntent(intent, contact)
                context.startActivity(intent)

            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                // User clicked No, do nothing
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


        return view
    }

}