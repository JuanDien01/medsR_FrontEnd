package com.example.aplikasiobat.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.aplikasiobat.api.response.dashboard.ObatPasien.Data

class ObatPasienDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        // Compare unique IDs or other unique identifiers
        return oldItem.idObatPasien == newItem.idObatPasien
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        // Compare the content of items
        return oldItem == newItem
    }
}