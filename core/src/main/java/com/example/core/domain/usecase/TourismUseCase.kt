package com.example.core.domain.usecase

import com.example.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow
interface TourismUseCase {

    // mengubah menjadi flowable
    fun getAllTourism(): Flow<com.example.core.data.Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, state: Boolean)

}