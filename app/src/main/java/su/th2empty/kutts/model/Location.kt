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

/**
 * Represents a location.
 *
 * @property id The unique identifier of the location.
 * @property index The postal index of the location.
 * @property city The city where the educational institution is located.
 * @property street The street where the educational institution building is located.
 * @property houseNumber The house number where the educational institution building is located.
 */
@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "idx") val index: Long,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "house_number") val houseNumber: String
)