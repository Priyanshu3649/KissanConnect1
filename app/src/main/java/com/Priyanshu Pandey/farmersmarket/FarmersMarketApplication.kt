package com.example.kisaanconnect

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.kisaanconnect.utils.NightMode
import com.jakewharton.threetenabp.AndroidThreeTen
import java.util.*

class FarmersMarketApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark_mode),
            getString(R.string.pref_dark_theme_off)
        )?.apply {
            val mode = NightMode.valueOf(this.toUpperCase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }
    }

    companion object {
        val requestOption by lazy {
            RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        }
    }
}