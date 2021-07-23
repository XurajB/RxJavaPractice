package com.example.testrxjava1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.testrxjava1.data.User
import com.example.testrxjava1.data.UserProfile
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    companion object {
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tv)

        //justOperator()
        //fromOperator()
        //fromIterableOperator()
        //rangeTest()
        //repeatTest()
        //intervalTest()
        //timerTest()
        //createTest()
        //filterTest()
        //lastTest()
        //distinctTest()
        //skipTest()
        //bufferTest()
        //mapTest()
        //mapTest2()
        //flatMapTest()
        //flatMapTest2()
        //groupByTest()
        //mergeTest()
        //concatTest()
        //startWithTest()
        //zipTest()


        //// observables
        //testObservable()
        //testSingleObservable()
        //testMayObservable()
        //testCompletableObservable()
        //testFlowableObservable()
        //testScheduler()
        //testColdObservable()
        //testHotObservable()
        //testHotObservable2()
        //testAsyncSubject()
        //testBehaviorSubject()
        testPublishSubject()
    }

    // using lambda instead (as compared to ^)
    private fun rangeTest() {
        rangeOperator().subscribe(
            {
                // onNext
                Log.d(TAG, "onNext $it")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
    }

    private fun repeatTest() {
        repeatOperator().subscribe(
            {
                // onNext
                Log.d(TAG, "onNext $it")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
    }

    private fun intervalTest() {
        intervalOperator().subscribe(
            {
                // onNext
                Log.d(TAG, "onNext $it")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
    }

    private fun timerTest() {
        timerOperator().subscribe(
            {
                // onNext
                Log.d(TAG, "onNext $it")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
    }

    private fun createTest() {
        createOperator().subscribe(
            {
                // onNext
                Log.d(TAG, "onNext $it")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
    }

    private fun filterTest() {
        filterOperator().filter {
            it.age > 15
        }
        .subscribe(
            {
                // onNext
                Log.d(TAG, "onNext $it")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
    }

    private fun lastTest() {
        lastOperator()
            .last(User(1, "demo1", 1)) // default user if empty. // last returns Single<T> which does not have onComplete
            //.lastElement()// instead lastElement does not have default
            //.lastError() ^ similar if empty error
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                }
            )
    }

    private fun distinctTest() {
        distinctOperator()
            .distinct {
                it.age // distinct by age
            }
            //.distinct() // all fields are distinct
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun skipTest() {
        skipOperator()
            .skip(2) // skips first
            //.distinct() // add more operators
            //.skipLast(2) // skip last values
            //.skip(2, TimeUnit.SECONDS) // skip for 2 seconds
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun bufferTest() {
        bufferOperator()
            .buffer(3) // it returns mutable list instead of item
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun mapTest() {
        mapOperator()
            .map {
                it.age * 2
            }
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun mapTest2() {
        mapOperator()
            .map { // transform User to UserProfile
                UserProfile(it.id, it.name, it.age, "https://www.image.com/image.jpg")
            }
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun flatMapTest() {
        flatMapOperator()
            .flatMap {
                getUserProfile(it.id) // fetch userprofile based on this user's id
            }
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun flatMapTest2() {
        flatMapOperator2()
            .flatMap {
                Observable.fromIterable(it) // fetch userprofile based on this user's id
            }
            .flatMap {
                getUserProfile(it.id) // flatten this observable. if we use map instead, it will just return an Observable as returned by getUserProfile
            }
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun groupByTest() {
        groupByOperator()
            .groupBy {
                it.age
            }
            .filter { // optional. just filtering by age
                it.key == 22
            }
            .subscribe(
                { group ->
                    // onNext
                    group.subscribe(
                        {
                            Log.d(TAG, "Key ${group.key} - value: $it")
                        },
                        {
                            Log.d(TAG, "onError $it")
                        }
                    )
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun groupByTest2() {
        groupByOperator()
            .groupBy {
                it.age
            }
            .flatMapSingle {
                it.toList() // group into single observables
            }
            .subscribe(
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun mergeTest() {
        mergeOperator()
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun concatTest() {
        concatOperator()
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun startWithTest() {
        startWithOperator()
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun zipTest() {
        zipOperator()
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    /////
    ////
    private fun testObservable() {
        createObservable().subscribe(observer())
    }

    private fun testSingleObservable() {
        createSingleObservable().subscribe(singleObserver())
    }

    private fun testMayObservable() {
        createMayObservable().subscribe(maybeObserver())
    }

    private fun testCompletableObservable() {
        createCompletable().subscribe(completableObserver())
    }

    private fun testFlowableObservable() {
        createFlowable()
            .onBackpressureDrop()
            .observeOn(Schedulers.io(), false, 10) // simulating long running job in a different thread with buffersize of 10
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    // observable can be converted to flowable
    private fun testFlowableObservable2() {
        createObservable()
            .toFlowable(BackpressureStrategy.LATEST) // emits everything until buffersize then the last item only.. 1 to 10, 100
            .observeOn(Schedulers.io(), false, 10) // simulating long running job in a different thread with buffersize of 10
            .subscribe(
                {
                    // onNext
                    Log.d(TAG, "onNext $it")
                },
                {
                    // onError
                    Log.d(TAG, "onError $it")
                },
                {
                    // onComplete
                    Log.d(TAG, "onComplete")
                }
            )
    }

    private fun testDisposable() {
        createObservable()
            .subscribe(observer2())
    }

    private fun testScheduler() {
        compositeDisposable.add(
            Observable.just(userList)
                .flatMap {
                    Log.d(TAG, "upStream Thread ${Thread.currentThread().name}")
                    Observable.fromIterable(it)
                }
                .subscribeOn(Schedulers.io()) // upstream
                .observeOn(AndroidSchedulers.mainThread()) // downstream
                .subscribe(
                    {
                        // onNext
                        textView.text = it.toString() // this needs to be in main thread
                        Log.d(TAG, "onNext $it Thread: ${Thread.currentThread().name}")
                    },
                    {
                        // onError
                        Log.d(TAG, "onError $it")
                    },
                    {
                        // onComplete
                        Log.d(TAG, "onComplete")
                    }
                )
        )
    }

    private fun testColdObservable() {
        // cold observable: without subscribing this will not emit anything
        // everytime you subscribe it will give same value, no matter how many times we subscribe. there is no loss of data
        coldObservable().subscribe(coldObserver())
        coldObservable().subscribe(coldObserver()) // same result, no data loss
    }

    private fun testHotObservable() {
        val hotObservable = hotObservable()
        hotObservable.subscribe(
            {
                // onNext
                textView.text = it.toString() // this needs to be in main thread
                Log.d(TAG, "onNext $it Thread: ${Thread.currentThread().name}")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
        // it won't emit unless we call connect, it will immediately emit when connected whether we have observer or not
        hotObservable.connect()
        // if we subscribe after this point, it has already finished emitting values so we won't see any items

    }

    private fun testHotObservable2() {
        val hotObservable = hotObservable2() // this is emitting value every second
        hotObservable.connect()
        hotObservable.subscribe(
            {
                // onNext
                Log.d(TAG, "1st onNext $it")
            },
            {
                // onError
                Log.d(TAG, "onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "onComplete")
            }
        )
        // first subscriber will get value until 5 seconds, then both will get after 5
        Thread.sleep(5000)
        hotObservable.subscribe(
            {
                // onNext
                Log.d(TAG, "2nd onNext $it")
            },
            {
                // onError
                Log.d(TAG, "2nd onError $it")
            },
            {
                // onComplete
                Log.d(TAG, "2nd onComplete")
            }
        )
    }

    private fun testAsyncSubject() {
        asyncSubject()
    }

    private fun testBehaviorSubject() {
        behaviorSubject2()
    }

    private fun testPublishSubject() {
        publishSubject()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        // if using composite disposable
        compositeDisposable.clear()
        Log.d(TAG, "onDestroy")
    }
}