package com.example.aplikasiobat.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.aplikasiobat.api.response.dashboard.detailObatPasien.Data

class JamMinumDiffCallback: DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.idObatPasien == newItem.idObatPasien
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}