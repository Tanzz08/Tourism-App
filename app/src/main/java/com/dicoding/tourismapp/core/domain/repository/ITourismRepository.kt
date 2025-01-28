package com.dicoding.tourismapp.core.domain.repository

import androidx.lifecycle.LiveData
import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.core.domain.model.Tourism
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface ITourismRepository {

    // karena hasil dari NetworkBoundResource berbeda, yaitu Flowable. maka perlu mengganti kode
    // pada repository menjadi seperti ini

    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}