package ru.gusandr.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.gusandr.data.local.storage.dao.CourseDao
import ru.gusandr.data.mapper.CourseMapper
import ru.gusandr.data.remote.GetListCoursesApi
import ru.gusandr.domain.model.Course
import ru.gusandr.domain.repository.CourseRepository

class CourseRepositoryImpl(private val apiService: GetListCoursesApi, private val dao: CourseDao) :
    CourseRepository {
    override suspend fun getCourses(): Flow<List<Course>> {
        return dao.getAll().map { list ->
            list.map { course ->
                CourseMapper.toDomain(course)
            }
        }
    }

    override suspend fun getCoursesLiked(): Flow<List<Course>> {
        return dao.getCoursesLiked().map { list ->
            list.map { course ->
                CourseMapper.toDomain(course)
            }
        }
    }

    override suspend fun fetchCourses(): Flow<Result<Unit>> = flow {
        try {
            val response = apiService.getCourses()

            if (response.isSuccessful) {
                val coursesDto = response.body()?.courses ?: emptyList()
                val courses = CourseMapper.toDomain(coursesDto)

                courses.forEach { course ->
                    val courseDb = CourseMapper.toDb(course)
                    dao.insertPreservingLike(
                        id = courseDb.id,
                        title = courseDb.title,
                        text = courseDb.text,
                        price = courseDb.price,
                        rate = courseDb.rate,
                        startDate = courseDb.startDate,
                        hasLike = courseDb.hasLike,
                        publishDate = courseDb.publishDate
                    )
                }

                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception("HTTP ${response.code()}: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun toggleCourseLike(courseId: Int): Result<Boolean> {
        return try {
            val oldStatus = dao.getCourseLikeStatus(courseId)

            dao.toggleCourseLike(courseId)

            Result.success(!oldStatus)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}