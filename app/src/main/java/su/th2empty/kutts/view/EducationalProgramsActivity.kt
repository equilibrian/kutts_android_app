/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.ActivityEducationalProgramsBinding
import su.th2empty.kutts.databinding.SearchLayoutBinding
import su.th2empty.kutts.model.EducationalProgram
import su.th2empty.kutts.utils.AppPreferences
import su.th2empty.kutts.view.adapters.ProgramsRecyclerViewAdapter
import su.th2empty.kutts.view.adapters.RecyclerViewItemListener
import su.th2empty.kutts.view.adapters.SearchEducationalProgramsRVAdapter
import su.th2empty.kutts.viewmodel.EducationalProgramsViewModel

class EducationalProgramsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEducationalProgramsBinding

    private lateinit var educationalProgramsAdapter: ProgramsRecyclerViewAdapter

    companion object {
        const val DEFAULT_FILTER = 0
        const val FILTER_OPOP = 1
        const val FILTER_OPPO = 2
    }

    private var currentFilter: Int = DEFAULT_FILTER
    private lateinit var appPreferences: AppPreferences
    private var shownTooltips: Boolean
        get() = appPreferences.getBoolean("shownTooltips", false)
        set(value) = appPreferences.putBoolean("shownTooltips", value)

    private lateinit var searchDialog: AlertDialog
    private lateinit var searchViewBinding: SearchLayoutBinding
    private lateinit var searchViewAdapter: SearchEducationalProgramsRVAdapter

    private val educationalProgramsViewModel: EducationalProgramsViewModel by lazy {
        ViewModelProvider(this)[EducationalProgramsViewModel::class.java]
    }

    /**
     * Applies the selected filter to the list of educational programs and updates the adapter.
     * @param filter The filter to apply, can be one of DEFAULT_FILTER, FILTER_OPOP, or FILTER_OPPO.
     */
    private fun applyFilter(filter: Int) {
        currentFilter = filter
        filterPrograms()
    }

    /**
     * Sets up the filters for the educational programs adapter by setting the appropriate click listeners
     * for each filter chip. When a filter chip is clicked, the appropriate filter is applied to the adapter.
     */
    private fun setupFilters() {
        binding.chipFilterAll.setOnClickListener {
            applyFilter(DEFAULT_FILTER)
        }

        binding.chipFilterOpop.setOnClickListener {
            applyFilter(FILTER_OPOP)
        }

        binding.chipFilterOppo.setOnClickListener {
            applyFilter(FILTER_OPPO)
        }
    }

    /**
     * Filters the educational programs based on the current filter and updates the provided [educationalProgramsAdapter].
     * If the current filter is [FILTER_OPOP], only programs with the category ID of [FILTER_OPOP] will be included.
     * If the current filter is [FILTER_OPPO], only programs with the category ID of [FILTER_OPPO] will be included.
     * Otherwise, all programs will be included.
     */
    private fun filterPrograms() {
        educationalProgramsViewModel.educationalPrograms.observe(this) { educationalPrograms ->
            val filteredItems = when(currentFilter) {
                FILTER_OPOP -> educationalPrograms.filter { it.categoryId == FILTER_OPOP }
                FILTER_OPPO -> educationalPrograms.filter { it.categoryId == FILTER_OPPO }
                else -> educationalPrograms
            }

            educationalProgramsAdapter.submitList(filteredItems)
        }
    }

    /**
     * Sets up the RecyclerView displaying the educational programs.
     * Observes changes in the ViewModel's `educationalPrograms` LiveData and updates the RecyclerView adapter
     * with the new data. Also sets up the filter chips by calling [setupFilters].
     */
    private fun setupEducationalProgramsRecyclerView() {
        educationalProgramsViewModel.educationalPrograms.observe(this) { educationalPrograms ->
            educationalProgramsAdapter = ProgramsRecyclerViewAdapter()
            educationalProgramsAdapter.submitList(educationalPrograms)
            binding.programsRecyclerView.adapter = educationalProgramsAdapter
            binding.progressBar.visibility = View.GONE

            setupFilters()
            setupSearchView()
        }
    }

    private val searchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = false

        override fun onQueryTextChange(newText: String?): Boolean {
            val query = newText?.trim()?.lowercase() ?: ""
            val filteredEducationalProgramsList = educationalProgramsAdapter.currentList.filter {
                it.programName.contains(query, true)
            }

            val searchResult = if (query.isNotEmpty()) filteredEducationalProgramsList else listOf()
            searchViewAdapter.submitList(searchResult)
            searchViewBinding.divider.visibility = if (searchResult.isNotEmpty()) View.VISIBLE else View.GONE

            return true
        }
    }

    /**
     * Sets up the search view in the activity.
     * It inflates a dialog with a search view, sets up a listener for the search view,
     * and updates the list of educational programs shown in the adapter based on user input.
     * Clicking an educational program in the search results dismisses the dialog and updates the
     * bottom sheet content.
     */
    private fun setupSearchView() {
        searchViewBinding = SearchLayoutBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(searchViewBinding.root)
        searchDialog = dialogBuilder.create()
        searchDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        searchViewAdapter = SearchEducationalProgramsRVAdapter(object : RecyclerViewItemListener {
            override fun onClick(educationalProgram: EducationalProgram) {
                searchDialog.dismiss()
                educationalProgramsAdapter.updateBottomSheetContent(educationalProgram)
                educationalProgramsAdapter.bottomSheetDialog.show()
            }
        })

        searchViewBinding.backButton.setOnClickListener { searchDialog.dismiss() }
        searchViewBinding.searchRecyclerView.adapter = searchViewAdapter
        searchViewBinding.searchView.setOnQueryTextListener(searchQueryListener)
    }

    private fun setupView() {
        binding.backBtn.setOnClickListener { finish() }
        binding.fab.setOnClickListener { searchDialog.show() }
    }

    private fun createTapTarget(view: View, description: String): TapTarget {
        return TapTarget.forView(view, description)
            .tintTarget(false)
            .cancelable(false)
            .drawShadow(true)
    }

    private fun addTargetToSequence(sequence: TapTargetSequence, view: View, description: String) {
        sequence.targets(createTapTarget(view, description))
    }

    private fun showTooltips() {
        val sequence = TapTargetSequence(this)
        addTargetToSequence(sequence, binding.chipFilterAll, getString(R.string.st_filter_all_description))
        addTargetToSequence(sequence, binding.chipFilterOpop, getString(R.string.st_filter_opop_description))
        addTargetToSequence(sequence, binding.chipFilterOppo, getString(R.string.st_oppo_filter_description))
        addTargetToSequence(sequence, binding.fab, getString(R.string.st_search_description))
        sequence.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationalProgramsBinding.inflate(layoutInflater)

        setupEducationalProgramsRecyclerView()
        setupView()

        appPreferences = AppPreferences(this)
        if (!shownTooltips) {
            showTooltips()
            shownTooltips = true
        }

        setContentView(binding.root)
    }
}