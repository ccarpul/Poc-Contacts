package com.example.contactspoc.ui.edit

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.contactspoc.R
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.extensions.allowEdit
import com.example.contactspoc.MainActivity
import com.example.contactspoc.extensions.toEntityContacts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.detail_layout.*
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {


    private val editViewModel: EditViewModel by lazy {
        ViewModelProviders.of(this).get(EditViewModel::class.java)
    }
    private val fab: FloatingActionButton by lazy { (activity as MainActivity).findViewById(R.id.fab) }
    private val contactDetail by lazy { arguments?.let { EditFragmentArgs.fromBundle(it).contactDetail } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenSetup(contactDetail)

        fab.setOnClickListener {
            if (contactDetail?._id.isNullOrEmpty()) saveContact()
            else updateContact()
        }

        imageDelete.setOnClickListener { alertDialog() }

        editViewModel.stateData.observe(viewLifecycleOwner) { addContact(it) }
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
    }

    private fun updateContact() = editViewModel.putContact(contactDetail?._id, getDataEditText())

    private fun saveContact() {

        if (editTextName.editText?.text.toString().isNotBlank()) {
            editViewModel.pushContact(getDataEditText())
        } else Toast.makeText(context, "Please fill the field name", Toast.LENGTH_SHORT).show()

    }

    private fun screenSetup(contact: Contact?) {
        if (contact?._id == null) {
            (activity as MainActivity).title = "New Contact"
            imageDelete.visibility = View.GONE
        } else {
            (activity as MainActivity).title = "Edit Contact"
            imageDelete.visibility = View.VISIBLE
        }

        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_save))
        editTextName.allowEdit(contact?.name)
        editTextPhone.allowEdit(contact?.phone)
        editTextAddress.allowEdit(contact?.address)
        editTextBirthday.allowEdit(contact?.birthday)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Edit Fragment OnDestroy")
    }

    private fun addContact(stateLiveData: StateLiveData) =
        when (stateLiveData) {
            Loading -> println("Loading edit Fragemnt")
            PostCall -> {
                println("postcall edit fragment")
                findNavController().popBackStack()
                findNavController().navigate(R.id.listContactsFragment)

            }
            is SavedStateData -> {
                println(" ssaveStateData ${stateLiveData.contactsEntity}")

            }
        }

    private fun getDataEditText(): Contact = Contact(
        null,
        editTextName.editText?.text.toString(),
        editTextPhone.editText?.text.toString(),
        editTextAddress.editText?.text.toString(),
        editTextBirthday.editText?.text.toString()
    )

    private fun alertDialog() {

        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Delete Contact")
                setMessage("Do you want to remove this contact?")
                setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        contactDetail?._id?.let { contact -> editViewModel.deleteContact(contact) }
                        contactDetail?.toEntityContacts()?.let {  contact -> editViewModel.delete(contact) }
                        println("DELETED")
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        println("nada")
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties


            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()

    }
}
