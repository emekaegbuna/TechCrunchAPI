package com.example.techcrunchapi.data.repository

import com.example.techcrunchapi.data.model.authorModel.AuthorModel
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import com.example.techcrunchapi.data.remote.WebServices
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TechCrunchRepositoryImpl(private val webServices: WebServices):
    TechCrunchRepository {
    override fun fetchAuthorModel(userID: Int): Single<AuthorModel> {
        return webServices.fetchAuthors(userID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchTechCrunchModel(): Single<List<TechCrunchModel>> {
        return webServices.fetchTechCrunchModel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}