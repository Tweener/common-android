package com.tweener.common.android.kotlinextension

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * @author Vivien Mahe
 * @since 04/10/2020
 */

/**
 * Calls the specified action after this Observable signals onNext, onError or onCompleted or gets disposed by the downstream.
 */
fun <T> Observable<T>.doOnNextOrFinished(consumer: () -> Unit): Observable<T> = doOnNext { consumer() }.doOnError { consumer() }.doOnComplete { consumer() }

/**
 * Calls the specified action after this Observable signals onError or onCompleted or gets disposed by the downstream.
 */
fun <T> Observable<T>.doOnErrorOrFinished(consumer: () -> Unit): Observable<T> = doOnError { consumer() }.doOnComplete { consumer() }

/**
 * Calls the specified action after this Single signals onError or doOnSuccess or gets disposed by the downstream.
 */
fun <T> Single<T>.doOnErrorOrFinished(consumer: () -> Unit): Single<T> = doOnError { consumer() }.doOnSuccess { consumer() }

/**
 * Calls the specified action after this Complete signals onError or doOnComplete or gets disposed by the downstream.
 */
fun Completable.doOnErrorOrFinished(consumer: () -> Unit): Completable = doOnError { consumer() }.doOnComplete { consumer() }

/**
 * Adds this Disposable to the given container [CompositeDisposable] or disposes it if the container has been disposed.
 */
fun Disposable.disposedBy(compositeDisposable: CompositeDisposable) = compositeDisposable.add(this)

/**
 * Removes and disposes this Disposable from the given container [CompositeDisposable] if it is part of this container.
 */
fun Disposable.removeFrom(compositeDisposable: CompositeDisposable) = compositeDisposable.remove(this)

/**
 * Execute the given [consumer] once this [Observable] is subscribed and adds the disposable result to the given container [compositeDisposable].
 */
fun <T> Observable<T>.subscribeWith(compositeDisposable: CompositeDisposable, consumer: (T) -> Unit) = this.subscribe { consumer(it) }.disposedBy(compositeDisposable)
