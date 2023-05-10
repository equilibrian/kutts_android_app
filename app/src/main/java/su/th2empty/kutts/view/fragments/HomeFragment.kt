package su.th2empty.kutts.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.FragmentHomeBinding
import su.th2empty.kutts.model.Location
import su.th2empty.kutts.view.adapters.ContactsRecyclerViewAdapter
import su.th2empty.kutts.view.adapters.LocationsRVItemsListener
import su.th2empty.kutts.view.adapters.LocationsRecyclerViewAdapter
import su.th2empty.kutts.viewmodel.HomeViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val locationsRVListener = object : LocationsRVItemsListener {
        override fun onButtonClick(location: Location) {
            val address = "${location.index}, ${location.city}, ${location.street}, ${location.houseNumber}"
            val uri = Uri.parse("geo:0,0?q=$address")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupWebsiteClickListener()
        setupContactsRecyclerView()
        setupLocationsRecyclerView()

        return root
    }

    private fun setupWebsiteClickListener() {
        binding.website.setOnClickListener {
            try {
                val uri = Uri.parse(binding.website.text.toString())
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } catch (e: Exception) {
                Timber.e(e, "Error opening website")
                Toast.makeText(context, getString(R.string.st_error_opening_website), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupContactsRecyclerView() {
        homeViewModel.allContacts.observe(viewLifecycleOwner) { contacts ->
            val contactsDecoration = ContactsRecyclerViewAdapter.ItemDecoration(8f)
            binding.contactsRecycler.addItemDecoration(contactsDecoration)
            val contactsRecyclerViewAdapter = ContactsRecyclerViewAdapter(contacts)
            binding.contactsRecycler.adapter = contactsRecyclerViewAdapter
        }
    }

    private fun setupLocationsRecyclerView() {
        homeViewModel.allLocations.observe(viewLifecycleOwner) { locations ->
            val locationsDecoration = LocationsRecyclerViewAdapter.ItemDecoration(8f)
            binding.locationsRecycler.addItemDecoration(locationsDecoration)
            val locationsRecyclerViewAdapter = LocationsRecyclerViewAdapter(locations, locationsRVListener)
            binding.locationsRecycler.adapter = locationsRecyclerViewAdapter
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smoothScrollContactsRecyclerView()
    }

    private fun smoothScrollContactsRecyclerView() {
        _binding?.contactsRecycler?.postDelayed({
            _binding?.contactsRecycler?.smoothScrollBy(256, 0)
            _binding?.contactsRecycler?.postDelayed({
                _binding?.contactsRecycler?.smoothScrollBy(-256, 0)
            }, 600)
        }, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}