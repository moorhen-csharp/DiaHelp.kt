package dev.moorhen.diahelp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.view.fragments.CorrectionFragment
import dev.moorhen.diahelp.view.fragments.ProfileFragment
import dev.moorhen.diahelp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigationView)

        // Загружаем фрагмент по умолчанию
        if (savedInstanceState == null) {
            openFragment(CorrectionFragment())
        }

        // Подписка на выбор элемента нижней панели
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_correction -> {
                    openFragment(CorrectionFragment())
                    true
                }
                R.id.navigation_profile -> {
                    openFragment(ProfileFragment())
                    true
                }
                else -> {
                    // временно игнорируем другие кнопки
                    false
                }
            }
        }

        // Выделяем пункт меню "Коррекция"
        bottomNav.selectedItemId = R.id.navigation_correction
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
