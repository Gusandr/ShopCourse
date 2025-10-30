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

    @Query("SELECT * FROM ${CourseDb.COURSE_TABLE_NAME} WHERE hasLike = 1")
    fun getCoursesLiked(): Flow<List<CourseDb>>

    @Query("UPDATE ${CourseDb.COURSE_TABLE_NAME} SET hasLike = NOT hasLike WHERE id = :courseId")
    suspend fun toggleCourseLike(courseId: Int)

    @Query("SELECT hasLike FROM ${CourseDb.COURSE_TABLE_NAME} WHERE id = :courseId")
    suspend fun getCourseLikeStatus(courseId: Int): Boolean

    @Query("""
        INSERT OR REPLACE INTO ${CourseDb.COURSE_TABLE_NAME} 
        (id, title, text, price, rate, startDate, hasLike, publishDate)
        VALUES (
            :id, :title, :text, :price, :rate, :startDate, 
            COALESCE((SELECT hasLike FROM ${CourseDb.COURSE_TABLE_NAME} WHERE id = :id), :hasLike),
            :publishDate
        )
    """)
    suspend fun insertPreservingLike(
        id: Int,
        title: String,
        text: String,
        price: String,
        rate: Double,
        startDate: String,
        hasLike: Boolean,
        publishDate: String
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(courseDb: CourseDb)
}
