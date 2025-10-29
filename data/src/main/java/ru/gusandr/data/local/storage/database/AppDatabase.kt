package ru.gusandr.data.local.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gusandr.data.local.storage.dao.CourseDao
import ru.gusandr.data.local.storage.entities.CourseDb

@Database(
    entities = [CourseDb::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao

    companion object {
        const val DATABASE_NAME = "courses.db"
    }
}
