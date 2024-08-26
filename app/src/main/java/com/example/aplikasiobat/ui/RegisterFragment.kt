package com.example.aplikasiobat.ui

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
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentRegisterBinding
import com.example.aplikasiobat.viewmodel.AuthViewModel
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDaftar.setOnClickListener {
            getBiodata()
        }
    }

    private fun getBiodata() {
        authViewModel.biodata.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                val biodata = RegisterUserBiodataRequest(
                    namaPengguna = data.nama_pengguna,
                    tanggalLahir = data.tanggal_lahir,
                    jenisKelamin = data.jenis_kelamin,
                    alamat = data.alamat,
                    nomorTelfon = data.no_telfon,
                    email = data.email
                )
                mainViewModel.registerUserBiodata(biodata).observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { response ->
                                val idPengguna = response.data.idPengguna
                                registerUser(idPengguna)
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                            Log.d("api", "registerUserBiodata: ${resource.message}")
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            } else {
                // Handle case when data is null
                Toast.makeText(context, "Biodata not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(idPengguna: Int) {
        val email = binding.edtUname.editText?.text.toString()
        val password = binding.edtPassword.editText?.text.toString()
        val confPassword = binding.edtConfirmPassword.editText?.text.toString()

        if (password != confPassword) {
            Toast.makeText(context, "Password Tidak Sama", Toast.LENGTH_SHORT).show()
            return
        }

        val registerRequest = RegisterRequest(
            username = email,
            password = password,
            roleId = 1,
            id_pengguna = idPengguna
        )

        mainViewModel.registerUser(registerRequest).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    Log.d("api", "registerUser: ${resource.data}")
                    // Navigate to the next screen or perform other actions
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
//                    binding.loading.visibility = View.GONE
//                    binding.linearLayout5.visibility = View.VISIBLE
//                    binding.linearLayout6.visibility = View.VISIBLE
                    Log.d("api", "registerUser: ${resource.message}")
                }
                Status.LOADING -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
//                    binding.loading.visibility = View.VISIBLE
//                    binding.linearLayout5.visibility = View.GONE
//                    binding.linearLayout6.visibility = View.GONE
                }
            }
        }
    }
}