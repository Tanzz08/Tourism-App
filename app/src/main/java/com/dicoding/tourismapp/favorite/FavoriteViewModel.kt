package com.dicoding.tourismapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase

class FavoriteViewModel(tourismUseCase: TourismUseCase) : ViewModel() {

    // pada viewmodel convert data Flowable menjadi LiveData menggunakan LiveDataReactiveStreams
    val favoriteTourism = tourismUseCase.getFavoriteTourism().toLiveData()

}

