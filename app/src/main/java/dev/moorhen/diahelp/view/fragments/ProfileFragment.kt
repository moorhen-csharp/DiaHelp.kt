package dev.moorhen.diahelp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val userName = view.findViewById<TextView>(R.id.tvUserName)
        val userEmail = view.findViewById<TextView>(R.id.tvUserEmail)
        val logoutButton = view.findViewById<Button>(R.id.btnLogout)

        // Заполняем данные пользователя
        userName.text = viewModel.getUserName()
        userEmail.text = viewModel.getUserEmail()

        // Обработка выхода
        logoutButton.setOnClickListener {
            viewModel.onLogoutClicked()
        }

        // Наблюдаем за событием выхода
        viewModel.logout.observe(viewLifecycleOwner) { shouldLogout ->
            if (shouldLogout) {
                val intent = Intent(requireContext(), AuthorizationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        return view
    }
}
