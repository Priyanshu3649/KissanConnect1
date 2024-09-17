package com.example.kisaanconnect.ui.ads

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.example.kisaanconnect.R
import com.example.kisaanconnect.data.entities.Product
import com.example.kisaanconnect.databinding.FragmentPagerBinding
import com.example.kisaanconnect.firebase.services.ProductServices
import com.example.kisaanconnect.utils.EventObserver
import com.example.kisaanconnect.utils.LocationUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class PagerFragment : Fragment() {
    private lateinit var binding: FragmentPagerBinding

    private val viewmodel by viewModels<AdsFragmentViewModel> {
        AdsFragmentViewModelFactory(ProductServices)
    }

    private var product: Product? = null

    private val handleLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            findNavController().navigate(
                PagerFragmentDirections.actionNavigationPagerToAddProductFragment(product)
            )
        } else {
            Snackbar.make(
                requireView(), getString(R.string.add_new_product_require_location),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPagerBinding.inflate(inflater, container, false)
        viewmodel.apply {
            eventAdsDetails.observe(viewLifecycleOwner, EventObserver {
                findNavController()
                    .navigate(PagerFragmentDirections.actionNavigationPagerToProductFragment(it))
            })

            eventEditAds.observe(viewLifecycleOwner, EventObserver { adId ->
                when {
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {

                        product = viewmodel.getProduct(adId)
                        LocationUtils.checkLocationRequest(requireActivity()) {
                            findNavController().navigate(
                                PagerFragmentDirections.actionNavigationPagerToAddProductFragment(
                                    product
                                )
                            )
                        }
                    }

                    shouldShowRequestPermissionRationale(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) -> {

                        Snackbar.make(
                            requireView(), getString(R.string.add_new_product_require_location),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        handleLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentArray = arrayListOf(PostedAdsFragment(), InterestedAdsFragment())

        val pager = binding.pager.apply {
            adapter = AdsPagerAdapter(this@PagerFragment, fragmentArray)
        }
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.my_ads)
            } else {
                tab.text = getString(R.string.interested_ads)
            }
        }.attach()
    }
}