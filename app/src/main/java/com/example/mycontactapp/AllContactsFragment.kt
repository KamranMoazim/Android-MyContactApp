package com.example.mycontactapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.example.mycontactapp.adapters.ContactAdapter
import com.example.mycontactapp.data.Contact
import com.example.mycontactapp.repos.ContactRepository


class AllContactsFragment : Fragment() {

    private lateinit var editTextSearch: EditText
    private lateinit var searchBtn: ImageButton

    private lateinit var contactRepository: ContactRepository
    private lateinit var allContactsListView: ListView
    private lateinit var adapter: ContactAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactRepository = ContactRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_all_contacts, container, false)


        editTextSearch = view.findViewById(R.id.search_input)
        searchBtn = view.findViewById(R.id.search_contact_btn)
        // Add click listener to search button
        searchBtn.setOnClickListener {
            // Perform search based on the current text in editTextSearch
            val query = editTextSearch.text.toString()
            filterContacts(query)
        }


        // Add a TextWatcher to editTextSearch
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that characters within `charSequence` are about to be replaced
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that characters within `charSequence` have been replaced
            }

            override fun afterTextChanged(editable: Editable?) {
                // This method is called to notify you that the characters within `editable` have been changed

                // Call your function here with the updated content of editTextSearch
                filterContacts(editable.toString())
            }
        })




        allContactsListView = view.findViewById(R.id.all_contacts_list_view)
        val contacts = contactRepository.getAllContacts()

        // Create the custom adapter and set it to the ListView
        adapter = ContactAdapter(requireContext(), contacts)
        allContactsListView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        adapter.forNotifyDataSetChanged()
    }

    fun filterContacts(query: String) {
//        Toast.makeText(this.context, query, Toast.LENGTH_LONG).show()
        // Update the list of contacts in your adapter based on the search query
        val filteredContacts = contactRepository.searchContact(query)
        adapter.updateContacts(filteredContacts)
    }

}