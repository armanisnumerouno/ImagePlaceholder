package com.example.images.view

import androidx.lifecycle.ViewModel
import com.example.images.entities.Event
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel: ViewModel() {

    val output = PublishSubject.create<Event>()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    protected fun <T> Single<T>.fetchData() {
        disposable.add(
            this.subscribeWithMapToEvent()
                .subscribe { output.onNext(it) }
        )
    }

    private fun <T> Single<T>.subscribeWithMapToEvent(): Observable<Event> {
        return PublishSubject.create { emitter ->
            emitter.onNext(Event.Progress(true))
            this
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        emitter.onNext(Event.Progress(false))
                        emitter.onNext(Event.Data(data))
                        emitter.onComplete()
                    },
                    { error ->
                        emitter.onNext(Event.Progress(false))
                        emitter.onNext(Event.Error(error.localizedMessage ?: "Error", error))
                        emitter.onComplete()
                    }
                )
        }
    }
}