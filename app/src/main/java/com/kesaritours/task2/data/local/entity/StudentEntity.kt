package com.kesaritours.task2.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kesaritours.task2.domain.model.Student
import com.kesaritours.task2.domain.model.Subject
import com.kesaritours.task2.util.Constants

@Entity(tableName = Constants.TABLE_STUDENT)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "course") var course: String,
    @ColumnInfo(name = "subjects") var subjects: List<Subject>,
    @ColumnInfo(name = "totalMarks") var totalMarks: Int = 0,
    @ColumnInfo(name = "average") var averageMarks: Int = 0,
) {
    fun toStudent() : Student {
        return Student(
            id = id,
            name = name,
            course = course,
            subjects = subjects,
            totalMarks = totalMarks,
            averageMarks = averageMarks
        )
    }
}






