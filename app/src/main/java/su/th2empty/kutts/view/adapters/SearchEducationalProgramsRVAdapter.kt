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
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import su.th2empty.kutts.databinding.EducationalProgramSearchItemBinding
import su.th2empty.kutts.model.EducationalProgram

class SearchEducationalProgramsRVAdapter(private val listener: RecyclerViewItemListener)
    : ListAdapter<EducationalProgram, SearchEducationalProgramsRVAdapter.ViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EducationalProgram>() {
            override fun areItemsTheSame(oldItem: EducationalProgram, newItem: EducationalProgram): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EducationalProgram, newItem: EducationalProgram): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(private val binding: EducationalProgramSearchItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(program: EducationalProgram) {
            binding.programName.text = program.programName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EducationalProgramSearchItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val program = getItem(position)
        holder.bind(program)
        holder.itemView.setOnClickListener { listener.onClick(program) }
    }
}