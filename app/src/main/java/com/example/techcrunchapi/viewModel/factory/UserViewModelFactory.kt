package com.example.techcrunchapi.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.techcrunchapi.data.repository.TechCrunchRepository
import com.example.techcrunchapi.viewModel.MainViewModel
import com.example.techcrunchapi.viewModel.UserViewModel

class UserViewModelFactory constructor(private val techCrunchRepository: TechCrunchRepository, private val userID: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(techCrunchRepository, userID) as T
    }

}