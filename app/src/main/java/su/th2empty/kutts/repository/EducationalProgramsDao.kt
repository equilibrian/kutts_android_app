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
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import su.th2empty.kutts.model.Contact
import su.th2empty.kutts.model.EducationalProgram

@Dao
interface EducationalProgramsDao {
    @Query("SELECT * FROM educational_programs")
    fun getAllPrograms(): LiveData<List<EducationalProgram>>

    @Query("SELECT * FROM educational_programs WHERE id = :id")
    fun getProgramById(id: Int): LiveData<EducationalProgram>

    @Query("SELECT * FROM educational_programs INNER JOIN educational_categories ON :id = educational_categories.id ORDER BY educational_categories.name")
    fun getProgramsByCategory(id: Int): LiveData<List<EducationalProgram>>
}