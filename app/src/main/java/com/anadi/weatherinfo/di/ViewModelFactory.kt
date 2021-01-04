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

package com.anadi.weatherinfo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anadi.weatherinfo.repository.LocationsCash
import com.anadi.weatherinfo.ui.addlocation.AddLocationViewModel
import com.anadi.weatherinfo.ui.addlocation.LocationsProvider
import com.anadi.weatherinfo.ui.details.DetailsViewModel
import com.anadi.weatherinfo.ui.locations.LocationsViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
            viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
class ViewModelModule {

    @Provides
    fun getViewModelFactory(viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(viewModels)
    }

    @Provides
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun locationsViewModel(locationsCash: LocationsCash): ViewModel {
        return LocationsViewModel(locationsCash)
    }

    @Provides
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun detailsViewModel(locationsCash: LocationsCash): ViewModel {
        return DetailsViewModel(locationsCash)
    }

    @Provides
    @IntoMap
    @ViewModelKey(AddLocationViewModel::class)
    fun addLocationViewModel(locationsCash: LocationsCash, locationsProvider: LocationsProvider): ViewModel {
        return AddLocationViewModel(locationsCash, locationsProvider)
    }
}