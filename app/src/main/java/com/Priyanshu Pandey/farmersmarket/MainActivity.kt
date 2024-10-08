package com.example.kisaanconnect

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.example.kisaanconnect.data.repository.MESSAGE_NOTIFICATION_CHANNEL_ID
import com.example.kisaanconnect.utils.NetworkAvailabilityUtils


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    val tag = "MainActivity"

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        createNotificationChannel()

        toolbar = findViewById(R.id.toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
                R.id.navigation_home, R.id.navigation_message, R.id.preferenceFragment,
                R.id.navigation_pager -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    toolbar.visibility = View.GONE
                }

                R.id.splashFragment, R.id.chatFragment, R.id.loginFragment2, R.id.signUpFragment2 -> {
                    bottomNavigationView.visibility = View.GONE
                    toolbar.visibility = View.GONE
                }

                else -> {
                    bottomNavigationView.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                }
            }
        }

        NetworkAvailabilityUtils.setNetworkAvailabilityListener(this){

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    
    private fun verifyUserLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid

        if(uid == null){
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

        }else{
           val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_message, R.id.preferenceFragment))
       }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(MESSAGE_NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}