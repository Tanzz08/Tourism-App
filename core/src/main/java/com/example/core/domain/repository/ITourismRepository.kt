package com.example.core.domain.repository

import com.example.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface ITourismRepository {

    // karena hasil dari NetworkBoundResource berbeda, yaitu Flowable. maka perlu mengganti kode
    // pada repository menjadi seperti ini

    fun getAllTourism(): Flow<com.example.core.data.Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}