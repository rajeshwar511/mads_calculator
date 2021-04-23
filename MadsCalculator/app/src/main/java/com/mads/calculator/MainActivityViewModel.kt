package com.mads.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val smartAnswer: MutableLiveData<String> = MutableLiveData("")
    val smartAnswerLiveData: LiveData<String>
        get() = smartAnswer

    private val calculatorText: MutableLiveData<String> = MutableLiveData("")
    val calculatorTextLiveData: LiveData<String>
        get() = calculatorText

    private var expression: Expression? = null

    fun addDigit(value: Int) {
        if (expression == null) {
            expression = Expression()
        }
        expression?.let {
            val result = it.addOrUpdateLastPart(value.toString())
            if (result) {
                calculatorText.value = it.toDisplayString()
                smartAnswer.value = Calculator.computeExpression(it)
            }
        }
    }

    fun addOperator(operatorType: OperatorType) {
        if (expression == null) {
            expression = Expression()
        }
        expression?.let {
            val result = it.addOrUpdateLastPart(operatorType.value)
            if (result) {
                calculatorText.value = it.toDisplayString()
                smartAnswer.value = Calculator.computeExpression(it)
            }
        }
    }

    fun computeAnswer() {
        expression?.let {
            Calculator.computeExpression(it)?.let { nonNullResult ->
                calculatorText.value = nonNullResult
                smartAnswer.value = ""
                expression = Expression().apply { addOrUpdateLastPart(nonNullResult) }
                InMemoryHistoryCache.put(it)
            }
        }
    }

    fun deleteLastPart() {
        expression?.let {
            val result = it.deleteLastPart()
            if (result) {
                calculatorText.value = it.toDisplayString()
                smartAnswer.value = Calculator.computeExpression(it)
            }
        }
    }

    fun clearExpression() {
        expression?.clearExpression()
        calculatorText.value = ""
        smartAnswer.value = ""
        expression = null
    }

    fun itemSelected(expression: Expression) {
        this.expression = expression
        calculatorText.value = expression.toDisplayString()
        smartAnswer.value = Calculator.computeExpression(expression)
    }
}
