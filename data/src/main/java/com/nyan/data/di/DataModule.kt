package com.nyan.data.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nyan.data.LocationRepoImpl
import com.nyan.data.model.LocationDataModel
import com.nyan.data.source.LocationDataSource
import com.nyan.domain.repo.LocationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DataModule {

    @Provides
    fun providesLocationDataSource(context: FragmentActivity) : LocationDataSource {
        return LocationDataSource(context)
    }

    @Provides
    fun providesUserRepo(locationDataSource: LocationDataSource) : LocationRepo {
        return LocationRepoImpl(locationDataSource)
    }

}