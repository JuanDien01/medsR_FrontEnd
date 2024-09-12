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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.databinding.FragmentForgotPasswordEmailBinding

class ForgotPasswordEmailFragment : Fragment() {
    private var _binding: FragmentForgotPasswordEmailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordEmailBinding.inflate(inflater, container, false)
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

        val userId = arguments?.getInt("userId")
        val email = arguments?.getString("email")
//        Log.d("confirmEmail", "onViewCreated: $email $userId")
        // Apply the maskEmail function
        val maskedEmail = email?.let { maskEmail(it) }
        val emailUser = binding.confirmEmail.text


        binding.tvEmail.text = maskedEmail
        binding.btnSelanjutnyaEmail.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("userId", userId!!)
            }
            if (emailUser.toString() == email){
                findNavController().navigate(R.id.action_forgotPasswordEmailFragment_to_confirmNewPasswordFragment, bundle)
            }else{
                binding.emailErrorCard.visibility = View.VISIBLE
            }

        }

    }

    fun maskEmail(email: String): String {
        // Split email into parts
        val emailParts = email.split("@")

        if (emailParts.size != 2) return email // Return original email if format is incorrect

        val localPart = emailParts[0] // Part before '@'
        val domainPart = emailParts[1] // Part after '@'

        // Mask the local part, showing only the first character and replacing the rest with '*'
        val maskedLocalPart = if (localPart.length > 1) {
            localPart.first() + "*".repeat(localPart.length - 1)
        } else {
            localPart // If the local part is too short, don't mask
        }

        // Mask the domain part, keeping only the first and last character of the domain before '.'
        val domainSections = domainPart.split(".")
        val domainName = domainSections[0]
        val maskedDomainName = if (domainName.length > 1) {
            domainName.first() + "*".repeat(domainName.length - 1)
        } else {
            domainName // If domain name is too short, don't mask
        }

        val maskedDomainPart = maskedDomainName + "." + domainSections[1]

        return "$maskedLocalPart@$maskedDomainPart"
    }
}