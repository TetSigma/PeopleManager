package com.example.peoplemanager

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class ListPeopleActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var peopleAdapter: PeopleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_people)

        recyclerView = findViewById(R.id.peopleRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val peopleList = getPeopleList()
        peopleAdapter = PeopleAdapter(peopleList, object : PeopleAdapter.OnDeleteClickListener {
            override fun onDeleteClick(position: Int) {
                deletePerson(position)
            }
        })

        recyclerView.adapter = peopleAdapter
    }

    private fun getPeopleList(): JSONArray {
        val sharedPreferences = getSharedPreferences("peopleData", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("people", "[]")
        return JSONArray(jsonString)
    }

    private fun deletePerson(position: Int) {
        val peopleList = getPeopleList()
        val person = peopleList.getJSONObject(position)
        peopleList.remove(position)

        val editor = getSharedPreferences("peopleData", Context.MODE_PRIVATE).edit()
        editor.putString("people", peopleList.toString())
        editor.apply()

        peopleAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "${person.getString("firstName")} ${person.getString("lastName")} deleted", Toast.LENGTH_SHORT).show()
    }
}
