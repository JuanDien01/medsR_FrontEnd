package com.example.aplikasiobat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentLoginBinding
import com.example.aplikasiobat.services.NotificationService
import com.example.aplikasiobat.viewmodel.DashboardViewModel
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buatAkun.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_biodataFragment)
        }
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUname.editText!!.text.toString()
            val pass = binding.edtPassword.editText!!.text.toString()
            if (username.isNotEmpty() && pass.isNotEmpty()){
                login(username,pass)
            }else{
                Toast.makeText(context, "Harap Isi Username Dan Password Dengan Benar", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun login(userName: String, password: String) {
        val loginData = LoginRequest(
            username = userName,
            password = password
        )
        mainViewModel.login(loginData).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                    val userId = resource.data?.data?.userId ?: 0
                    val fullName = resource.data?.data?.fullName ?: "Tamu"
                    dashboardViewModel.setUserData(userId, fullName)
                    // Start foreground service after successful login
                    val intent = Intent(requireContext(), NotificationService::class.java).apply {
                        putExtra("userId", userId)
                        putExtra("fullName", fullName)
                    }
                    requireContext().startService(intent)

                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }

                Status.ERROR -> {
                    Toast.makeText(context, "Error: Username atau Password Salah!", Toast.LENGTH_SHORT).show()
                    binding.btnLogin.isClickable = true
                    binding.lottieLoading.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.btnLogin.isClickable = false
                    binding.lottieLoading.visibility = View.VISIBLE
                }
            }
        }
    }
}