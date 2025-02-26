package com.example.wedonate.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DonationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonationViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
