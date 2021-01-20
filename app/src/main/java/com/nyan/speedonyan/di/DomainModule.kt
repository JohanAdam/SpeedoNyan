package com.nyan.speedonyan.di

import com.nyan.data.LocationRepoImpl
import com.nyan.domain.repo.LocationRepo
import com.nyan.domain.usecases.GetLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DomainModule {

    @Provides
    fun providesGetLocationUseCases(locationRepo: LocationRepo) : GetLocationUseCase {
        return GetLocationUseCase(locationRepo)
    }

}