package su.th2empty.kutts.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.FragmentDashboardBinding
import su.th2empty.kutts.view.AboutAppActivity
import su.th2empty.kutts.view.DormitoryActivity
import su.th2empty.kutts.view.EducationalProgramsActivity
import su.th2empty.kutts.view.EnrollmentOrdersActivity
import su.th2empty.kutts.view.EntranceTestsActivity
import su.th2empty.kutts.view.TargetedTrainingActivity
import su.th2empty.kutts.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[DashboardViewModel::class.java]
    }

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

    private fun observeLinks() {
        viewModel.links.observe(viewLifecycleOwner) { links ->
            val intentMonitoring = Intent(Intent.ACTION_VIEW, links["monitoring"])
            binding.monitoringButton.setOnClickListener {
                if (links.isNotEmpty()) {
                    startActivity(intentMonitoring)
                } else Toast.makeText(context, R.string.st_network_error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupView() {
        binding.educationalProgramsButton.setOnClickListener {
            startActivity(Intent(activity, EducationalProgramsActivity::class.java))
        }

        binding.aboutAppButton.setOnClickListener {
            startActivity(Intent(activity, AboutAppActivity::class.java))
        }

        binding.entranceTestsButton.setOnClickListener {
            startActivity(Intent(activity, EntranceTestsActivity::class.java))
        }

        binding.targetedTrainingButton.setOnClickListener {
            startActivity(Intent(activity, TargetedTrainingActivity::class.java))
        }

        binding.ordersButton.setOnClickListener {
            startActivity(Intent(activity, EnrollmentOrdersActivity::class.java))
        }

        binding.dormitoryInfoButton.setOnClickListener {
            startActivity(Intent(activity, DormitoryActivity::class.java))
        }

        observeLinks()
        viewModel.fetchLinks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}