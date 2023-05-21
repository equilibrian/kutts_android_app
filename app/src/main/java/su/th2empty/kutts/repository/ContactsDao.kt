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

/**
 * Data Access Object (DAO) for accessing the 'contacts' table.
 */
@Dao
interface ContactsDao {
    /**
     * Retrieves all contacts from the 'contacts' table.
     *
     * @return LiveData containing a list of all contacts.
     */
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): LiveData<List<Contact>>

    /**
     * Retrieves a contact from the 'contacts' table by its ID.
     *
     * @param id The ID of the contact to retrieve.
     * @return LiveData containing the contact with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContactById(id: Int): LiveData<Contact>

    /**
     * Inserts a list of contacts into the 'contacts' table.
     * If a contact with the same ID already exists, it will be replaced.
     *
     * @param contacts The list of contacts to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<Contact>)
}