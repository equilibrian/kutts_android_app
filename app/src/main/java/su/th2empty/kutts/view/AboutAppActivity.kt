package su.th2empty.kutts.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.ActivityAboutAppBinding
import su.th2empty.kutts.viewmodel.AboutAppViewModel

class AboutAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutAppBinding
    private lateinit var viewModel: AboutAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setContentView(binding.root)
        setupViewModel()
        observeLiveData()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[AboutAppViewModel::class.java]
    }

    private fun setupView() {
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener { finish() }
        binding.termsOfUseBtn.setOnClickListener { launchTermsOfUseActivity() }
        binding.copyInfoBtn.setOnClickListener { viewModel.copyToClipboard() }
    }

    private fun observeLiveData() {
        viewModel.applicationVersionValue.observe(this) { version ->
            binding.applicationVersion.text = version
        }

        viewModel.osVersionValue.observe(this) { osVersion ->
            binding.osVersion.text = osVersion
        }

        viewModel.deviceName.observe(this) { deviceName ->
            binding.deviceName.text = deviceName
        }

        viewModel.showCopiedMessage.observe(this) { showCopied ->
            if (showCopied) {
                Toast.makeText(
                    binding.root.context,
                    resources.getString(R.string.st_copied),
                    Toast.LENGTH_LONG
                ).show()
                viewModel.showCopiedMessage.value = false
            }
        }
    }

    private fun launchTermsOfUseActivity() {
        startActivity(Intent(this, TermsOfUseActivity::class.java))
    }
}