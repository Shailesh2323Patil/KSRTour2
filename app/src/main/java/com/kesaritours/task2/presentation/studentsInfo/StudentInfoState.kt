package com.kesaritours.task2.presentation.studentsInfo

import com.kesaritours.task2.domain.model.Student

data class StudentInfoState(
    val studentInfoItem: List<Student> = emptyList(),
    val isLoading: Boolean = false ,
    val addStudent: Boolean = false
)
