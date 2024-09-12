package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.request.ForgotPasswordRequest
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentConfirmNewPasswordBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory

class ConfirmNewPasswordFragment : Fragment() {
    private var _binding: FragmentConfirmNewPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmNewPasswordBinding.inflate(inflater, container, false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Find the Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        // Set the Toolbar as the support action bar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Set the custom title
        (activity as AppCompatActivity).supportActionBar?.title = "Buat Kata Sandi Baru"

        // Enable back button
        val navController = findNavController()
        toolbar.setupWithNavController(navController)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val userId = arguments?.getInt("userId")
        binding.btnConfirmNewPassword.setOnClickListener {
//            Log.d("userId", "onViewCreated: $userId")
            val newPassword = binding.edtUpdateNewPassword.editText?.text.toString()
            val confirmNewPassword = binding.edtUpdatePassword.editText?.text.toString()
            if (newPassword == confirmNewPassword){
                newPassword(ForgotPasswordRequest(newPassword, userId!!))
            }else{
                Toast.makeText(context, "Kata Sandi Tidak Sama", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun newPassword(forgotPasswordRequest: ForgotPasswordRequest) {
        mainViewModel.forgotPassword(forgotPasswordRequest)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        findNavController().navigate(R.id.action_confirmNewPasswordFragment_to_confirmNewPasswordSuccessFragment)
                        Toast.makeText(context, "Berhasil Ubah Kata Sandi", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Harap coba lagi", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
    }
}