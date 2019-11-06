package com.example.techcrunchapi.ui.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.techcrunchapi.ui.adapter.listener.TechCrunchClickListener
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import kotlinx.android.synthetic.main.card_layout_tech_crunch.view.*

class TechCrunchViewHolder(item: View): RecyclerView.ViewHolder(item) {

    var image = item.iv_post
    var title = item.tv_title
    var description = item.tv_description
    var author = item.tv_author_name
    var date = item.tv_date

    fun bind(result: TechCrunchModel, techCrunchClickListener: TechCrunchClickListener){
        itemView.setOnClickListener {
            techCrunchClickListener.onClick(result)
        }
    }
}