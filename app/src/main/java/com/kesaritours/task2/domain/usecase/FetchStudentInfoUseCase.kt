package com.kesaritours.task2.domain.usecase

import com.kesaritours.task2.domain.model.Student
import com.kesaritours.task2.domain.repository.StudentRepository
import com.kesaritours.task2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchStudentInfoUseCase @Inject constructor(
    private val repository: StudentRepository
) {
    operator fun invoke() : Flow<Resource<List<Student>>> {
        return repository.fetchStudentInfo()
    }
 }