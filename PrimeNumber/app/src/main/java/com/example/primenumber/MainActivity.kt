package com.example.primenumber

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val input: EditText = findViewById(R.id.etInput)
        val result: TextView = findViewById(R.id.tvResult)

        input.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try {
                    val inputVal = input.text.toString()
                    val regex = Regex("^\\d+,\\d+$")
                    if (regex.matches(inputVal)) {
                        val split = inputVal.split(",")
                        val min = split[0].toInt()
                        val max = split[1].toInt()

                        if (min > 0 && max > 0 && min < max) {
                            val primes = mutableListOf<Int>()
                            for (i in min..max) {
                                if (isPrime(i)) {
                                    primes.add(i)
                                }
                            }

                            if (primes.isNotEmpty()) {
                                result.text = primes[Random.nextInt(primes.size)].toString()

                            } else {
                                result.text = "No primes found in the range"
                            }

                        } else {
                            throw NumberFormatException()
                        }
                    } else {
                        throw NumberFormatException()
                    }
                } catch (exception: Exception) {
                    Toast.makeText(this, "Input should be in format A,B where A>0, B>0, and A<B", Toast.LENGTH_LONG).show()
                    result.text = ""
                }
                true
            } else {
                false
            }
        }
    }

    // Function to check if a number is prime
    fun isPrime(i: Int): Boolean {
        if (i <= 1) return false
        for (j in 2..Math.sqrt(i.toDouble()).toInt()) {
            if (i % j == 0) return false
        }
        return true
    }
}