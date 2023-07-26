package com.kesaritours.task2.domain.model

data class Student(
    var id: Int = 0,
    var name: String,
    var course: String,
    var subjects: List<Subject>,
    var totalMarks: Int,
    var averageMarks: Int = 0,
)




