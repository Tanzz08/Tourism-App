package com.example.core.data

import com.example.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType: Any, RequestType> {

    private val result : Flow<com.example.core.data.Resource<ResultType>> = flow {
        emit(com.example.core.data.Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(com.example.core.data.Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { com.example.core.data.Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { com.example.core.data.Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(com.example.core.data.Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { com.example.core.data.Resource.Success(it) })
        }
    }

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

    fun asFlow(): Flow<com.example.core.data.Resource<ResultType>> = result

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