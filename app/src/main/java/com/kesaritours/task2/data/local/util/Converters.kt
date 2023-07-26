package com.kesaritours.task2.data.local.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.kesaritours.task2.domain.model.Subject
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: GsonParser
) {
    @TypeConverter
    fun fromSubjectJson(json: String): List<Subject> {
        return jsonParser.fromJson<ArrayList<Subject>>(
            json = json,
            type = object : TypeToken<ArrayList<Subject>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toSubjectJson(subjects: List<Subject>): String {
        return jsonParser.toJson(
            subjects,
            type = object : TypeToken<ArrayList<Subject>>() {}.type
        ) ?: "[]"
    }

}