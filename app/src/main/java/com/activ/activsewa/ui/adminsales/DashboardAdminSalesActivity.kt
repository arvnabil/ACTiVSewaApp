package com.activ.activsewa.ui.adminsales

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.activ.activsewa.R
import com.activ.activsewa.databinding.ActivityDashboardAdminSalesBinding
import com.google.android.material.snackbar.Snackbar

class DashboardAdminSalesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminSalesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardAdminSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        supportActionBar?.hide()
        val navController = findNavController(R.id.nav_host_fragment_activity_dsahboard_admin_sales)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashbord_admin_sales, R.id.navigation_transaction, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}