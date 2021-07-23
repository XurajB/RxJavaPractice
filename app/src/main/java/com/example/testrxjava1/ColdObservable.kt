package com.example.testrxjava1

import android.util.Log
import com.example.testrxjava1.MainActivity.Companion.TAG
import com.example.testrxjava1.data.User
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Cold Observables: Cold observables are sequences that only emits item upon subscription. Each observer will have its own set of
 *   items emitted to them and depending on how observable was created, will have different instances of emitted items. Data-driven observables are most
 *   likely cold.
 */

fun coldObservable(): Observable<User> {
    return Observable.fromIterable(userList)
}

fun coldObserver(): Observer<User> {
    return object : Observer<User> {
        override fun onSubscribe(d: Disposable?) {
            Log.d(TAG, "onSubscribe")
        }

        override fun onNext(t: User?) {
            Log.d(TAG, "onNext $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "onError $e")
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete")
        }
    }
}