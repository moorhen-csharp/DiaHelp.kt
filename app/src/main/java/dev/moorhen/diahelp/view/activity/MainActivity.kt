package dev.moorhen.diahelp.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.view.fragments.CalculatorContainerFragment
import dev.moorhen.diahelp.view.fragments.ProfileFragment
import dev.moorhen.diahelp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("app_settings", MODE_PRIVATE)
        val isDarkTheme = prefs.getBoolean("dark_theme", false)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigationView)

        if (savedInstanceState == null) {
            openFragment(CalculatorContainerFragment())
        }

        bottomNav.selectedItemId = R.id.navigation_correction

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_correction -> {
                    openFragment(CalculatorContainerFragment())
                    true
                }
                R.id.navigation_profile -> {
                    openFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
