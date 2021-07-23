package com.example.testrxjava1

import android.util.Log
import com.example.testrxjava1.MainActivity.Companion.TAG
import com.example.testrxjava1.data.User
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.Exception

/**
 * Types of Observables:
 * - Observable
 * - Flowable
 * - Single
 * - Maybe
 * - Completable
 *
 * Types of Observers
 * - Observer
 * - SingleObserver
 * - MaybeObserver
 * - CompletableObserver
 *
 * Observable <> Observer
 * - The Observable that emits more that one value.
 * Single <> SingleObserver
 * - Single is used when the Observable has to emit only one value like a response from network call.
 * Maybe <> MaybeObserver
 * - Maybe is used when the observable has to emit a value or no value
 * Completable <> CompletableObserver
 * - Completable is used when the Observable has to do some task without emitting a value
 * Flowable <> Observer
 * - Similar to Observable but this comes into picture when Observable is emitting a huge number of values that can't be received
 * - or consumed by the Observer. In this case, the Observable needs to skip some values on the basis of strategy or it will
 * - throw an Exception. Flowable handles this exception with a strategy. this is call BackPressureStrategy and this exception
 * - is called MissingBackpressureException
 * - BackPressure strategies:
 *      - BackpressureStrategy.DROP: we use this to discard the events that con not be consumed by the Observer
 *      - BackpressureStrategy.BUFFER: the source will buffer all the events until the subscriber can consume them
 *      - BackpressureStrategy.LATEST: force the source to keep only the latest items, by overwriting previous values
 *      - BackpressureStrategy.MISSING: temporary ignore this value, if we don't want any backpressure strategy
 *      - BackpressureStrategy.ERROR: if we don't expect backpressure at all, we can pass BackpressureStrategy.ERROR
 *
 *   Observable can be converted to Flowable using observable.toFlowable(strategy)
 *
 *   -------------------
 *   RxJava Schedulers
 *   Threading in RxJava is done with the help of Schedulers. It can be thought as a thread pool manager for 1 or more threads. Whenever a
 *   scheduler needs to execute a task, it will take a thread from its pool and run the task in that thread.
 *   Types of Schedulers
 *   - Schedulers.io() : is backed by an unbounded thread pool. It is used for non CPU-intensive I/O type work like working with file system,
 *     network calls, database operations etc.
 *   - Schedulers.computation() : bounded thread pool with size up to the number of available processors. It is used for computational or
 *     CPU-intensive work such as image resizing, processing large data sets etc
 *   - Schedulers.newThread() : creates a new thread for each unit of work scheduled. This is very expensive as new thread is spawned every time
 *   - Schedulers.from(Executor executor) : creates and returns a custom scheduler backed by specific executor.
 *   - AndroidSchedulers.mainThread() : available in RxAndroid extension.
 *   - Schedulers.single() : backed by single thread executing tasks sequentially in order requested
 *   - Schedulers.trampoline() : executes tasks in a FIFO manner by one of the participating worker
 *
 *   Default threading in RxJava: if we don;t specify threading (subscribeOn, observeOn..), the data will be emitted and processed by current
 *   scheduler/thread (usually main thread). Some operators like interval operator operate on a computational thread by default.
 *   We can specify a thread to execute any operator by using subscribeOn and/or observeOn
 *   - subscribeOn affects upstream operators (operators above the subscribeOn)
 *   - observeOn affects downstream operators (operators below the observeOn)
 *   if only subscribeOn is specified, all operators will be executed on that thread
 *   if only observeOn is specified, all operators will be executed on the current thread and only operators below the observeOn will be switched to
 *    thread specified by the observeOn
 *
 *   Hot Observables:
 */

fun createObservable(): Observable<Int> {
    return Observable.create { emitter ->
        try {
            if (!emitter.isDisposed) {
                for (i in 0..100) {
                    emitter.onNext(i)
                }
                emitter.onComplete()
            }
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }
}

/////////////////
///////////////////
// returns a generic observer
fun observer(): Observer<Int> {
    return object : Observer<Int> {
        override fun onSubscribe(d: Disposable?) {
            Log.d(TAG, "onSubscribe")
        }

        override fun onNext(t: Int?) {
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

// single
fun createSingleObservable(): Single<Int> {
    return Single.create { emitter ->
        try {
            if (!emitter.isDisposed) {
                emitter.onSuccess(10) // single does not have onNext, onComplete
            }
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }
}

fun singleObserver(): SingleObserver<Int> {
    return object : SingleObserver<Int> {
        override fun onSubscribe(d: Disposable?) {
            Log.d(TAG, "onSubscribe")
        }

        override fun onSuccess(t: Int?) {
            Log.d(TAG, "onSuccess $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "onError $e")
        }
    }
}

// maybe: could have value or no value
fun createMayObservable(): Maybe<List<User>> {
    //return Maybe.just(userList)
    return Maybe.just(emptyList())
}

fun maybeObserver(): MaybeObserver<List<User>> {
    return object : MaybeObserver<List<User>> {
        override fun onSubscribe(d: Disposable?) {
            Log.d(TAG, "onSubscribe")
        }

        override fun onSuccess(t: List<User>?) {
            Log.d(TAG, "onSuccess $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "onError $e")
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete")
        }
    }
}

fun createCompletable(): Completable {
    return Completable.create { emitter ->
        try {
            if (!emitter.isDisposed) {
                getLocation() // do some work.
                emitter.onComplete() // let the observer know we are done
            }
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }
}

fun completableObserver(): CompletableObserver {
    return object : CompletableObserver {
        override fun onSubscribe(d: Disposable?) {
            Log.d(TAG, "onSubscribe")
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete")
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "onError $e")
        }
    }
}

fun getLocation() {
    Log.d(TAG, "getting location from network")
}

fun createFlowable(): Flowable<Int> {
    return Flowable.range(1, 100)
}

// disposable. we need to clean up after the work has been done or activity has been destroyed
lateinit var disposable: Disposable // one way
var compositeDisposable = CompositeDisposable() // another way.. but we can add multiple disposables here. it is reusable for multiple observers
fun observer2(): Observer<Int> {
    return object : Observer<Int> {
        override fun onSubscribe(d: Disposable?) {
            d?.let {
                disposable = it
                // one or the other
                compositeDisposable.add(it)
            }
            Log.d(TAG, "onSubscribe")
        }

        override fun onNext(t: Int?) {
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
fun disposableTest3() {
    // or use it this way
    compositeDisposable.add(
        intervalOperator().subscribe {
            //// implement methods
        }
    )
}


