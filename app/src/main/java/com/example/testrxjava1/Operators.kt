package com.example.testrxjava1

import android.util.Log
import com.example.testrxjava1.data.User
import com.example.testrxjava1.data.UserProfile
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 10, 11)
val numArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
val numArray2 = arrayOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
val userList = mutableListOf<User>(
    User(1, "name1", 15),
    User(2, "name2", 15),
    User(3, "name3", 22),
    User(4, "name4", 43),
    User(5, "name5", 43),
    User(6, "name6", 22)
)

val userProfileList = mutableListOf<UserProfile>(
    UserProfile(1, "name1", 15, "image"),
    UserProfile(2, "name2", 15, "image"),
    UserProfile(3, "name3", 22, "image"),
    UserProfile(4, "name4", 43, "image"),
    UserProfile(5, "name5", 12, "image"),
    UserProfile(6, "name6", 22, "image")
)

// just: creates an observable that emits particular values - one by one. max 10
fun justOperator() {
    val observable = Observable.just(1,2,3,4,5)
    val observer = object : Observer<Int> {
        override fun onSubscribe(d: Disposable?) {
            Log.d(MainActivity.TAG, "onSubscribe")
        }

        override fun onNext(t: Int) {
            Log.d(MainActivity.TAG, "onNext: $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(MainActivity.TAG, "onError ${e.toString()}")
        }

        override fun onComplete() {
            Log.d(MainActivity.TAG, "onComplete")
        }
    }

    observable.subscribe(observer)
}

// from: converts various other objects and data types into Observable
fun fromOperator() {
    val observable = Observable.fromArray(numArray, numArray2)
    val observer = object : Observer<Array<Int>> {
        override fun onSubscribe(d: Disposable?) {
            Log.d(MainActivity.TAG, "onSubscribe")
        }

        override fun onNext(t: Array<Int>?) {
            Log.d(MainActivity.TAG, "onNext: ${Arrays.toString(t)}")
        }

        override fun onError(e: Throwable?) {
            Log.d(MainActivity.TAG, "onError ${e.toString()}")
        }

        override fun onComplete() {
            Log.d(MainActivity.TAG, "onComplete")
        }
    }

    observable.subscribe(observer)
}

// fromIterable: converts an iterable sequence into Observable that emits the items in the sequence
fun fromIterableOperator() {
    val observable = Observable.fromIterable(list)
    val observer = object: Observer<Int> {
        override fun onSubscribe(d: Disposable?) {
            Log.d(MainActivity.TAG, "onSubscribe")
        }

        override fun onNext(t: Int?) {
            Log.d(MainActivity.TAG, "onNext: $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(MainActivity.TAG, "onError ${e.toString()}")
        }

        override fun onComplete() {
            Log.d(MainActivity.TAG, "onComplete")
        }
    }
    observable.subscribe(observer)
}

// range: returns an Observable that emits a sequence of Int within a specified range
fun rangeOperator(): Observable<Int> {
    return Observable.range(1, 10)
}

// repeat: repeats sequence of items emitted by current Observable
fun repeatOperator(): Observable<Int> {
    return Observable.range(1, 10).repeat(2)
}

// interval: returns an observable that emits after initial delay and in certain interval forever
// or attach takeWhile to stop after certain condition
fun intervalOperator(): Observable<Long> {
    return Observable.interval(2, 1, TimeUnit.SECONDS)
        .takeWhile {
            it <= 10
        }
}

// timer: initial delay before emitting a single value, then completes
fun timerOperator(): Observable<Long> {
    return Observable.timer(5, TimeUnit.SECONDS)
}

// create: provides an API via a cold observable that bridges the reactive world with the callback style world
fun createOperator(): Observable<Int> {
    return Observable.create {
        try {
            for (i in list) {
                it.onNext(i * 5) // custom logic
            }
        } catch (e: Exception) {
            it.onError(e)
        }
        it.onComplete()
    }
}

// filter: emits only items from an observable that pass a predicate test
fun filterOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// last: emit only item emitted by an Observable
fun lastOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// distinct: suppress duplicate items emitted by an Observable
fun distinctOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// skip: suppress first n items emitted by an Observable
fun skipOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// Transforming observables
//
// buffer observable. periodically gather items emitted by an Observable into bundles and emit these bundles rather than emitting items at a time.
fun bufferOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// map: transform the items emitted by an Observable by applying a function to each item
fun mapOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// flatmap: transform the items emitted by an Observable into Observables, then flatten the emission from those into a single Observable
fun flatMapOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// helper for flatmap since it emits Observables
fun getUserProfile(id: Long): Observable<UserProfile> {
    return Observable.fromIterable(userProfileList)
        .filter {
            it.id == id
        }
}

fun flatMapOperator2(): Observable<List<User>> {
    return Observable.just(userList)
}

// group by: divide an Observable into a set of Observables that each emit a different subset of items from the original Observable
fun groupByOperator(): Observable<User> {
    return Observable.fromIterable(userList)
}

// merge: combine multiple Observables into one by merging their emissions. it may interleave items from different Observables.
fun getUser(): Observable<User> {
    return Observable.fromIterable(userList)
}
fun getProfile(): Observable<UserProfile> {
    return Observable.fromIterable(userProfileList)
}
fun mergeOperator(): Observable<Any> {
    return Observable.merge(getUser(), getProfile())
}

// concat: emit the emissions from two or more Observables without interleaving them - all items from one Observables are emitted before next Observable
fun getNum1To5(): Observable<Int> {
    return Observable.range(1, 5)
}
fun getNum6To10(): Observable<Int> {
    return Observable.range(6, 10)
}
fun concatOperator(): Observable<Int> {
    return getNum1To5().concatWith(getNum6To10())
}

// startwith: emit a specified sequence of items before beginning to mit the items from the source Observable
fun startWithOperator(): Observable<Int> {
    return getNum6To10().startWith(getNum1To5())
}
fun startWithOperator2(): Observable<User> {
    return getUser().startWith(Single.just(User(1, "test0", 22)))
}

// zip: combine the emissions of multiple Observables together via a specified function and emit single items for each
// combination based on the result of the function
fun zipOperator(): Observable<Any> {
    val num = Observable.just(1, 2, 3, 4, 5)
    val char = Observable.just("A", "B", "C", "D")
    return Observable.zip(num, char, BiFunction { t1, t2 ->
        "$t1$t2"
    })
}
