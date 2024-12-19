package com.example.peoplemanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONObject

class AddPersonActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var ageEditText: TextInputEditText
    private lateinit var heightEditText: TextInputEditText
    private lateinit var weightEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_person)

        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        ageEditText = findViewById(R.id.ageEditText)
        heightEditText = findViewById(R.id.heightEditText)
        weightEditText = findViewById(R.id.weightEditText)

        val saveButton: Button = findViewById(R.id.saveButton)
        val nextButton: Button = findViewById(R.id.nextButton)

        saveButton.setOnClickListener {
            if (validateInput()) {
                savePersonData()
                Toast.makeText(this, "Person saved successfully!", Toast.LENGTH_SHORT).show()
            }
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, ListPeopleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(): Boolean {
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val age = ageEditText.text.toString().toIntOrNull()
        val height = heightEditText.text.toString().toIntOrNull()
        val weight = weightEditText.text.toString().toIntOrNull()

        if (firstName.isBlank()) {
            firstNameEditText.error = "First name is required"
            return false
        }

        if (lastName.isBlank()) {
            lastNameEditText.error = "Last name is required"
            return false
        }

        if (age == null || age <= 0) {
            ageEditText.error = "Age must be a positive number"
            return false
        }

        if (height == null || height !in 50..250) {
            heightEditText.error = "Height must be between 50 and 250 cm"
            return false
        }

        if (weight == null || weight !in 3..200) {
            weightEditText.error = "Weight must be between 3 and 200 kg"
            return false
        }

        return true
    }

    private fun savePersonData() {
        val sharedPreferences = getSharedPreferences("peopleData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val age = ageEditText.text.toString().toInt()
        val height = heightEditText.text.toString().toInt()
        val weight = weightEditText.text.toString().toInt()

        val person = JSONObject()
        person.put("firstName", firstName)
        person.put("lastName", lastName)
        person.put("age", age)
        person.put("height", height)
        person.put("weight", weight)

        val peopleList = getPeopleList()
        peopleList.put(person)

        editor.putString("people", peopleList.toString())
        editor.apply()
    }

    private fun getPeopleList(): JSONArray {
        val sharedPreferences = getSharedPreferences("peopleData", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("people", "[]")
        return JSONArray(jsonString)
    }
}
