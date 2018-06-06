package com.epita.ing1.ghibliapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CharacterAdapter(val data: MutableList<Character>, val context: Context) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)

        val rowView = when (convertView) {
            null -> inflater.inflate(R.layout.activity_characters, parent, false)
            else -> convertView
        }

        val nameTextView = rowView.findViewById<TextView>(R.id.character_list) // FIX
        nameTextView.text = getItem(position).name
        return rowView
    }

    override fun getItem(position: Int): Character {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}