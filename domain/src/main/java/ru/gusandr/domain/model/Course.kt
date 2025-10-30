package ru.gusandr.domain.model

import java.time.LocalDate

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: LocalDate,
    val hasLike: Boolean,
    val publishDate: LocalDate
) : Comparable<Course> {

    override fun compareTo(other: Course): Int {
        return other.publishDate.compareTo(this.publishDate)
    }
}
