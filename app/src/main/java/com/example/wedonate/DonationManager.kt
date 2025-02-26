package com.example.wedonate.utils

import android.content.Context
import android.content.SharedPreferences

object DonationManager {
    private const val PREFS_NAME = "donation_prefs"
    private const val KEY_DONATION_POINTS = "donation_points"

    fun getDonationPoints(context: Context): Int {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_DONATION_POINTS, 0) // Default = 0
    }

    fun addDonationPoints(context: Context, points: Int) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentPoints = getDonationPoints(context)
        prefs.edit().putInt(KEY_DONATION_POINTS, currentPoints + points).apply()
    }
}
