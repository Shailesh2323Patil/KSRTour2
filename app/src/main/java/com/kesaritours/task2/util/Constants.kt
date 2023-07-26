package com.kesaritours.task2.util

import com.kesaritours.task2.domain.model.Course
import com.kesaritours.task2.domain.model.Subject

object Constants {
    const val DATABASE_STUDENT = "student.db"
    const val TABLE_STUDENT = "student"

    val listCourse : List<Course> = listOf(
        Course(course = "CSE", listOf(
            Subject(subject = "C Language"),
            Subject(subject = "Java"),
            Subject(subject = "Data Structure")
        )),
        Course(course = "ECE", listOf(
            Subject(subject = "Digital Electronics"),
            Subject(subject = "Maths"),
            Subject(subject = "Microprocessor")
        )),
        Course(course = "Mech", listOf(
            Subject(subject = "NanoTechnology"),
            Subject(subject = "Biometrics"),
            Subject(subject = "Acoustics")
        )),
        Course(course = "Civil", listOf(
            Subject(subject = "Material Science"),
            Subject(subject = "Construction Engineering"),
            Subject(subject = "Hydraulic Science")
        ))
    )


}