package su.th2empty.kutts.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import su.th2empty.kutts.R
import su.th2empty.kutts.viewmodel.AdmissionViewModel

class AdmissionFragment : Fragment() {

    companion object {
        fun newInstance() = AdmissionFragment()
    }

    private lateinit var viewModel: AdmissionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admission, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdmissionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}