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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import su.th2empty.kutts.R
import su.th2empty.kutts.model.Location
import su.th2empty.kutts.view.decorations.RecyclerItemDecoration

class LocationsRecyclerViewAdapter(
    private val locations: List<Location>,
    private val listener: RecyclerViewItemListener
    ) : RecyclerView.Adapter<LocationsRecyclerViewAdapter.ViewHolder>() {

    private lateinit var itemDecoration: RecyclerItemDecoration

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title_street_and_house)
        val body: TextView = v.findViewById(R.id.body_city)
        val openMapButton: Button = v.findViewById(R.id.open_map_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_recycler_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locations[position]
        holder.title.text = holder.itemView.context.getString(R.string.st_format_string)
            .format("${location.street}, ${location.houseNumber}")
        holder.body.text = holder.itemView.context.getString(R.string.st_format_string).format(location.city)
        holder.openMapButton.setOnClickListener {
            listener.onButtonClick(location)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        itemDecoration = RecyclerItemDecoration(bottom = recyclerView.context.resources.getDimensionPixelOffset(R.dimen.vertical_space))

        if (recyclerView.itemDecorationCount == 0)
            recyclerView.addItemDecoration(itemDecoration)

        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (recyclerView.itemDecorationCount != 0)
            recyclerView.removeItemDecoration(itemDecoration)
    }
}