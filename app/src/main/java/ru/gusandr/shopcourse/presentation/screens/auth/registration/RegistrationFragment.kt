package ru.gusandr.shopcourse.presentation.screens.auth.registration

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
import ru.gusandr.shopcourse.databinding.FragmentRegistrationBinding
import ru.gusandr.shopcourse.presentation.navigation.collectNavigation
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding
        get() = _binding?:throw Exception("binding is not be null!")

    private val viewModel: RegistrationViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    @Inject
    lateinit var authPreferences: AuthPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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

            etConfirmPassword.addTextChangedListener { confirmPassword ->
                viewModel.onConfirmPasswordChanged(confirmPassword.toString())
            }

            btnRegister.setOnClickListener {
                viewModel.onRegisterClick()
            }

            tvLogin.setOnClickListener {
                // TODO: когда 2 экранчик сделаю подключить переходик было бы неплохо
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
                binding.btnRegister.isEnabled = state.isButtonEnabled && !state.isLoading

                // тута можно реализовать показ состояния загрузки (isLoading)

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