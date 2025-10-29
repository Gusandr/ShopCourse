package ru.gusandr.shopcourse.presentation.screens.courses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.gusandr.domain.repository.CourseRepository
import javax.inject.Inject

@HiltViewModel
class CoursesListViewModel @Inject constructor(private val repository: CourseRepository) : ViewModel() {
    fun getCourses() {
        viewModelScope.launch {
            repository.getCourses().distinctUntilChanged().collect {
                Log.d("Room", "$it")
            }
        }
    }

    fun fetchCourses() {
        viewModelScope.launch {
            repository.fetchCourses().collect {

            }
        }
    }
}