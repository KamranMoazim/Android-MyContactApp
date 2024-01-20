package com.example.mycontactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.mycontactapp.repos.ContactRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var contactRepository: ContactRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        contactRepository = ContactRepository(this)


        val bottomNav:BottomNavigationView = findViewById(R.id.bottomNav)

        // Set the default fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AllContactsFragment()).commit()


        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_contact_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AllContactsFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_new_contact_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AddNewContactFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

    }


    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AllContactsFragment()).commit()
    }


}