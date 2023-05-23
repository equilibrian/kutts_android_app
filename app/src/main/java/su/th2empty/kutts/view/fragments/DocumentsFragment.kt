package su.th2empty.kutts.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.FragmentDocumentsBinding
import su.th2empty.kutts.model.PdfDocument
import su.th2empty.kutts.view.custom.LayoutButton
import su.th2empty.kutts.viewmodel.DocumentsViewModel

class DocumentsFragment : Fragment() {
    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!

    private val documentsViewModel by lazy {
        ViewModelProvider(this)[DocumentsViewModel::class.java]
    }

    private fun observePdfDocuments() {
        documentsViewModel.pdfDocuments.observe(viewLifecycleOwner) { documents ->
            if (documents.isNotEmpty()) {
                displayPdfDocuments(documents)
                binding.nothingToShowLayout.visibility = View.GONE
                binding.contentLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun displayPdfDocuments(documents: List<PdfDocument>) {
        binding.documentsLayout.removeAllViews()
        documents.forEach { document ->
            val button = createPdfButton(document)
            binding.documentsLayout.addView(button)
        }
    }

    private fun createPdfButton(document: PdfDocument): LayoutButton {
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
            setBackground(outValue.data)
            layoutParams = documentLayoutParams
            setMarginTop(8)
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

    private fun setupView() {
        observePdfDocuments()
        documentsViewModel.fetchPdfDocuments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupView()

        return root
    }
}