package ru.gusandr.domain.usecase

import ru.gusandr.domain.model.Course
import ru.gusandr.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow

class GetCoursesUseCase(
    private val courseRepository: CourseRepository
) {
    suspend fun execute(): Flow<List<Course>> {
        return courseRepository.getCourses()
    }
}