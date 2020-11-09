package com.example.contactspoc.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactspoc.R
import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.extensions.setShape
import kotlinx.android.synthetic.main.recycler_item.view.*

class AdapterList(
    private val context: Context,
    private var dataset: MutableList<ContactsEntity>?,
    private val listener: OnClickItemListContact
) :
    RecyclerView.Adapter<AdapterList.ItemViewHolder>() {

    fun loadData(dataset: MutableList<ContactsEntity>?) {
        this.dataset = dataset
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        adapterLayout.itemImage.setShape()
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(dataset?.get(position))

    override fun getItemCount(): Int = dataset?.size ?: 0

    interface OnClickItemListContact {
        fun onClick(contactsEntity: ContactsEntity?)
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        fun bind(contacts: ContactsEntity?) {
            itemView.apply {
                itemName.text = contacts?.name
                itemPhone.text = contacts?.phone
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) = listener.onClick(dataset?.get(position))

    }

}
