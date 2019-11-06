package com.example.techcrunchapi.data.repository

import com.example.techcrunchapi.data.model.authorModel.AuthorModel
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import io.reactivex.Single

interface TechCrunchRepository {

    fun fetchTechCrunchModel(): Single<List<TechCrunchModel>>
    fun fetchAuthorModel(userID: Int): Single<AuthorModel>

}