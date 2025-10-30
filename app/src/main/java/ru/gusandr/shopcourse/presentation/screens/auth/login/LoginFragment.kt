package ru.gusandr.shopcourse.presentation.screens.auth.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gusandr.data.local.AuthPreferences
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.databinding.FragmentLoginBinding
import ru.gusandr.shopcourse.presentation.navigation.collectNavigation
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding?:throw Exception("binding is not be null!")

    private val viewModel: LoginViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    @Inject
    lateinit var authPreferences: AuthPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
        collectNavigation(viewModel.nav)
    }

    private fun setupUI() {
        with(binding) {
            etEmail.addTextChangedListener { email ->
                viewModel.onEmailChanged(email.toString())
            }

            etPassword.addTextChangedListener { password ->
                viewModel.onPasswordChanged(password.toString())
            }

            btnLogin.setOnClickListener {
                viewModel.onLoginClick()
            }

            tvRegistration.setOnClickListener {
                viewModel.onSwitchToRegistration()
            }

            btnVk.setOnClickListener {
                openSocialAuth(viewModel.getVkAuthUrl())
            }
            btnOk.setOnClickListener {
                openSocialAuth(viewModel.getOkAuthUrl())
            }
        }
    }

    private fun openSocialAuth(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(requireContext(), "Не открывается $url, плаки плаки ;(", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.btnLogin.isEnabled = state.isButtonEnabled && !state.isLoading

                // isLoading - тута

                if (state.isSuccess)
                    authPreferences.setLoggedIn(true, state.email)
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}