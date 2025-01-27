package com.dicoding.tourismapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase

class HomeViewModel(tourismUseCase: TourismUseCase) : ViewModel() {

    // pada viewmodel convert data Flowable menjadi LiveData menggunakan LiveDataReactiveStreams
    val tourism = tourismUseCase.getAllTourism().toLiveData()

}

