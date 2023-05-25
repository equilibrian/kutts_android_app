/*
 * Copyright (c) 2023 Denis Novikov
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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.th2empty.kutts.model.Dormitory
import su.th2empty.kutts.model.Location
import su.th2empty.kutts.model.PdfDocument
import su.th2empty.kutts.repository.DormitoryRepository
import su.th2empty.kutts.repository.KuttsDatabase
import su.th2empty.kutts.repository.LocationsRepository
import su.th2empty.kutts.utils.HtmlParser
import timber.log.Timber
import javax.inject.Inject

class DormitoryViewModel @Inject constructor(
    dormitoryRepository: DormitoryRepository,
    private val locationsRepository: LocationsRepository,
    application: Application
) : AndroidViewModel(application) {

    companion object {
        const val DORMITORY_INFO_WEB_URL = "https://kutts.ru/abiturientam/obshhezhitie/"
    }

    val dormitoryInfo: LiveData<Dormitory> = dormitoryRepository.dormitoryInfo
    private val _dormitoryLocation = MutableLiveData<Location>()
    val dormitoryLocation: LiveData<Location> get() = _dormitoryLocation
    private val _pdfDocuments = MutableLiveData<List<PdfDocument>>()
    val pdfDocuments: LiveData<List<PdfDocument>> get() = _pdfDocuments

    @Suppress("unused")
    constructor(application: Application) : this(
        DormitoryRepository(KuttsDatabase.getDatabase(application).dormitoryDao()),
        LocationsRepository(KuttsDatabase.getDatabase(application).locationsDao()),
        application
    )

    @Throws(Exception::class)
    fun fetchPdfDocuments() {
        viewModelScope.launch {
            try {
                val documents = withContext(Dispatchers.IO) {
                    HtmlParser.getPdfDocuments(DORMITORY_INFO_WEB_URL)
                }
                _pdfDocuments.value = documents
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    fun fetchLocation(id: Int) {
        viewModelScope.launch {
            val location = withContext(Dispatchers.IO) {
                 locationsRepository.getLocationById(id)
            }

            _dormitoryLocation.value = location
        }
    }
}