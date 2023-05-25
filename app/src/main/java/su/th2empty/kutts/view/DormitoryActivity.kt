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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.ActivityDormitoryBinding
import su.th2empty.kutts.model.PdfDocument
import su.th2empty.kutts.view.custom.LayoutButton
import su.th2empty.kutts.viewmodel.DormitoryViewModel

class DormitoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDormitoryBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[DormitoryViewModel::class.java]
    }

    private fun observeDormitoryInfo() {
        viewModel.dormitoryInfo.observe(this) { info ->
            binding.description.text = Html.fromHtml(info.description, Html.FROM_HTML_MODE_COMPACT)

            observeLocation()
            viewModel.fetchLocation(info.locationId)
        }
    }

    private fun observeLocation() {
        viewModel.dormitoryLocation.observe(this) { location ->
            binding.address.text = getString(
                R.string.st_location_format,
                location.city,
                location.street,
                location.houseNumber
            )
        }
    }

    private fun observePdfDocuments() {
        viewModel.pdfDocuments.observe(this) { documents ->
            if (documents.isNotEmpty()) {
                displayPdfDocuments(documents)
            }
        }
    }

    private fun displayPdfDocuments(documents: List<PdfDocument>) {
        binding.documentsLayout.removeAllViews()
        documents.forEachIndexed { idx, document ->
            val button = createPdfButton(document, idx)
            binding.documentsLayout.addView(button)
        }
    }

    private fun createPdfButton(document: PdfDocument, position: Int): LayoutButton {
        val documentLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        }

        val button = LayoutButton(binding.root.context)
        button.apply {
            val colorBackgroundFloating = com.google.android.material.R.attr.colorBackgroundFloating
            val outValue = TypedValue()
            context.theme.resolveAttribute(colorBackgroundFloating, outValue, true)

            setText(document.title)
            setIconStart(R.drawable.ic_pdf_document)
            setIconEnd(R.drawable.ic_arrow_right)
            setBackgroundColor(outValue.data)
            layoutParams = documentLayoutParams
            if (position != 0) setMarginTop(8)
            setOnClickListener {
                openPdfDocument(document.url)
            }
        }
        return button
    }

    private fun openPdfDocument(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupView() {
        setupActionBar()
        observeDormitoryInfo()
        observePdfDocuments()
        viewModel.fetchPdfDocuments()

        binding.openGalleryButton.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDormitoryBinding.inflate(layoutInflater)

        setupView()

        setContentView(binding.root)
    }
}