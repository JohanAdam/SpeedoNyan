package com.nyan.speedonyan.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider constructor() : BaseSchedulerProvider {

    override fun io() = Schedulers.io()

    override fun computation() = Schedulers.computation()

    override fun ui() = AndroidSchedulers.mainThread()

}