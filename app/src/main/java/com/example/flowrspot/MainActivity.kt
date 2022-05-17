package com.example.flowrspot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.flowrspot.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        viewBinding.bnvMenu.setupWithNavController(navController)

        // Setup the ActionBar with NavController and 2 top level destinations
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.home_fragment,
//                R.id.item2_fragment,
//                R.id.item3_fragment,
//                R.id.item4_fragment
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
//        navController.navigateUp(appBarConfiguration)
        navController.navigateUp()
    }
}