package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentRegisterBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.masuk.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnDaftar.setOnClickListener {
//            it.findNavController().navigate(R.id.action_registerFragment_to_biodataFragment)
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.edtEmail.editText!!.text
        val password = binding.edtPassword.editText!!.text
        val confPassword = binding.edtConfirmPassword.editText!!.text

//        if (password != confPassword){
//            Toast.makeText(context, "Password Tidak Sama", Toast.LENGTH_SHORT).show()
//        }

        val registerRequest = RegisterRequest(
            username = email.toString(),
            password = password.toString(),
            roleId = 1,
            id_pengguna = 1
            // Add other fields as needed
        )

        mainViewModel.registerUser(registerRequest)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        // Handle successful registration
                        resource.data?.let { response ->
                            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("api", "registerUser: $response")
                            // Navigate to the next screen or perform other actions
                        }
                    }

                    Status.ERROR -> {
                        // Handle error
                        Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("api", "registerUser: ${resource.message}")
                    }

                    Status.LOADING -> {
                        // Show loading state
                        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }


}