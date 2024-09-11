package com.example.aplikasiobat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.databinding.FragmentForgotPasswordEmailBinding
import com.example.aplikasiobat.databinding.FragmentForgotPasswordUsernameBinding

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
    }
}