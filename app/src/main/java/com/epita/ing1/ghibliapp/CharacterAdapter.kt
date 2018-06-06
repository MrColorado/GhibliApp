package com.epita.ing1.ghibliapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CharacterAdapter(val context: Context,
                       val data: MutableList<Character>) :
        RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = LayoutInflater
                .from(context)
                .inflate(R.layout.character_list ,parent, false)
        val viewHolder = ViewHolder(rowView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.nameTextView.text = currentItem.name
        //holder.alignmentImageView.setImageResource(R.drawable.logo)
    }
}