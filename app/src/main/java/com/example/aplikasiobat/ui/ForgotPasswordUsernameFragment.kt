package com.example.aplikasiobat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentDetailReminderBinding
import com.example.aplikasiobat.databinding.FragmentForgotPasswordUsernameBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory

class ForgotPasswordUsernameFragment : Fragment() {
    private var _binding: FragmentForgotPasswordUsernameBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordUsernameBinding.inflate(inflater, container, false)
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
        (activity as AppCompatActivity).supportActionBar?.title = "Lupa Kata Sandi"

        // Enable back button
        val navController = findNavController()
        toolbar.setupWithNavController(navController)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSelanjutnyaUsername.setOnClickListener {
            val username = binding.edtUsernameForgotPassword.editText?.text.toString()
            if (username.isNotEmpty()) {
                findUsername(username)
            }
        }
    }
    private fun findUsername(username : String){
        mainViewModel.findUsername(username).observe(viewLifecycleOwner){ resource ->
            when (resource.status){
                Status.SUCCESS -> {
                    val bundle = Bundle().apply {
                        putInt("userId", resource.data?.data?.userId ?: 0)
                        putString("email", resource.data?.data?.biodata?.email ?: "")
                    }
                    findNavController().navigate(R.id.action_forgotPasswordUsernameFragment_to_forgotPasswordEmailFragment, bundle)
                }
                Status.ERROR -> {
                    binding.usernameNotFoundCard.visibility = View.VISIBLE
                }
                Status.LOADING -> {

                }
            }
        }
    }
}