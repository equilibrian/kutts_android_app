package su.th2empty.kutts.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.AdmissionBottomSheetBinding
import su.th2empty.kutts.databinding.FragmentAdmissionBinding
import su.th2empty.kutts.model.AdmissionVariant
import java.time.LocalDate

class AdmissionFragment : Fragment() {
    private var _binding: FragmentAdmissionBinding? = null
    private val binding get() = _binding!!

    private var bottomSheetDialog: BottomSheetDialog? = null
    private lateinit var bottomSheetBinding: AdmissionBottomSheetBinding

    companion object {
        const val PERSONAL_CABINET_URI = "https://priem.egov66.ru"
    }

    /*private val admissionViewModel by lazy {
        ViewModelProvider(this)[AdmissionViewModel::class.java]
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdmissionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        createBottomSheet()
        setupView()

        return root
    }

    private fun setupView() {
        binding.admissionDeadlines.text = getString(R.string.st_admission_deadlines)
            .format(LocalDate.now().year, LocalDate.now().year)
        binding.admissionDeadlines2.text =
            getString(R.string.st_admission_deadlines2).format(LocalDate.now().year)

        binding.throughPersonalAccountButton.setOnClickListener {
            updateBottomSheetContent(AdmissionVariant.PERSONAL_CABINET)
            bottomSheetDialog?.show()
        }

        binding.admissionCommitteeButton.setOnClickListener {
            updateBottomSheetContent(AdmissionVariant.ADMISSION_OFFICE)
            bottomSheetDialog?.show()
        }

        binding.byMailButton.setOnClickListener {
            updateBottomSheetContent(AdmissionVariant.POSTAL_OPERATOR)
            bottomSheetDialog?.show()
        }

        binding.byEmailButton.setOnClickListener {
            updateBottomSheetContent(AdmissionVariant.EMAIL)
            bottomSheetDialog?.show()
        }
    }

    private fun createBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetBinding = AdmissionBottomSheetBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog?.setContentView(bottomSheetBinding.root)
    }

    /**
     * Updates the content of the bottom sheet with the given admission variant.
     * @param variant The admission variant containing the information to update the bottom sheet.
     */
    private fun updateBottomSheetContent(variant: AdmissionVariant) {
        bottomSheetBinding.title.text = getString(variant.titleResId)

        // Build the string representation of the required documents
        val documents = buildString {
            variant.requiredDocuments.forEachIndexed { index, admissionDocument ->
                append("${index + 1}. ")
                append(getString(admissionDocument.document))
                append('\n')
            }
        }

        bottomSheetBinding.textContent.text = documents.trim()

        // Configure the submit button
        bottomSheetBinding.submitButton.apply {
            visibility = if (variant.submitButtonEnabled) View.VISIBLE else View.GONE
            setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PERSONAL_CABINET_URI)))
            }
        }
    }
}