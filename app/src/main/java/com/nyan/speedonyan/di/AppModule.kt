package com.nyan.speedonyan.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.nyan.speedonyan.scheduler.BaseSchedulerProvider
import com.nyan.speedonyan.scheduler.SchedulerProvider
import com.nyan.speedonyan.utils.PermissionManager

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun providesBaseScheduler() : BaseSchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    fun providesPermissionManager() : PermissionManager {
        return PermissionManager()
    }

}