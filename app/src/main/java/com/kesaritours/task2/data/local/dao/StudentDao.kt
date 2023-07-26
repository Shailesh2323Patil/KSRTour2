package com.kesaritours.task2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kesaritours.task2.data.local.entity.StudentEntity

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(studentEntity: StudentEntity) : Long

    @Query("SELECT * FROM student ORDER BY id ASC")
    suspend fun readStudentsData(): List<StudentEntity>
}