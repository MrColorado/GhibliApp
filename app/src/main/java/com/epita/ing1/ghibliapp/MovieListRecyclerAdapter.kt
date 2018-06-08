package com.epita.ing1.ghibliapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MovieListRecyclerAdapter (
        private val context: Context,
        var data: MutableList<Movie>,
        private val onItemClickListener: View.OnClickListener) :
        RecyclerView.Adapter<MovieListRecyclerAdapter.ViewHolder>() {

        // the new RecyclerAdapter enforces the use of
        // the ViewHolder class performance pattern
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitleTextView: TextView = itemView.findViewById(R.id.moviesListItemTitleView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // called when a new view holder is required to display a row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        // create the row (list item) from a layout inflater
        var layout = R.layout.activity_browse_films_item_list
        if (data.isEmpty()) {
            layout = R.layout.activity_browse_films_empty_list
        }
        val rowView = LayoutInflater
                .from(context)
                .inflate(layout, parent, false)

        // create a ViewHolder using this row view
        // return this ViewHolder. The system will make sure view holders
        // are used and recycled
        return ViewHolder(rowView)
    }

    // called when a row is about to be displayed
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // retrieve the item at the specified position
        val currentItem = data[position]

        // put the data
        holder.movieTitleTextView.text = currentItem.title

        // We just put position in data to be able to retrieve information when
        // user clicks on it. (to load details about the movie and stuff)
        holder.movieTitleTextView.tag = position

        // add onclicklisterner to the newly created view
        holder.movieTitleTextView.setOnClickListener(onItemClickListener)
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    fun filterList(filteredItems: ArrayList<Movie>) {
        this.data = filteredItems
        notifyDataSetChanged()
    }
}