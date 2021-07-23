package com.example.testrxjava1

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


/**
 * RxJava Subject: A subject os a sort of bridge or proxy that is available in some implementations of ReactiveX that acts both as an Observer and
 *   as an Observable. Because it is an observer, it can subscribe to one or more Observables.. and because it is an Observable, it can pass through
 *   items it observes by re-emitting them, and it can also emit new items.
 *   Types of Subject:
 *   1. AsyncSubject: Emits the last value (only the last value) emitted by the source Observable and only after the source completes. Any subsequent
 *      observers will also receive same last item. If source Observable terminates with an error, it will not emit any items but it will pass along
 *      the error notification.
 *   2. BehaviorSubject: It begins by emitting the most recently emitted by the source Observable at that time or send a default item if non has
 *      emitted and then continues to emit any other items emitted by the source
 *   3. PublishSubject: emits to an observer only those items that are emitted by the source subsequent to the time of subscription. PublishSubject
 *      may begin emitting items immediately upon creation and so there is a risk that one or more items may be lost. If you need a guarantee delivery
 *      of all items from a source Observable, you will need to manually reintroduce "cold" observable behavior by Observable.create or use ReplaySubject
 *   4. ReplaySubject: Emits to any observer all of the items that were emitted by the source Observable regardless of when the observers subscribes
 */

fun asyncSubject() {
    val observable = Observable.interval(1, TimeUnit.SECONDS).takeWhile {
        it <= 5
    }

    // use it as observer
    val subject = AsyncSubject.create<Long>() // only emit last value
    observable.subscribe(subject)

    // use subject as an observable
    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )

    // observer 2
    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "2nd onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "2nd onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "2nd onComplete")
        }
    )
}

fun asyncSubject2() {
    val subject = AsyncSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(10) // we can also use it to directly pass values.
    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )

    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "2nd onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )
    subject.onNext(100)
    subject.onComplete() // it will only pass last item, so we have to call onComplete. both will receive last emitted value
    subject.onNext(150) // this is ignored since we already called onComplete

    // if we subscribe 3rd observer after onComplete, it will still emit the last value
}

fun behaviorSubject() {
    val observable = Observable.interval(1, TimeUnit.SECONDS).takeWhile {
        it <= 5
    }

    val subject = BehaviorSubject.create<Long>()
    observable.subscribe(subject)

    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )

    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "2nd onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )
}

fun behaviorSubject2() {
    val subject = BehaviorSubject.create<Int>()
    subject.onNext(0)
    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )
    subject.onNext(1)
    subject.onNext(2)
    // most recent one is 2. so 2 onwards below. first observer will continue to get all next items
    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "2nd onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )
    subject.onNext(3)
}

fun publishSubject() {
    val observable = Observable.interval(1, TimeUnit.SECONDS).takeWhile { it <= 5 }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    val subject = PublishSubject.create<Long>()
    observable.subscribe(subject)

    // observable will not emit until it is subscribed to
    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )

    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "2nd onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )
}

fun publishSubject2() {
    val subject = PublishSubject.create<Int>()
    subject.onNext(0)
    subject.onNext(1)

    // onNext is before we subscribe, not at the time of subscription so we don't get them
    subject.subscribe(
        {
            // onNext
            Log.d(MainActivity.TAG, "onNext $it")
        },
        {
            // onError
            Log.d(MainActivity.TAG, "onError $it")
        },
        {
            // onComplete
            Log.d(MainActivity.TAG, "onComplete")
        }
    )

    subject.onNext(2)
    subject.onNext(3)
}