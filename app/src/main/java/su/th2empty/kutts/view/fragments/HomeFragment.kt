package su.th2empty.kutts.view.fragments

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import su.th2empty.kutts.KuttsApplication
import su.th2empty.kutts.R
import su.th2empty.kutts.databinding.FragmentHomeBinding
import su.th2empty.kutts.model.Location
import su.th2empty.kutts.view.adapters.ContactsRecyclerViewAdapter
import su.th2empty.kutts.view.adapters.RecyclerViewItemListener
import su.th2empty.kutts.view.adapters.LocationsRecyclerViewAdapter
import su.th2empty.kutts.viewmodel.HomeViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val locationsRVListener = object : RecyclerViewItemListener {
        @Throws(ActivityNotFoundException::class)
        override fun onButtonClick(location: Location) {
            val address = "${location.index}, ${location.city}, ${location.street}, ${location.houseNumber}"
            val uri = Uri.parse("geo:0,0?q=$address")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Timber.wtf(ex)
                Toast.makeText(activity, getString(R.string.st_error_to_open_map), Toast.LENGTH_LONG).show()
            }
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

        try {
            setupWebsiteClickListener()
            setupContactsRecyclerView()
            setupLocationsRecyclerView()
        } catch (ex: Exception) {
            AlertDialog.Builder(KuttsApplication.instance).apply {
                setTitle("Ебал того рот")
                setMessage(ex.message)
                setPositiveButton("У сука") { wtf, _ ->
                    wtf.dismiss()
                }
            }
        }

        return root
    }

    /**
     * Sets up the click listener for the website link.
     * When clicked, opens the website URL in a web browser.
     * If an error occurs while opening the website, logs the error using Timber
     * and displays a toast message to notify the user about the error.
     *
     * @throws Exception if an error occurs while opening the website URL.
     */
    @Throws(Exception::class)
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
            val contactsRecyclerViewAdapter = ContactsRecyclerViewAdapter(contacts)
            binding.contactsRecycler.adapter = contactsRecyclerViewAdapter
        }
    }

    private fun setupLocationsRecyclerView() {
        homeViewModel.allLocations.observe(viewLifecycleOwner) { locations ->
            val locationsRecyclerViewAdapter = LocationsRecyclerViewAdapter(locations, locationsRVListener)
            binding.locationsRecycler.adapter = locationsRecyclerViewAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smoothScrollContactsRecyclerView()
    }

    /**
     * Performs a smooth scroll animation on the contacts RecyclerView.
     * Scrolls the RecyclerView by a distance of 256 pixels to the right,
     * then scrolls it back by the same distance after a delay of 600 milliseconds.
     * The scrolling animation starts after a delay of 1000 milliseconds.
     */
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