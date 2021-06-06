package com.warlock.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.warlock.newsapp.R
import com.warlock.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var appToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        appToolbar = mBinding.toolbar
        setUpNavController()
    }

    private fun setUpNavController() {
        val navController = findNavController(R.id.mainNavHost)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.newsSourceFragment))
        mBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(mBinding.toolbar)
        navController.addOnDestinationChangedListener { _, destination, _ ->
        }
    }
}