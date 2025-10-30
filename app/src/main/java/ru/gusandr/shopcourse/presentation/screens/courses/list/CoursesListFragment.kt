package ru.gusandr.shopcourse.presentation.screens.courses.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.databinding.FragmentListCoursesBinding
import ru.gusandr.shopcourse.presentation.navigation.collectNavigation
import ru.gusandr.shopcourse.presentation.screens.courses.adapter.CoursesAdapter

@AndroidEntryPoint
class CoursesListFragment : Fragment() {

    private var _binding: FragmentListCoursesBinding? = null
    private val binding get() = _binding?:throw Exception("binding is not be null!")

    private val viewModel: CoursesListViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectNavigation(viewModel.nav)
        setupRecycler()
        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            rvCourses.adapter = coursesAdapter

            sortSection.setOnClickListener {
                viewModel.switchSort()
            }

            bottomNav.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_home -> true
                    R.id.nav_favorites -> {
                        viewModel.onSwitchingToFavorites()
                        false
                    }
                    R.id.nav_account -> {
                        false
                    }
                    else -> false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.selectedItemId = R.id.nav_home
    }

    private fun setupRecycler() {
        coursesAdapter = CoursesAdapter(viewModel::switchLikeCourse)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.courses.collect {
                    coursesAdapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}