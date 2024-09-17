package com.example.kisaanconnect.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kisaanconnect.R
import com.example.kisaanconnect.databinding.FragmentMessageBinding
import com.example.kisaanconnect.firebase.services.ProductServices
import com.example.kisaanconnect.utils.EventObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MessageFragment : Fragment() {

    private val viewmodel by viewModels<MessageViewModel> {
        MessageViewModelFactory(ProductServices)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil
            .inflate<FragmentMessageBinding>(inflater, R.layout.fragment_message, container, false)

        binding.apply {
            viewModel = viewmodel
            lifecycleOwner = viewLifecycleOwner
        }

        viewmodel.apply {
            eventMessage.observe(viewLifecycleOwner, EventObserver{
                findNavController()
                    .navigate(MessageFragmentDirections.actionNavigationMessageToChatFragment(it))
            })
        }

        return binding.root
    }
}