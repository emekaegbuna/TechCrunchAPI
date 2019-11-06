package com.example.techcrunchapi.viewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techcrunchapi.data.model.authorModel.AuthorModel
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import com.example.techcrunchapi.data.repository.TechCrunchRepository
import com.example.techcrunchapi.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class UserViewModel(private var techCrunchRepository: TechCrunchRepository, private var userID: Int): ViewModel() {

    private val disposable =  CompositeDisposable()

    var lastFetchedTime: Date? = null

    val authorModel: MutableLiveData<AuthorModel> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val loadingState = MutableLiveData<LoadingState>()

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    fun fetchAuthorModel(){
        loadingState.value = LoadingState.LOADING
        disposable.add(
            techCrunchRepository
                .fetchAuthorModel(userID)
                .subscribe({it ->
                    lastFetchedTime = Date()
                    if (it == null){
                        errorMessage.value = "no posts found"
                    }else{
                        authorModel.value = it
                        loadingState.value = LoadingState.SUCCESS
                    }
                }, {error ->
                    lastFetchedTime = Date()
                    error.printStackTrace()
                    when(error){
                        is UnknownHostException -> errorMessage.value = "No Network"
                        else -> errorMessage.value = error.localizedMessage
                    }

                    loadingState.value = LoadingState.ERROR
                })
        )


    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getActivity(): Class<out Activity>{
        return MainActivity::class.java
    }
}