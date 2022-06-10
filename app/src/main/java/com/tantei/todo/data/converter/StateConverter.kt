package com.tantei.todo.data.converter

import androidx.room.TypeConverter
import com.tantei.todo.data.models.State

class StateConverter {
    @TypeConverter
    fun storeState(state: State): Byte {
        return state.code
    }

    @TypeConverter
    fun getState(state: Byte): State {
        return State.fromByte(state)
    }
}