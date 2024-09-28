package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var result by remember { mutableStateOf("") }
    var currentNumber by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var firstNumber by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = result,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontSize = 36.sp
        )

        val buttonRows = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", "C", "=", "+")
        )

        buttonRows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { buttonText ->
                    Button(
                        onClick = {
                            when (buttonText) {
                                in "0".."9" -> currentNumber += buttonText
                                in listOf("+", "-", "*", "/") -> {
                                    if (currentNumber.isNotEmpty()) {
                                        operator = buttonText
                                        firstNumber = currentNumber.toDouble()
                                        currentNumber = ""
                                    }
                                }
                                "=" -> {
                                    if (currentNumber.isNotEmpty() && operator.isNotEmpty()) {
                                        val secondNumber = currentNumber.toDouble()
                                        val resultValue = when (operator) {
                                            "+" -> firstNumber + secondNumber
                                            "-" -> firstNumber - secondNumber
                                            "*" -> firstNumber * secondNumber
                                            "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                                            else -> 0.0
                                        }
                                        result = if (resultValue.isNaN()) "Error" else resultValue.toString()
                                        currentNumber = result
                                        operator = ""
                                    }
                                }
                                "C" -> {
                                    currentNumber = ""
                                    operator = ""
                                    firstNumber = 0.0
                                    result = ""
                                }
                            }
                            result = currentNumber
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(buttonText)
                    }
                }
            }
        }
    }
}