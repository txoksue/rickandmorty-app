package com.paradigma.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.paradigma.rickandmorty.R
import dagger.hilt.android.AndroidEntryPoint
import com.paradigma.rickandmorty.domain.Character

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{ navController: NavController, navDestination: NavDestination, bundle: Bundle? ->

            when (navDestination.id) {
                R.id.splashFragment -> {
                    showToolbar(show = false)
                }
                R.id.charactersFragment -> {
                    setStatusColor(R.color.secondaryColor)
                    supportActionBar?.let { actionBar ->
                        actionBar.setHomeButtonEnabled(false)
                        actionBar.setDisplayHomeAsUpEnabled(false)
                        actionBar.setDisplayShowHomeEnabled(false)
                    }
                    showToolbar(show = true)
                    setToolbarTitle(getString(R.string.app_name))
                }
                R.id.characterDetailFragment -> {
                    showToolbar(show = true)
                    val character = bundle?.getParcelable<Character>("character") as Character
                    setToolbarTitle("${character.name}'s location")
                }
                R.id.favoritesFragment -> {
                    showToolbar(show = true)
                    setToolbarTitle(getString(R.string.favorites_title))
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    fun setToolbarTitle(title: String) {
        findViewById<Toolbar>(R.id.toolbar).title = title
    }

    fun showToolbar(show: Boolean) {
        findViewById<Toolbar>(R.id.toolbar).visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setStatusColor(color: Int){
        window.statusBarColor = getColor(color)
    }
}