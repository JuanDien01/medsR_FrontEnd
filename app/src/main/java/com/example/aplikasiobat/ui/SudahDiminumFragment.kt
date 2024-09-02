package com.example.aplikasiobat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasiobat.R
import com.example.aplikasiobat.databinding.FragmentBelumDiminumBinding
import com.example.aplikasiobat.databinding.FragmentSudahDiminumBinding

class SudahDiminumFragment : Fragment() {
    private var _binding: FragmentSudahDiminumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSudahDiminumBinding.inflate(inflater, container, false)
        return binding.root
    }

}