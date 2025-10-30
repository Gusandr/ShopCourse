package ru.gusandr.shopcourse.presentation.screens.courses.favorites

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
import ru.gusandr.shopcourse.presentation.screens.courses.list.CoursesListFragmentDirections
import javax.inject.Inject

@HiltViewModel
class FavoritesCoursesViewModel @Inject constructor(
    private val repository: CourseRepository
): ViewModel() {

    private val _nav = Channel<NavCommand>(capacity = Channel.BUFFERED)
    val nav: Flow<NavCommand> = _nav.receiveAsFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    init {
        getCoursesLiked()
        fetchCourses()
    }

    fun onSwitchingToList() {
        viewModelScope.launch {
            _nav.send(NavCommand.To(FavoritesCoursesFragmentDirections.actionFavoritesCoursesToList()))
        }
    }

    fun switchLikeCourse(courseId: Int) {
        viewModelScope.launch {
            repository.toggleCourseLike(courseId)
            getCoursesLiked()
        }
    }

    private fun getCoursesLiked() {
        viewModelScope.launch {
            repository
                .getCoursesLiked()
                .distinctUntilChanged()
                .collect { favoritesList ->
                    _courses.value = favoritesList
                }
        }
    }

    private fun fetchCourses() {
        viewModelScope.launch {
            repository.fetchCourses().collect { result ->
                if (result.isSuccess) {
                    getCoursesLiked()
                }
            }
        }
    }
}