package com.example.techcrunchapi.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.techcrunchapi.data.repository.TechCrunchRepository
import com.example.techcrunchapi.viewModel.MainViewModel

class MainViewModelFactory constructor(private val techCrunchRepository: TechCrunchRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(techCrunchRepository) as T
    }

}