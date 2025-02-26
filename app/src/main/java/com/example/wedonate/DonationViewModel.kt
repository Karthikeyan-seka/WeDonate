package com.example.wedonate.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DonationViewModel(context: Context) : ViewModel() {

    private val _donationPoints = MutableStateFlow(0)
    val donationPoints: StateFlow<Int> = _donationPoints

    init {
        loadDonationPoints(context)
    }

    private fun loadDonationPoints(context: Context) {
        val sharedPref = context.getSharedPreferences("WeDonatePrefs", Context.MODE_PRIVATE)
        _donationPoints.value = sharedPref.getInt("donation_points", 0)
    }

    fun addDonationPoints(context: Context, points: Int) {
        viewModelScope.launch {
            val newPoints = _donationPoints.value + points
            _donationPoints.value = newPoints

            val sharedPref = context.getSharedPreferences("WeDonatePrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putInt("donation_points", newPoints)
                apply()
            }
        }
    }
}
