package com.example.challenge3.pages.AuthPages

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challenge3.util.viewmodelsfactory.PageViewModelFactory
import com.example.challenge3.databinding.FragmentRegisterBinding
import com.example.challenge3.models.User
import com.example.challenge3.util.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {
    private lateinit var binding:FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    private var isPasswordVisible=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            PageViewModelFactory(requireActivity().application)
        )
            .get(RegisterViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        //for clickable icon
        binding.etPasswordRegister.compoundDrawablesRelative[2].let {
            it.setBounds(0,0,it.intrinsicWidth,it.intrinsicHeight)
            binding.etPasswordRegister.setOnTouchListener{ _,event->
                if(event.action == MotionEvent.ACTION_UP &&
                    event.rawX >= binding
                        .etPasswordRegister.right-(it.bounds.width()+25)){
                    if(!isPasswordVisible){
                        binding.etPasswordRegister
                            .inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        isPasswordVisible = true
                        return@setOnTouchListener true
                    }
                    isPasswordVisible=false
                    binding.etPasswordRegister
                        .inputType= InputType
                        .TYPE_TEXT_VARIATION_PASSWORD or InputType
                        .TYPE_CLASS_TEXT
                    return@setOnTouchListener true
                }
                false
            }
        }



        binding.btnRegister.setOnClickListener {

            viewModel.registerUser(User(
                username = binding.etUsernameRegister.text.toString(),
                email = binding.etEmailRegister.text.toString(),
                telepon = binding.etTeleponRegister.text.toString()
            ),binding.etEmailRegister.text.toString(),
                binding.etPasswordRegister.text.toString())
        }

        viewModel.isRegister.observe(viewLifecycleOwner){
            if(it){
                viewModel.resetRegisterStatus()
                findNavController().popBackStack()
            }
        }
        return binding.root
    }
}