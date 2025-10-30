package ru.gusandr.shopcourse.presentation.screens.courses.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.gusandr.domain.model.Course
import ru.gusandr.domain.repository.CourseRepository
import ru.gusandr.shopcourse.presentation.navigation.NavCommand
import javax.inject.Inject

@HiltViewModel
class CoursesListViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    private val _nav = Channel<NavCommand>(capacity = Channel.BUFFERED)
    val nav: Flow<NavCommand> = _nav.receiveAsFlow()

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

    fun switchLikeCourse(courseId: Int) {
        viewModelScope.launch {
            repository.toggleCourseLike(courseId)
        }
    }

    fun onSwitchingToFavorites() {
        viewModelScope.launch {
            _nav.send(NavCommand.To(CoursesListFragmentDirections.actionListCoursesToFavorites()))
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