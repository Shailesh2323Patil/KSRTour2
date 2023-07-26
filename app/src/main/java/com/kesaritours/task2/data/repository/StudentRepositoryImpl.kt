package com.kesaritours.task2.data.repository

import com.kesaritours.task2.data.local.dao.StudentDao
import com.kesaritours.task2.data.local.entity.StudentEntity
import com.kesaritours.task2.domain.model.Student
import com.kesaritours.task2.domain.repository.StudentRepository
import com.kesaritours.task2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class StudentRepositoryImpl(
    private val studentDao: StudentDao
) : StudentRepository {

    override fun insertStudentData(studentEntity: StudentEntity): Flow<Resource<List<Student>>> = flow {
        emit(Resource.Loading())

        try {
            studentDao.insertStudent(studentEntity)

            var data2 = studentDao.readStudentsData().map { it.toStudent() }
            emit(Resource.Success(data = data2))
        }
        catch (exception : Exception) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = null
                )
            )
        }
    }

    override fun fetchStudentInfo(): Flow<Resource<List<Student>>> = flow {
        emit(Resource.Loading())

        try {
            var data = studentDao.readStudentsData().map { it.toStudent() }
            emit(Resource.Success(data = data))
        }
        catch (exception : Exception) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = null
                )
            )
        }
    }
}