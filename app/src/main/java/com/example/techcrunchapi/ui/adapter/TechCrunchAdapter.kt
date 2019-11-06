package com.example.techcrunchapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.htmlEncode
import androidx.recyclerview.widget.RecyclerView
import com.example.techcrunchapi.R
import com.example.techcrunchapi.ui.adapter.listener.TechCrunchClickListener
import com.example.techcrunchapi.ui.adapter.viewHolder.TechCrunchViewHolder
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import com.squareup.picasso.Picasso
import java.util.Collections.replaceAll

class TechCrunchAdapter(val techCrunchModel: MutableList<TechCrunchModel>, private val techCrunchClickListener: TechCrunchClickListener): RecyclerView.Adapter<TechCrunchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechCrunchViewHolder {
        return TechCrunchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_layout_tech_crunch,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return techCrunchModel.size
    }

    override fun onBindViewHolder(holder: TechCrunchViewHolder, position: Int) {
        var techCrunch = techCrunchModel[position]
        holder.author.text = "Author: " + techCrunch.author.toString()
        holder.title.text = "Title: "+ techCrunch.title.rendered
        holder.description.text = "Description: "+techCrunch.content.rendered.htmlEncode()
        holder.date.text = "Date"+ techCrunch.date
        Picasso.get().load(techCrunch.jetpack_featured_media_url).into(holder.image)

        holder.bind(techCrunch, techCrunchClickListener)
    }

    fun updateAuthor(techCrunch: TechCrunchModel){
        techCrunchModel[techCrunchModel.indexOf(techCrunch)] = techCrunch
    }
}