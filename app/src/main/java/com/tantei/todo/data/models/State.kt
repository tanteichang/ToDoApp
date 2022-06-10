package com.tantei.todo.data.models

enum class State(val code: Byte) {
    NEW(0),
    DONE(1),
    DELETED(2);

    companion object {
        private val map = values().associateBy(State::code)
        fun fromByte(n: Byte): State {
            return map.getValue(n)
        }
    }
}