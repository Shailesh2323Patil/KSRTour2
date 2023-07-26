package com.kesaritours.task2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kesaritours.task2.data.local.dao.StudentDao
import com.kesaritours.task2.data.local.entity.StudentEntity
import com.kesaritours.task2.data.local.util.Converters

@Database(
    entities = [StudentEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class StudentDatabase : RoomDatabase() {
    abstract val studentDao: StudentDao
}