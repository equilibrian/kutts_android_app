/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.view.adapters

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import su.th2empty.kutts.R
import su.th2empty.kutts.model.Contact

class ContactsRecyclerViewAdapter(private val contacts: List<Contact>)
    : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val jobTitle: TextView = v.findViewById(R.id.title_job)
        val contactName: TextView = v.findViewById(R.id.contact_name)
        val contactPhone: TextView = v.findViewById(R.id.contact_phone)
        val contactEmail: TextView = v.findViewById(R.id.contact_email)
    }

    /**
     * Contacts items decoration
     * @param horizontalSpace Horizontal padding in dp
     */
    class ItemDecoration(private val horizontalSpace: Float) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val horizontalSpaceInPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                horizontalSpace,
                view.resources.displayMetrics
            ).toInt()

            outRect.right = horizontalSpaceInPx
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_recycler_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    private fun getContactName(ctx: Context, contact: Contact): String {
        return if (contact.name.isNullOrBlank()) {
            ctx.getString(R.string.st_unknown)
        } else {
            contact.name
        }
    }

    private fun getContactEmail(ctx: Context, contact: Contact): String {
        return if (contact.email.isNullOrBlank()) {
            ctx.getString(R.string.st_unknown)
        } else {
            contact.email
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.jobTitle.text = contact.jobTitle
        holder.contactName.text = getContactName(holder.itemView.context, contact)
        holder.contactPhone.text = contact.phoneNumber
        holder.contactEmail.text = getContactEmail(holder.itemView.context, contact)
    }
}