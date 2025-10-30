package ru.gusandr.shopcourse.presentation.screens.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.databinding.FragmentListCoursesBinding
import ru.gusandr.shopcourse.presentation.screens.courses.adapter.CoursesAdapter

@AndroidEntryPoint
class CoursesListFragment : Fragment() {

    private var _binding: FragmentListCoursesBinding? = null
    private val binding get() = _binding?:throw Exception("binding is not be null!")

    private val viewModel: CoursesListViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val coursesAdapter = CoursesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCourses.adapter = coursesAdapter

        binding.sortSection.setOnClickListener {
            viewModel.switchSort()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courses.collect { courses ->
                coursesAdapter.submitList(null)
                coursesAdapter.submitList(courses)
            }
        }

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> true
                R.id.nav_favorites -> true
                R.id.nav_account -> true
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}