package com.example.techcrunchapi.viewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techcrunchapi.ui.activity.MainActivity
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import com.example.techcrunchapi.data.repository.TechCrunchRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class MainViewModel constructor(private val techCrunchRepository: TechCrunchRepository): ViewModel() {

    private val disposable =  CompositeDisposable()

    var lastFetchedTime: Date? = null

    val techCrunchModel: MutableLiveData<List<TechCrunchModel>> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val loadingState = MutableLiveData<LoadingState>()

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    fun fetchTechCrunchModel(){
        loadingState.value = LoadingState.LOADING
        disposable.add(
            techCrunchRepository
                .fetchTechCrunchModel()
                .subscribe({it ->
                    lastFetchedTime = Date()
                    if (it.isEmpty()){
                        errorMessage.value = "no posts found"
                    }else{
                        techCrunchModel.value = it
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