package ru.gusandr.shopcourse.presentation.screens.courses.favorites

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
import ru.gusandr.shopcourse.databinding.FragmentFavoritesCoursesBinding
import ru.gusandr.shopcourse.presentation.navigation.collectNavigation
import ru.gusandr.shopcourse.presentation.screens.courses.adapter.CoursesAdapter

@AndroidEntryPoint
class FavoritesCoursesFragment : Fragment() {
    private var _binding: FragmentFavoritesCoursesBinding? = null
    private val binding
        get() = _binding?:throw Exception("binding is not be null!")

    private val viewModel: FavoritesCoursesViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectNavigation(viewModel.nav)
        setupRecycler()
        setupUI()

        view.post {
            binding.bottomNav.selectedItemId = R.id.nav_favorites
        }
    }

    private fun setupUI() {
        with(binding) {
            rvFavorites.adapter = coursesAdapter
            bottomNav.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_home -> {
                        viewModel.onSwitchingToList()
                        true
                    }
                    R.id.nav_favorites -> true
                    R.id.nav_account -> {
                        false
                    }
                    else -> false
                }
            }
        }
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