package com.example.techcrunchapi.data.model.postModel;

data class TechCrunchModel(
    val author: Int,
    val content: Content,
    val date: String,
    val date_gmt: String,
    val jetpack_featured_media_url: String,
    val title: Title
)


