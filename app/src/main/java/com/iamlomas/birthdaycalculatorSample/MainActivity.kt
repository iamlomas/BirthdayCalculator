package com.iamlomas.birthdaycalculatorSample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.iamlomas.birthday_calculator.BirthdayCalculator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val age = BirthdayCalculator().calculateAge(2000, 1, 12)
        val nextBirthdayCelebration = BirthdayCalculator().calculateNextBirthDate(2000, 1, 12)

        findViewById<TextView>(R.id.tvText).apply {
            text = "My age is $age"
        }

        findViewById<TextView>(R.id.tvNextBirthdayCelebration).apply {
            text = "And my next Birthday celebration is after $nextBirthdayCelebration"
        }
    }
}