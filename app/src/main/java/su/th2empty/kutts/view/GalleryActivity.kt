/*
 * Copyright (c) 2023 Denis Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.ActivityGalleryBinding
import su.th2empty.kutts.view.adapters.GalleryItemRecyclerViewAdapter
import su.th2empty.kutts.viewmodel.GalleryViewModel

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[GalleryViewModel::class.java]
    }

    private fun calculateSpanCount(): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val itemWidth = resources.getDimensionPixelSize(R.dimen.gallery_item_width)
        val spanCount = screenWidth / itemWidth
        return if (spanCount > 0) spanCount else 1
    }

    private fun setupView() {
        viewModel.imgLinks.observe(this) { links ->
            val layoutManager = StaggeredGridLayoutManager(
                calculateSpanCount(),
                StaggeredGridLayoutManager.VERTICAL
            )
            binding.imagesRecycler.layoutManager = layoutManager

            val imagesRecyclerViewAdapter = GalleryItemRecyclerViewAdapter()
            imagesRecyclerViewAdapter.submitList(links)
            binding.imagesRecycler.adapter = imagesRecyclerViewAdapter
        }

        viewModel.fetchImgLinks()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)

        setupActionBar()
        setupView()

        setContentView(binding.root)
    }
}