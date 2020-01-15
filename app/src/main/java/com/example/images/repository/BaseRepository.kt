package com.example.images.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

abstract class BaseRepository {

    protected fun<T> Single<Response<T>>.subscribeToResponse(): Single<T> {
        return this.subscribeOn(Schedulers.io())
            .map {
                if (it.isSuccessful && it.body() != null) it.body()!!
                else throw Throwable()
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}