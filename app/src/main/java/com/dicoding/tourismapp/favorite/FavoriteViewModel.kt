package com.dicoding.tourismapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.TourismUseCase

class FavoriteViewModel(tourismUseCase: TourismUseCase) : ViewModel() {

    // pada viewmodel convert data Flow menjadi LiveData
    val favoriteTourism = tourismUseCase.getFavoriteTourism().asLiveData()

}

