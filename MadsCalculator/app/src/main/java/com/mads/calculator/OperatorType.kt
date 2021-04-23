package com.mads.calculator

enum class OperatorType(val value: String) {
    SUBTRACTION("-"),
    DIVISION("/"),
    ADDITION("+"),
    MULTIPLICATION("*");

    companion object {
        fun fromValue(rawValue: String): OperatorType? {
            return when (rawValue) {
                SUBTRACTION.value -> SUBTRACTION
                DIVISION.value -> DIVISION
                ADDITION.value -> ADDITION
                MULTIPLICATION.value -> MULTIPLICATION
                else -> null
            }
        }
    }
}