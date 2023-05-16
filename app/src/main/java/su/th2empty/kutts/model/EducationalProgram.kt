/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "educational_programs")
data class EducationalProgram(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "program_code") val programCode: String,
    @ColumnInfo(name = "program_name") val programName: String,
    @ColumnInfo(name = "basic_education") val basicEducation: String,
    @ColumnInfo(name = "training_period") val trainingPeriod: String,
    @ColumnInfo(name = "training_form") val trainingForm: String,
    @ColumnInfo(name = "seats_number") val seatsNumber: Int,
    @ColumnInfo(name = "qualification") val qualification: String,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "commercial") val isCommercial: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "img") val img: String,
    @ColumnInfo(name = "funding_source") val fundingSource: String
)
