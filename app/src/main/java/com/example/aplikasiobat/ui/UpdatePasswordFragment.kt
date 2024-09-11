package com.example.aplikasiobat.ui

import android.os.Bundle
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
import com.example.aplikasiobat.api.request.UpdatePasswordRequest
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentUpdatePasswordBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory

class UpdatePasswordFragment : Fragment() {
    private var _binding: FragmentUpdatePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
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
        (activity as AppCompatActivity).supportActionBar?.title = "Ubah Kata Sandi"

        // Enable back button
        val navController = findNavController()
        toolbar.setupWithNavController(navController)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = arguments?.getString("username")
        binding.btnUbahKataSandiSettings.setOnClickListener {
            val kataSandiLama = binding.edtUpdatePassword.editText?.text.toString()
            val kataSandiBaru = binding.edtUpdateNewPassword.editText?.text.toString()
            val konfirmasiKataSandiBaru = binding.edtUpdateConfirmPassword.editText?.text.toString()
            if (kataSandiBaru == konfirmasiKataSandiBaru) {
                val updatePasswordRequest =
                    UpdatePasswordRequest( kataSandiBaru, kataSandiLama,username!!)
                updatePassword(updatePasswordRequest)
            }else{
                Toast.makeText(context, "Kata Sandi Baru Tidak Sama", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun updatePassword(updatePasswordRequest: UpdatePasswordRequest) {
        mainViewModel.updatePassword(updatePasswordRequest)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(context, "Sukses update Password", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_updatePasswordFragment_to_settingsFragment)
                    }

                    Status.ERROR -> {
                        Toast.makeText(context, "Kata Sandi Lama Salah", Toast.LENGTH_SHORT)
                            .show()
                    }

                    Status.LOADING -> {

                    }
                }
            }
    }
}