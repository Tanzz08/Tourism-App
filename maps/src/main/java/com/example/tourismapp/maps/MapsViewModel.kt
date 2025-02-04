package com.example.tourismapp.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.TourismUseCase

class MapsViewModel(tourismUseCase: TourismUseCase): ViewModel() {
    val tourism = tourismUseCase.getAllTourism().asLiveData()
}