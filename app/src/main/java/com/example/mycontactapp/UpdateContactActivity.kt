package com.example.mycontactapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.mycontactapp.repos.ContactRepository
import com.example.mycontactapp.utils.MyUtils

class UpdateContactActivity : AppCompatActivity() {

    private lateinit var contactRepository: ContactRepository
    private lateinit var contactNumberEditText:EditText
    private lateinit var nameEditText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)

        contactRepository = ContactRepository(this)

        contactNumberEditText = findViewById(R.id.update_contact_user_number)
        nameEditText = findViewById(R.id.update_user_contact_name)

        var contact = MyUtils.getContactFromIntent(intent)

        contactNumberEditText.setText(contact!!.phoneNumber)
        nameEditText.setText(contact!!.name)





        var btn:Button = findViewById(R.id.update_contact_btn)



        btn.setOnClickListener{

            contact!!.name = nameEditText.text.toString()
            contact!!.phoneNumber = contactNumberEditText.text.toString()


            if (contactRepository.updateContact(contact!!) > -1){
                Toast.makeText(this, "Updated Contact Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Error, Couldn't Update Contact", Toast.LENGTH_LONG).show()
            }



            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}