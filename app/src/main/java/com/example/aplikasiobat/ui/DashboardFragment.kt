package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasiobat.R
import com.example.aplikasiobat.databinding.FragmentDashboardBinding
import com.example.aplikasiobat.databinding.FragmentLoginBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val email = arguments?.getString("email")
//        Log.d("email", "onViewCreated: $email")
//        binding.email.setText(email)
        val semuaFragment = SemuaFragment()
        binding.chipSemua.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.fragment_container, semuaFragment).commit()
        }
    }
}