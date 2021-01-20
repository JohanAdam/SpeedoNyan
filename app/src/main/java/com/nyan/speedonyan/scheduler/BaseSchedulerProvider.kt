package com.nyan.speedonyan.scheduler

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Inject

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}