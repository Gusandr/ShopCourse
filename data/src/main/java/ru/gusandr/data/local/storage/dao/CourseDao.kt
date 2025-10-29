package ru.gusandr.data.local.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gusandr.data.local.storage.entities.CourseDb

@Dao
interface CourseDao {

    @Query("SELECT * FROM ${CourseDb.COURSE_TABLE_NAME}")
    fun getAll(): Flow<List<CourseDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(courseDb: CourseDb)
}
