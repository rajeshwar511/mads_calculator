package com.mads.calculator

class Expression {

    private val parts: ArrayList<String> = ArrayList()

    fun addOrUpdateLastPart(value: String): Boolean {
        if (parts.isEmpty() && isOperator(value)) {
            return false
        }

        if (parts.isEmpty()) {
            parts.add(value)
            return true
        }

        val lastPart = parts.last()
        if (isOperator(value) && isOperator(lastPart)) {
            parts.set(parts.size - 1, value)
            return true
        }

        if (!isOperator(value) && !isOperator(lastPart)) {
            val newPart = lastPart + value
            parts.set(parts.size - 1, newPart)
            return true
        }

        parts.add(value)
        return true
    }

    fun deleteLastPart(): Boolean {
        if (parts.isEmpty()) {
            return false
        }
        val lastPart = parts.last()
        if (isOperator(lastPart) || lastPart.length == 1) {
            parts.removeAt(parts.lastIndex)
            return true
        }

        val newPart = lastPart.substring(0, lastPart.lastIndex)
        parts.set(parts.lastIndex, newPart)
        return true
    }

    public fun clearExpression() {
        parts.clear()
    }

    public fun toDisplayString(): String {
        val stringBuilder = StringBuilder()
        parts.forEach {
            stringBuilder.append(it)
        }
        return stringBuilder.toString()
    }

    public fun snapShot(): List<String> {
        return ArrayList(parts)
    }

    private fun isOperator(value: String): Boolean {
        return OperatorType.values().find { operatorType -> operatorType.value == value } != null
    }
}