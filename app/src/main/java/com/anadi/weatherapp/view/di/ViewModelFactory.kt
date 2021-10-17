/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anadi.weatherapp.view.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anadi.weatherapp.data.network.state.NetworkMonitor
import com.anadi.weatherapp.domain.location.AddLocationUseCase
import com.anadi.weatherapp.domain.location.CheckUpdatesAllLocationsUseCase
import com.anadi.weatherapp.domain.location.LocationRepository
import com.anadi.weatherapp.domain.location.UpdateLocationUseCase
import com.anadi.weatherapp.domain.places.PlacesWrapper
import com.anadi.weatherapp.view.ui.details.DetailsViewModel
import com.anadi.weatherapp.view.ui.locations.LocationsViewModel
import com.anadi.weatherapp.view.ui.providers.ProvidersViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(
        private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as? T
                ?: throw IllegalArgumentException("The requested ViewModel isn't bound: ${modelClass.simpleName}")
    }

}

@Target(AnnotationTarget.FUNCTION)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
class ViewModelModule {

    @Provides
    fun getViewModelFactory(
            viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(viewModels)
    }

    @Provides
    @[IntoMap ViewModelKey(LocationsViewModel::class)]
    fun locationsViewModel(
            networkMonitor: NetworkMonitor,
            addLocationUseCase: AddLocationUseCase,
            placesWrapper: PlacesWrapper,
            checkUpdatesAllLocationsUseCase: CheckUpdatesAllLocationsUseCase,
            locationRepository: LocationRepository
    ): ViewModel {
        return LocationsViewModel(
                networkMonitor,
                addLocationUseCase,
                placesWrapper,
                checkUpdatesAllLocationsUseCase,
                locationRepository
        )
    }

    @Provides
    @[IntoMap ViewModelKey(ProvidersViewModel::class)]
    fun providersViewModel(locationRepository: LocationRepository): ViewModel {
        return ProvidersViewModel(locationRepository)
    }

    @Provides
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    fun detailsViewModel(
            updateLocationUseCase: UpdateLocationUseCase, locationRepository: LocationRepository
    ): ViewModel {
        return DetailsViewModel(updateLocationUseCase, locationRepository)
    }
}
