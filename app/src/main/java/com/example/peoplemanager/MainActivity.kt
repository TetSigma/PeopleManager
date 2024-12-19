package com.example.peoplemanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assuming you have a button in activity_main.xml to navigate to AddPersonActivity
        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            // Navigating to AddPersonActivity (Screen 1)
            val intent = Intent(this, AddPersonActivity::class.java)
            startActivity(intent)
        }
    }
}
