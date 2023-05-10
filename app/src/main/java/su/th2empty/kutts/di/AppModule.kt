/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import su.th2empty.kutts.repository.ContactsDao
import su.th2empty.kutts.repository.ContactsRepository
import su.th2empty.kutts.repository.KuttsDatabase
import su.th2empty.kutts.repository.LocationsDao
import su.th2empty.kutts.repository.LocationsRepository
import su.th2empty.kutts.viewmodel.HomeViewModel
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideContactsDao(context: Context): ContactsDao {
        return KuttsDatabase.getDatabase(context).contactsDao()
    }

    @Provides
    @Singleton
    fun provideLocationsDao(context: Context): LocationsDao {
        return KuttsDatabase.getDatabase(context).locationsDao()
    }

    @Provides
    @Singleton
    fun provideContactsRepository(contactsDao: ContactsDao): ContactsRepository {
        return ContactsRepository(contactsDao)
    }

    @Provides
    @Singleton
    fun provideLocationsRepository(locationsDao: LocationsDao): LocationsRepository {
        return LocationsRepository(locationsDao)
    }

    @Provides
    @Singleton
    fun provideHomeViewModel(
        contactsRepository: ContactsRepository,
        locationsRepository: LocationsRepository,
        application: Application
    ): HomeViewModel {
        return HomeViewModel(contactsRepository, locationsRepository, application)
    }
}