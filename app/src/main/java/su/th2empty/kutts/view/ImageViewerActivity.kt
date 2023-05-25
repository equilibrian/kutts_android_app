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

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import su.th2empty.kutts.databinding.ActivityImageViewerBinding
import su.th2empty.kutts.view.adapters.ImageViewerViewPagerAdapter

class ImageViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewerBinding

    private lateinit var imgLinks: List<Uri>
    private var currentImagePosition: Int = 0

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupView() {
        imgLinks = intent.getParcelableArrayExtra("links")?.map { it as Uri } ?: emptyList()
        currentImagePosition = intent.getIntExtra("currentPosition", 0)

        val imagesViewerAdapter = ImageViewerViewPagerAdapter()
        imagesViewerAdapter.apply { submitList(imgLinks) }
        binding.viewpager.apply {
            adapter = imagesViewerAdapter
            setCurrentItem(currentImagePosition, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewerBinding.inflate(layoutInflater)

        setupActionBar()
        setupView()

        supportPostponeEnterTransition()
        setContentView(binding.root)
        window.decorView.doOnPreDraw {
            supportStartPostponedEnterTransition()
        }
    }
}