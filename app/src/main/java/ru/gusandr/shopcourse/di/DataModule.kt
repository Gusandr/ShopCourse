package ru.gusandr.shopcourse.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.gusandr.data.local.AuthPreferences
import ru.gusandr.data.local.storage.dao.CourseDao
import ru.gusandr.data.local.storage.database.AppDatabase
import ru.gusandr.data.local.storage.database.AppDatabase.Companion.DATABASE_NAME
import ru.gusandr.data.remote.GetListCoursesApi
import ru.gusandr.data.repository.CourseRepositoryImpl
import ru.gusandr.domain.repository.CourseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideCourseRepository(getListCoursesApi: GetListCoursesApi, dao: CourseDao): CourseRepository {
        return CourseRepositoryImpl(getListCoursesApi, dao)
    }

    @Provides
    @Singleton
    fun provideAuthPreferences(@ApplicationContext context: Context): AuthPreferences {
        return AuthPreferences(context)
    }

    @Provides
    @Singleton
    fun provideGetListCoursesApi(retrofit: Retrofit): GetListCoursesApi {
        return retrofit.create(GetListCoursesApi::class.java)
    }

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) =
        databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTotalDao(db: AppDatabase): CourseDao = db.courseDao()

    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val contentType = "application/json".toMediaType()
        return Retrofit
            .Builder()
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType).apply {

            })
            .baseUrl(BASE_URL)
            .build()
    }

    val BASE_URL = "https://drive.usercontent.google.com/"
}