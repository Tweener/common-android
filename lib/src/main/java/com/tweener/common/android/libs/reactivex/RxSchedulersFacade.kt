package com.tweener.common.android.libs.reactivex

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Facade for Rx [Scheduler]s to use more user-friendly names.
 *
 * @author Vivien Mahe
 * @since 12/10/2020
 */
class RxSchedulersFacade {

    fun io(): Scheduler {
        return Schedulers.io()
    }

    fun computation(): Scheduler {
        return Schedulers.computation()
    }

    fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun sameThread(): Scheduler {
        return Schedulers.trampoline();
    }

    fun single(): Scheduler {
        return Schedulers.single();
    }

    fun newThread(): Scheduler {
        return Schedulers.newThread();
    }
}