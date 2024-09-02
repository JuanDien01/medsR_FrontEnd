package com.example.aplikasiobat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentDashboardBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory
import com.google.gson.Gson


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding?= null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = arguments?.getInt("userId")
        val fullName = arguments?.getString("Fullname")
        binding.edtFullName.text = fullName
        getObatPasienSemua(userId!!)
        binding.chipSemua.setOnClickListener {
            getObatPasienSemua(userId)
        }
    }

    private fun getObatPasienSemua(userId:Int) {
        mainViewModel.getObatPasien(userId).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.loading.visibility = View.GONE
                    binding.fragmentContainer.visibility = View.VISIBLE
                    resource.data?.let { obatPasienData ->
                        val gson = Gson()
                        val jsonString = gson.toJson(obatPasienData)
                        val bundle = Bundle().apply {
                            putString("obatPasienData", jsonString)
                        }
                        val semuaFragment = SemuaFragment().apply {
                            arguments = bundle
                        }
                        childFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, semuaFragment)
                            .commit()
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.fragmentContainer.visibility = View.GONE
                }
            }
        }
    }
}