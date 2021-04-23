package com.mads.calculator

import java.util.*
import kotlin.collections.ArrayList

object Calculator {

    // MADS
    fun computeExpression(expression: Expression): String? {
        val parts = ArrayList(expression.snapShot())
        if (!isValidExpression(parts)) {
            return null;
        }
        val valuesStack = Stack<Double>()
        val operatorStack = Stack<OperatorType>()
        parts.forEachIndexed { index, part ->
            if (isOperator(part)) {
                if (index == parts.lastIndex) {
                    return@forEachIndexed
                }
                val operator = OperatorType.fromValue(part)
                while (!operatorStack.empty() && hasPrecedence(operator!!, operatorStack.peek())) {
                    val right = valuesStack.pop()
                    val left = valuesStack.pop()
                    valuesStack.push(
                        performOperation(
                            operatorStack.pop(),
                            right,
                            left
                        )
                    )
                }
                operatorStack.push(operator)
            } else {
                valuesStack.push(part.toDouble())
            }
        }

        while (!operatorStack.empty()) {
            val right = valuesStack.pop()
            val left = valuesStack.pop()
            valuesStack.push(performOperation(operatorStack.pop(), right, left))
        }

        val result = valuesStack.pop()

        return if (result.rem(1).equals(0)) {
            result.toLong().toString()
        } else {
            result.toFloat().toString();
        }
    }

    // Returns true if operator2 has higher or same precedence as operator1
    private fun hasPrecedence(operator1: OperatorType, operator2: OperatorType): Boolean {
        if (operator1 == operator2 || operator2.ordinal > operator1.ordinal) {
            return true
        }
        return false
    }

    private fun performOperation(operator: OperatorType, right: Double, left: Double): Double {
        when (operator) {
            OperatorType.MULTIPLICATION -> {
                return left * right
            }
            OperatorType.ADDITION -> {
                return left + right
            }
            OperatorType.SUBTRACTION -> {
                return left - right
            }
            OperatorType.DIVISION -> {
                return left / right
            }
        }
    }

    private fun isValidExpression(parts: List<String>): Boolean {
        if (parts.size < 3) {
            return false
        }
        var digitsParts = 0;
        parts.forEach { value ->
            if (!isOperator(value)) {
                digitsParts++
            }
        }
        return digitsParts >= 2
    }

    private fun isOperator(value: String): Boolean {
        return OperatorType.values().find { operatorType -> operatorType.value == value } != null
    }
}