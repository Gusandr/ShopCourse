package ru.gusandr.shopcourse.presentation.screens.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.databinding.FragmentListCoursesBinding
@AndroidEntryPoint
class CoursesListFragment : Fragment() {
    private var _binding: FragmentListCoursesBinding? = null
    private val binding
        get() = _binding?:throw Exception("binding is not be null!")

    private val viewModel: CoursesListViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        viewModel.fetchCourses()
        viewModel.getCourses()
    }

    private fun setupUI() {
        with(binding) {
            bottomNav.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_home -> true
                    R.id.nav_favorites -> {
                        //findNavController().navigate(R.id.action_to_favorites)
                        true
                    }
                    R.id.nav_account -> {
                        //findNavController().navigate(R.id.action_to_account)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}