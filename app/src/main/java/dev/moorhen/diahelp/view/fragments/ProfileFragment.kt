package dev.moorhen.diahelp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.view.activity.AuthorizationActivity
import dev.moorhen.diahelp.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val userName = view.findViewById<TextView>(R.id.tvUserName)
        val userEmail = view.findViewById<TextView>(R.id.tvUserEmail)
        val logoutButton = view.findViewById<Button>(R.id.btnLogout)
        val themeSwitch = view.findViewById<Switch>(R.id.themeSwitch)
        val coeff = view.findViewById<TextView>(R.id.tvUserCoeffIns)

        // Загружаем текущую тему из SharedPreferences
        val isDarkMode = viewModel.isDarkThemeEnabled(requireContext())
        themeSwitch.isChecked = isDarkMode

        // 🛠️ Блокируем передачу клика нижней навигации
        themeSwitch.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            false // возвращаем false, чтобы Switch всё равно сработал
        }

        // 🎨 Переключение темы
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Сохраняем выбор
            viewModel.saveThemePreference(requireContext(), isChecked)

            // Применяем тему без перезапуска активити
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // 👤 Отображаем данные пользователя
        userName.text = viewModel.getUserName()
        userEmail.text = viewModel.getUserEmail()


        // 🚪 Обработка выхода из профиля
        logoutButton.setOnClickListener {
            viewModel.onLogoutClicked()
        }

        // 🔁 Наблюдаем за событием выхода
        viewModel.logout.observe(viewLifecycleOwner) { shouldLogout ->
            if (shouldLogout) {
                startActivity(Intent(requireContext(), AuthorizationActivity::class.java))
                requireActivity().finish()
            }
        }

        return view
    }
}
