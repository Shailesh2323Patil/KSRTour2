package com.kesaritours.task2.di

import android.content.Context
import androidx.room.Room
import com.kesaritours.task2.data.local.database.StudentDatabase
import com.kesaritours.task2.data.local.util.Converters
import com.kesaritours.task2.data.local.util.GsonParser
import com.kesaritours.task2.data.repository.StudentRepositoryImpl
import com.kesaritours.task2.domain.repository.StudentRepository
import com.kesaritours.task2.util.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudentModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): StudentDatabase {
        return Room.databaseBuilder(
            context,
            StudentDatabase::class.java,
            Constants.DATABASE_STUDENT
        ).addTypeConverter(Converters(GsonParser(Gson()))).build()
    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        database: StudentDatabase
    ) : StudentRepository {
        return StudentRepositoryImpl(studentDao = database.studentDao)
    }
}