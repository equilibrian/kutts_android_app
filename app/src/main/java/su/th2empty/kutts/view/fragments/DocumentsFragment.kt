package su.th2empty.kutts.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import su.th2empty.kutts.databinding.FragmentDocumentsBinding

class DocumentsFragment : Fragment() {
    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!

    /*private val documentsViewModel by lazy {
        ViewModelProvider(this)[DocumentsViewModel::class.java]
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}