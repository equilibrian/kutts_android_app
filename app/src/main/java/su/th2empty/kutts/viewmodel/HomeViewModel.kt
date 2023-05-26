/*
 * Copyright (c)  2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import su.th2empty.kutts.model.Contact
import su.th2empty.kutts.model.Location
import su.th2empty.kutts.repository.ContactsRepository
import su.th2empty.kutts.repository.KuttsDatabase
import su.th2empty.kutts.repository.LocationsRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    contactsRepository: ContactsRepository,
    locationsRepository: LocationsRepository,
    application: Application
) : AndroidViewModel(application) {

    @Suppress("unused")
    constructor(application: Application) : this(
        ContactsRepository(KuttsDatabase.getDatabase(application).contactsDao()),
        LocationsRepository(KuttsDatabase.getDatabase(application).locationsDao()),
        application
    )

    val allContacts: LiveData<List<Contact>> = contactsRepository.allContacts
    val allLocations: LiveData<List<Location>> = locationsRepository.allLocations


}