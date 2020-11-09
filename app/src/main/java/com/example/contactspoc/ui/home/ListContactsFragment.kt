package com.example.contactspoc.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactspoc.R
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.MainActivity
import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.di.factory.ListContactViewModelFactory
import com.example.contactspoc.extensions.toContact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListContactsFragment : Fragment(), AdapterList.OnClickItemListContact {

    //@Inject lateinit var listContactViewModelFactory: ListContactViewModelFactory
    private val fab: FloatingActionButton by lazy { (activity as MainActivity).findViewById(R.id.fab) }
    private val adapterRecycler by lazy { AdapterList(requireContext(), mutableListOf(), this) }
    private val viewmodel by lazy {
        ViewModelProviders.of(this).get(ListContactsViewModel::class.java)
        //ViewModelProviders.of(this, listContactViewModelFactory)[ListContactsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupScreen()
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_listContactsFragment_to_editFragment)
        }
        viewmodel.stateData.observe(viewLifecycleOwner) { refresh(it)
        }

    }

    private fun setupRecyclerView() {
        recyclerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterRecycler
        }
    }

    override fun onClick(contactsEntity: ContactsEntity?) {

        val action = ListContactsFragmentDirections
            .actionListContactsFragmentToDetailContactFragment(contactsEntity?.toContact())
        findNavController().navigate(action)
        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_pencil))
    }

    private fun refresh(stateData: StateLiveData) =
        when (stateData) {
            Loading -> println("Loading")
            PostCall -> println("Postcall")
            is ListContactsState -> adapterRecycler.loadData(stateData.contacts)
        }

    private fun setupScreen(){
        (activity as MainActivity).title = getString(R.string.titlebar)
        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_add_contact))
    }

}