package com.example.kisaanconnect.ui.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kisaanconnect.R
import com.example.kisaanconnect.databinding.FragmentInterestedAdsBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 */
@ExperimentalCoroutinesApi
class InterestedAdsFragment : Fragment() {

    private val viewmodel by viewModels<AdsFragmentViewModel> (ownerProducer = {
        requireParentFragment()
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentInterestedAdsBinding>(
            inflater, R.layout.fragment_interested_ads, container, false)

        binding.apply {
            viewModel = viewmodel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}