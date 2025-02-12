package com.dicoding.tourismapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.TourismUseCase

class HomeViewModel(tourismUseCase: TourismUseCase) : ViewModel() {

    // pada viewmodel convert data Flow menjadi LiveData
    val tourism = tourismUseCase.getAllTourism().asLiveData()

}

