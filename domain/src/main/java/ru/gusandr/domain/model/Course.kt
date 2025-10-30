package ru.gusandr.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
    val displayStartDate: String
        get() = startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    val displayPublishDate: String
        get() = publishDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    val displayRate: String
        get() = String.format(Locale.getDefault(),"%.1f", rate)

    override fun compareTo(other: Course): Int {
        return other.publishDate.compareTo(this.publishDate)
    }
}
