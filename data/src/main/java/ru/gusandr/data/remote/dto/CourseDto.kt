package ru.gusandr.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)

@Serializable
data class CoursesResponse(
    val courses: List<CourseDto>
)