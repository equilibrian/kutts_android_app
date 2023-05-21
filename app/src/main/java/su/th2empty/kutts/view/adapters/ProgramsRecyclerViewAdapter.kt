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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.EducationalProgramBottomSheetBinding
import su.th2empty.kutts.databinding.EducationalProgramRecyclerItemBinding
import su.th2empty.kutts.model.EducationalProgram
import su.th2empty.kutts.view.decorations.RecyclerItemDecoration

/**
 * Adapter for the Educational Programs RecyclerView. Displays a list of EducationalProgram items.
 * On clicking the "More" button of an item, opens a bottom sheet with more details about the program.
 */
class ProgramsRecyclerViewAdapter : ListAdapter<EducationalProgram, ProgramsRecyclerViewAdapter.ViewHolder>(DiffCallback) {

    private lateinit var itemDecoration: RecyclerItemDecoration
    lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetBinding: EducationalProgramBottomSheetBinding

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

    /**
     * Creates a bottom sheet dialog for the given parent view group.
     * @param parent The parent view group for the bottom sheet dialog.
     */
    private fun createBottomSheet(parent: ViewGroup) {
        bottomSheetDialog = BottomSheetDialog(parent.context)
        bottomSheetBinding = EducationalProgramBottomSheetBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
    }

    /**
     * Updates the content of the bottom sheet view with the information from the given [EducationalProgram].
     * @param program the educational program object containing the data to be displayed.
     */
    fun updateBottomSheetContent(program: EducationalProgram) {
        bottomSheetBinding.programName.text = program.programName
        bottomSheetBinding.basicEducation.text = program.basicEducation
        bottomSheetBinding.trainingPeriod.text = program.trainingPeriod
        bottomSheetBinding.trainingForm.text = program.trainingForm
        bottomSheetBinding.seatsNumber.text = program.seatsNumber.toString()
        bottomSheetBinding.qualification.text = program.qualification
        bottomSheetBinding.programCode.text = program.programCode
    }

    inner class ViewHolder(private val binding: EducationalProgramRecyclerItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(program: EducationalProgram) {
            binding.programName.text = program.programName
            binding.fundingSource.text = program.fundingSource
            binding.seatsNumber.text = itemView.context.getString(R.string.st_seats_number_format, program.seatsNumber)
            binding.moreButton.setOnClickListener {
                updateBottomSheetContent(program)
                bottomSheetDialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EducationalProgramRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

       createBottomSheet(parent)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val program = getItem(position)
        holder.bind(program)
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