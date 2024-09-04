package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasiobat.R
import com.example.aplikasiobat.adapter.ObatPasienAdapter
import com.example.aplikasiobat.api.response.dashboard.Data
import com.example.aplikasiobat.api.response.dashboard.GetObatPasienResponse
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.databinding.FragmentBelumDiminumBinding
import com.example.aplikasiobat.databinding.FragmentLoginBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory
import com.google.gson.Gson

class BelumDiminumFragment : Fragment() {
    private var _binding: FragmentBelumDiminumBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ObatPasienAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBelumDiminumBinding.inflate(inflater, container, false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("obatPasienDataBelumDiminum")?.let { jsonString ->
            val gson = Gson()
            val obatPasienData = gson.fromJson(jsonString, GetObatPasienResponse::class.java)

            // Check if the data is not null before initializing the RecyclerView
            obatPasienData?.data?.let { data ->
                if (data.isEmpty()) {
                    binding.noJadwal.visibility = View.VISIBLE
                    binding.RcyViewSemua.visibility = View.GONE
                } else {
                    initRecyclerView(data)
                    binding.noJadwal.visibility = View.GONE
                    binding.RcyViewSemua.visibility = View.VISIBLE
                }
            }

            Log.d("dataObatPasien", "Obat Pasien Data: $obatPasienData")
        }
    }

    private fun initRecyclerView(obatPasienList: List<Data>) {
        adapter = ObatPasienAdapter()
        binding.RcyViewSemua.layoutManager = LinearLayoutManager(context)
        binding.RcyViewSemua.adapter = adapter
        // Submit the list to the adapter
        adapter.submitList(obatPasienList)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}