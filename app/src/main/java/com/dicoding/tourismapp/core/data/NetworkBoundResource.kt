package com.dicoding.tourismapp.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse

import com.dicoding.tourismapp.core.utils.AppExecutors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType: Any, RequestType> {

    private val result : Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }
    private val mCompositeDisposable = CompositeDisposable()

//    init {
//
//        @Suppress("LeakingThis")
//        val dbSource = loadFromDB()
//        val db = dbSource
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .take(1)
//            .subscribe { value ->
//                if (shouldFetch(value)) {
//                    fetchFromNetwork()
//                } else {
//                    result.onNext(Resource.Success(value))
//                }
//            }
//        mCompositeDisposable.add(db)
//    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun  createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result

//    private fun fetchFromNetwork() {
//
//        val apiResponse = createCall()
//
//        result.onNext(Resource.Loading(null))
//        val response = apiResponse
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .take(1)
//            .doOnComplete {
//                mCompositeDisposable.dispose()
//            }
//            .subscribe { response ->
//                when (response) {
//                    is ApiResponse.Success -> {
//                        saveCallResult(response.data)
//                        val dbSource = loadFromDB()
//                        val subscribe = dbSource.subscribeOn(Schedulers.computation())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .take(1)
//                            .subscribe {
//                                result.onNext(Resource.Success(it))
//                            }
//                        subscribe.dispose()
//                    }
//                    is ApiResponse.Empty -> {
//                        val dbSource = loadFromDB()
//                        val subscribe = dbSource.subscribeOn(Schedulers.computation())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .take(1)
//                            .subscribe {
//                                result.onNext(Resource.Success(it))
//                            }
//                        subscribe.dispose()
//                    }
//                    is ApiResponse.Error -> {
//                        onFetchFailed()
//                        result.onNext(Resource.Error(response.errorMessage, null))
//                    }
//                }
//            }
//        mCompositeDisposable.add(response)
//    }
//
//    fun asFlowable(): Flowable<Resource<ResultType>> =
//        result.toFlowable(BackpressureStrategy.BUFFER)
}