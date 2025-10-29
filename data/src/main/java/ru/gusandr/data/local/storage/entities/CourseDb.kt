package ru.gusandr.data.local.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gusandr.data.local.storage.entities.CourseDb.Companion.COURSE_TABLE_NAME

@Entity(tableName = COURSE_TABLE_NAME)
data class CourseDb(
    @PrimaryKey
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
) {

    companion object {
        const val COURSE_TABLE_NAME = "course_table"
    }
}
