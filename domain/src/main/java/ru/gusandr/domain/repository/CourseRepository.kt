package ru.gusandr.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.gusandr.domain.model.Course

interface CourseRepository {
    suspend fun getCourses(): Flow<List<Course>>
    suspend fun getCoursesLiked(): Flow<List<Course>>
    suspend fun fetchCourses(): Flow<Result<Unit>>
    suspend fun toggleCourseLike(courseId: Int): Result<Boolean>
}