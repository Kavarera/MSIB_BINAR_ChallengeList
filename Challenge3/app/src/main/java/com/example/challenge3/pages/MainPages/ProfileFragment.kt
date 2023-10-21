package com.example.challenge3.pages.MainPages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.challenge3.R
import com.example.challenge3.databinding.FragmentProfileBinding
import com.example.challenge3.util.viewmodels.ProfileVIewModel


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private val viewModel by viewModels<ProfileVIewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        viewModel.getUserData(requireContext())


        viewModel.User.observe(viewLifecycleOwner){
            binding.etUsernameProfile.setText(it?.username)
            binding.etEmailProfile.setText(it?.email)
            binding.etTeleponProfile.setText(it?.telepon)
        }

        binding.btnEditProfile.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_profileEditActivity)
        }

        return binding.root
    }
}