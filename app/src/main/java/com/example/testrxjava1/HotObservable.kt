package com.example.testrxjava1

import com.example.testrxjava1.data.User
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observables.ConnectableObservable
import java.util.concurrent.TimeUnit

/**
 * Hot observables: They emit items regardless of where there are observers. In a hot observable, there is a single source of emission
 *   and depending on when observers subscribe, the may miss some of these emissions.
 *   They represent events rather than finite dataset. The events can carry data with then, but there is a time-sensitive component where
 *   late observers can miss previously emitted data. UI events or Server requests can be represented as hot observers. One way to implement hot
 *   observables is Subject.
 * ConnectableObservable is another way to create hot observable: It takes any Observable and makes it hot. All emissions are played to all observers at once
 *   it does not begin emitting items when it is subscribed but only when its connect method is called. In this way we can
 *   wait for all intended Observer to to the Observable before it begins emitting items. Using ConnectableObservable allows us to set up all Observers
 *   beforehand and force each emission to go to all simultaneously which is called Multicasting
 */

fun hotObservable(): ConnectableObservable<User> {
    return Observable.fromIterable(userList).publish()
}

fun hotObservable2(): ConnectableObservable<Long> {
    return Observable.interval(1, TimeUnit.SECONDS).publish()
}