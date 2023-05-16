package su.th2empty.kutts.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.FragmentDashboardBinding
import su.th2empty.kutts.databinding.FragmentDocumentsBinding
import su.th2empty.kutts.view.AboutAppActivity
import su.th2empty.kutts.viewmodel.DocumentsViewModel

class DocumentsFragment : Fragment() {
    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!

    private val documentsViewModel by lazy {
        ViewModelProvider(this)[DocumentsViewModel::class.java]
    }

    private fun setupView() {

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