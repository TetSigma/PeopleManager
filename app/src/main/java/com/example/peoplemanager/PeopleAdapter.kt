package com.example.peoplemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class PeopleAdapter(private val peopleList: JSONArray, private val listener: OnDeleteClickListener) :
    RecyclerView.Adapter<PeopleAdapter.PersonViewHolder>() {

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = peopleList.getJSONObject(position)

        // Set first name and last name separately
        holder.firstNameTextView.text = person.getString("firstName")
        holder.lastNameTextView.text = person.getString("lastName")

        // Set other information
        holder.ageTextView.text = "Wiek: ${person.getInt("age")}"
        holder.heightTextView.text = "Wzrost: ${person.getInt("height")} cm"
        holder.weightTextView.text = "Waga: ${person.getInt("weight")} kg"

        // Set delete button action
        holder.deleteButton.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = peopleList.length()

    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstNameLabelTextView: TextView = view.findViewById(R.id.firstNameLabelTextView)
        val firstNameTextView: TextView = view.findViewById(R.id.firstNameTextView)
        val lastNameLabelTextView: TextView = view.findViewById(R.id.lastNameLabelTextView)
        val lastNameTextView: TextView = view.findViewById(R.id.lastNameTextView)
        val ageTextView: TextView = view.findViewById(R.id.ageTextView)
        val heightTextView: TextView = view.findViewById(R.id.heightTextView)
        val weightTextView: TextView = view.findViewById(R.id.weightTextView)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }
}

