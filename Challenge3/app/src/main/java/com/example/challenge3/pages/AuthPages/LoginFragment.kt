package com.example.challenge3.pages.AuthPages

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challenge3.R
import com.example.challenge3.util.viewmodelsfactory.PageViewModelFactory
import com.example.challenge3.databinding.FragmentLoginBinding
import com.example.challenge3.util.viewmodels.LoginViewModel
import com.example.challenge3.util.viewmodels.MainActivityViewModel

class LoginFragment : Fragment() {
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding:FragmentLoginBinding
    private var isPasswordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        mainViewModel.setVisibleBottomNav(false)
        viewModel = ViewModelProvider(
            requireActivity(),
            PageViewModelFactory(requireActivity().application)
            ).get(LoginViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        val textWatcher = object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                when(p0){
                    binding.etUsernameLogin.editableText->{
                        if(p0?.isEmpty() == false){
                            viewModel.setUsername(p0.toString())
                        }
                    }
                    binding.etPasswordLogin.editableText->viewModel.setPassword(p0.toString())
                }
            }

        }

        binding.etPasswordLogin.compoundDrawablesRelative[2].let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            binding.etPasswordLogin.setOnTouchListener{_, event->
                if(event.action == MotionEvent.ACTION_UP &&
                    event.rawX >= binding
                        .etPasswordLogin.right - it.bounds.width()){
                    if(!isPasswordVisible){
                        binding.etPasswordLogin
                            .inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
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

        binding.etUsernameLogin.setText(viewModel.username.value)
        binding.etUsernameLogin.addTextChangedListener(textWatcher)
        binding.etPasswordLogin.setText(viewModel.password.value)
        binding.etPasswordLogin.addTextChangedListener(textWatcher)

        binding.btnLogin.setOnClickListener{
            findNavController().popBackStack()
            mainViewModel.setVisibleBottomNav(true)
            findNavController().navigate(R.id.menuFragment)
        }

        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.registerFragment)
        }



        return binding.root
    }
}