package com.example.challenge3.page

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challenge3.R
import com.example.challenge3.databinding.FragmentLoginBinding
import com.example.challenge3.viewmodels.MainActivityViewModel

class LoginFragment : Fragment() {
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var binding:FragmentLoginBinding
    private var isPasswordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        mainViewModel.setVisibleBottomNav(false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.etPasswordLogin.compoundDrawablesRelative[2].let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            binding.etPasswordLogin.setOnTouchListener{_, event->
                if(event.action == MotionEvent.ACTION_UP && event.rawX >= binding.etPasswordLogin.right - it.bounds.width()){
                    if(!isPasswordVisible){
                        binding.etPasswordLogin.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        isPasswordVisible=true
                        return@setOnTouchListener true
                    }
                    isPasswordVisible=false
                    binding.etPasswordLogin.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                    return@setOnTouchListener true
                }
                false
            }
        }

        binding.btnLogin.setOnClickListener{
            findNavController().popBackStack()
            mainViewModel.setVisibleBottomNav(true)
            findNavController().navigate(R.id.menuFragment)
        }

        return binding.root
    }
}