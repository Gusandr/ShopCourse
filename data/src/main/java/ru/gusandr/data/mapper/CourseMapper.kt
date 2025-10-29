package ru.gusandr.data.mapper

import ru.gusandr.data.local.storage.entities.CourseDb
import ru.gusandr.data.remote.dto.CourseDto
import ru.gusandr.domain.model.Course
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CourseMapper {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun toDomain(dto: CourseDto): Course {
        return Course(
            id = dto.id,
            title = dto.title,
            text = dto.text,
            price = dto.price,
            rate = dto.rate.toDoubleOrNull() ?: 0.0,
            startDate = LocalDate.parse(dto.startDate, dateFormatter),
            hasLike = dto.hasLike,
            publishDate = LocalDate.parse(dto.publishDate, dateFormatter)
        )
    }

    fun toDomain(dtoList: List<CourseDto>): List<Course> {
        return dtoList.map { toDomain(it) }
    }

    fun toDomain(dto: CourseDb): Course {
        return Course(
            id = dto.id,
            title = dto.title,
            text = dto.text,
            price = dto.price,
            rate = dto.rate,
            startDate = LocalDate.parse(dto.startDate, dateFormatter),
            hasLike = dto.hasLike,
            publishDate = LocalDate.parse(dto.publishDate, dateFormatter)
        )
    }

    fun toDb(course: Course): CourseDb {
        return CourseDb(
            id = course.id,
            title = course.title,
            text = course.text,
            price = course.price,
            rate = course.rate,
            startDate = course.startDate.toString(),
            hasLike = course.hasLike,
            publishDate = course.publishDate.toString()
        )
    }
}