package com.kesaritours.task2.domain.repository

import com.kesaritours.task2.data.local.entity.StudentEntity
import com.kesaritours.task2.domain.model.Student
import com.kesaritours.task2.util.Resource
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    fun insertStudentData(studentEntity: StudentEntity) : Flow<Resource<List<Student>>>

    fun fetchStudentInfo() : Flow<Resource<List<Student>>>
}