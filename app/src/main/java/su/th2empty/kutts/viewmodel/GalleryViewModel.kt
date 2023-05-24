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
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.th2empty.kutts.utils.HtmlParser
import timber.log.Timber

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val DORMITORY_WEB_URL = "https://kutts.ru/abiturientam/obshhezhitie/"
    }

    private val _imgLinks = MutableLiveData<List<Uri>>()
    val imgLinks: LiveData<List<Uri>> get() = _imgLinks

    @Throws(Exception::class)
    fun fetchImgLinks() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val links = withContext(Dispatchers.IO) {
                    HtmlParser.getImages(DORMITORY_WEB_URL)
                }

                withContext(Dispatchers.Main) {
                    _imgLinks.value = links
                }
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }
}