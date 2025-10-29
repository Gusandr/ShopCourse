package ru.gusandr.data.remote

import ru.gusandr.data.remote.dto.CoursesResponse
import retrofit2.Response
import retrofit2.http.*

interface GetListCoursesApi {
    @GET("/u/0/uc")
    suspend fun getCourses(
        @Query("id") id: String = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q",
        @Query("export") export: String = "download"
    ): Response<CoursesResponse>
}