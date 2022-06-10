package com.tantei.todo.data.converter

import androidx.room.TypeConverter
import com.tantei.todo.data.models.Priority
import com.tantei.todo.data.models.State

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }


}