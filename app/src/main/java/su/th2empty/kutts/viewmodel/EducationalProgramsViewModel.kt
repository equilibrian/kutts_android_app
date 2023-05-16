/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
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
import su.th2empty.kutts.model.EducationalProgram
import su.th2empty.kutts.repository.EducationalProgramsRepository
import su.th2empty.kutts.repository.KuttsDatabase
import javax.inject.Inject

class EducationalProgramsViewModel@Inject constructor(
    programsRepository: EducationalProgramsRepository,
    application: Application
) : AndroidViewModel(application) {

    @Suppress("unused")
    constructor(application: Application) : this(
        EducationalProgramsRepository(KuttsDatabase.getDatabase(application).educationalProgramsDao()),
        application
    )

    val educationalPrograms: LiveData<List<EducationalProgram>> = programsRepository.allPrograms
}