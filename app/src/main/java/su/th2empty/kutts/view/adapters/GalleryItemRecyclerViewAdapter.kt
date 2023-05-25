/*
 * Copyright (c) 2023 Denis Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.view.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.RecyclerViewGalleryItemBinding
import su.th2empty.kutts.utils.ImageLoader
import su.th2empty.kutts.view.decorations.RecyclerItemDecoration

class GalleryItemRecyclerViewAdapter(private val listener: (List<Uri>, Int, currentItem: View) -> Unit)
    : ListAdapter<Uri, GalleryItemRecyclerViewAdapter.ViewHolder>(DiffCallback) {

    private lateinit var itemDecoration: RecyclerItemDecoration

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(private val binding: RecyclerViewGalleryItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(img: Uri) {
            val imgLoader = ImageLoader()
            imgLoader.loadImageIntoView(binding.img, img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewGalleryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img = getItem(position)
        holder.bind(img)
        holder.itemView.setOnClickListener { listener.invoke(currentList, position, holder.itemView) }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        itemDecoration = RecyclerItemDecoration(
            bottom = recyclerView.context.resources.getDimensionPixelOffset(R.dimen.space1dp),
            left = recyclerView.context.resources.getDimensionPixelOffset(R.dimen.space1dp),
            right = recyclerView.context.resources.getDimensionPixelOffset(R.dimen.space1dp)
        )

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