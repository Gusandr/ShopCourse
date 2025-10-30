package ru.gusandr.shopcourse.presentation.screens.courses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.gusandr.domain.model.Course
import ru.gusandr.domain.repository.CourseRepository
import javax.inject.Inject

@HiltViewModel
class CoursesListViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    private var originalCourses: List<Course> = emptyList()
    private var isAscending = false

    init {
        getCourses()
        fetchCourses()
    }

    fun switchSort() {
        isAscending = !isAscending

        _courses.value = if (isAscending) {
            originalCourses.sorted()
        } else {
            originalCourses.sortedDescending()
        }
    }


    private fun getCourses() {
        viewModelScope.launch {
            repository
                .getCourses()
                .distinctUntilChanged()
                .collect { coursesList ->
                    originalCourses = coursesList
                    _courses.value = if (isAscending) {
                        coursesList.sorted()
                    } else {
                        coursesList.sortedDescending()
                    }
                }
        }
    }

    private fun fetchCourses() {
        viewModelScope.launch {
            repository.fetchCourses().collect {

            }
        }
    }
}