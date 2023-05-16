/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.repository

import androidx.lifecycle.LiveData
import su.th2empty.kutts.model.Contact
import su.th2empty.kutts.model.EducationalProgram

class EducationalProgramsRepository(private val educationalProgramsDao: EducationalProgramsDao) {
    val allPrograms: LiveData<List<EducationalProgram>> = educationalProgramsDao.getAllPrograms()

    fun getProgramsByCategory(categoryId: Int): LiveData<List<EducationalProgram>> {
        return educationalProgramsDao.getProgramsByCategory(categoryId)
    }

    fun getProgramById(id: Int): LiveData<EducationalProgram> {
        return educationalProgramsDao.getProgramById(id)
    }
}