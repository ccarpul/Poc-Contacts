package com.example.contactspoc.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contactspoc.R
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.extensions.blockEdit
import com.example.contactspoc.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.detail_layout.*


class DetailContactFragment : Fragment() {

    private val fab: FloatingActionButton by lazy { (activity as MainActivity).findViewById(R.id.fab) }

    private var contactDetail: Contact? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { contactDetail = DetailContactFragmentArgs.fromBundle(it).detailContact }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScreen(contactDetail)

        fab.setOnClickListener {

            val action = DetailContactFragmentDirections.actionDetailContactFragmentToEditFragment()
                .setContactDetail(contactDetail)

            findNavController().navigate(action)
        }
    }

    private fun setupScreen(contact: Contact?){
        (activity as MainActivity).title = "Detail"
        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_pencil))

        editTextName.blockEdit(contact?.name)
        editTextPhone.blockEdit(contact?.phone)
        editTextAddress.blockEdit(contact?.address)
        editTextBirthday.blockEdit(contact?.birthday)
    }

    override fun onDestroy() {
        super.onDestroy()
        println(" DetailFragment onDestroy")
    }

}

/*
        val callback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().apply {
                    popBackStack()
                    navigate(R.id.listContactsFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
*/