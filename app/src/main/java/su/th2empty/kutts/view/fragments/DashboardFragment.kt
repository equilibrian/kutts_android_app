package su.th2empty.kutts.view.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.FragmentDashboardBinding
import su.th2empty.kutts.view.AboutAppActivity
import su.th2empty.kutts.view.EducationalProgramsActivity
import su.th2empty.kutts.view.custom.LayoutButton
import su.th2empty.kutts.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    /*private val dashboardViewModel by lazy {
        ViewModelProvider(this)[DashboardViewModel::class.java]
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupView()

        return root
    }

    private fun setupView() {
        binding.educationalProgramsButton.setOnClickListener {
            startActivity(Intent(activity, EducationalProgramsActivity::class.java))
        }

        binding.aboutAppButton.setOnClickListener {
            startActivity(Intent(activity, AboutAppActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}