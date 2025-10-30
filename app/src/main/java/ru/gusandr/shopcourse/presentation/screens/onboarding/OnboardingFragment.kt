package ru.gusandr.shopcourse.presentation.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.gusandr.data.local.AuthPreferences
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.databinding.FragmentOnboardingBinding
import ru.gusandr.shopcourse.presentation.navigation.collectNavigation
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding
        get() = _binding?:throw Exception("binding is not be null!")

    private val viewModel: OnboardingViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    @Inject
    lateinit var authPreferences: AuthPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectNavigation(viewModel.nav)
        if (authPreferences.isLoggedIn()) {
            viewModel.switchToListCourses()
        }

        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            btnContinue.setOnClickListener {
                viewModel.onContinueClicked()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}