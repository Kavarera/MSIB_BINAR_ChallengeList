package com.example.challenge3.pages.AuthPages

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.challenge3.databinding.FragmentRegisterBinding
import com.example.challenge3.models.User
import com.example.challenge3.util.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    private lateinit var binding:FragmentRegisterBinding
    private val viewModel: RegisterViewModel by inject()

    private var isPasswordVisible=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity(),
//            PageViewModelFactory(requireActivity().application, ApiClient.instance)
//        )
//            .get(RegisterViewModel::class.java)
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

            if(binding.etPasswordRegister.text.toString().length<=6){
                binding.etPasswordRegister.error = "At least more than 6 char"
            }
            else if(binding.etUsernameRegister.text.toString().length<=5){
                binding.etUsernameRegister.error = "At least more than 5 char"
            }
            else if(binding.etTeleponRegister.text.toString().length<=11){
                binding.etTeleponRegister.error = "At least more than 11 char"
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(
                    binding.etEmailRegister.text.toString()
                ).matches()){
                    binding.etEmailRegister.error = "Please use correct format"
            }
            else{
                binding.btnRegister.visibility=View.INVISIBLE
                viewModel.registerUser(User(
                    username = binding.etUsernameRegister.text.toString(),
                    email = binding.etEmailRegister.text.toString(),
                    telepon = binding.etTeleponRegister.text.toString()
                ),binding.etEmailRegister.text.toString(),
                    binding.etPasswordRegister.text.toString())
                Snackbar.make(binding.root,
                    "User Successfully created",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.isRegister.observe(viewLifecycleOwner){
            if(it){
                viewModel.resetRegisterStatus()
                findNavController().popBackStack()
            }
            else{
                binding.btnRegister.visibility=View.VISIBLE
            }
        }
        return binding.root
    }
}