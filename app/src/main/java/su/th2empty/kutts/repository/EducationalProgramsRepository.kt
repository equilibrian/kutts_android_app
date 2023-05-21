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
import su.th2empty.kutts.model.EducationalProgram

/**
 * Repository class for accessing educational programs data.
 * @param educationalProgramsDao The Data Access Object (DAO) for educational programs.
 */
class EducationalProgramsRepository(private val educationalProgramsDao: EducationalProgramsDao) {

    /**
     * Retrieves all educational programs.
     * @return LiveData object containing a list of all educational programs.
     */
    val allPrograms: LiveData<List<EducationalProgram>> = educationalProgramsDao.getAllPrograms()
}