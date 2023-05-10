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

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import su.th2empty.kutts.BuildConfig
import su.th2empty.kutts.model.Contact
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

class KuttsDatabaseHelper {
    companion object {
        private const val DATABASE_NAME = "database.db"

        /**
         * Loads contacts from the database located in the assets folder.
         * @param context Application context.
         * @param databaseName Database name in assets-directory.
         * @return Contact list.
         */
        suspend fun loadContactsFromAsset(context: Context, databaseName: String): List<Contact> {
            val inputStream = context.assets.open(databaseName)
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)

            if (!outputFile.exists() || BuildConfig.DEBUG) {
                withContext(Dispatchers.IO) {
                    outputFile.createNewFile()
                }
            }

            inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }

            return try {
                KuttsDatabase.getDatabase(context).contactsDao().getAllContacts().value ?: emptyList()
            } catch (ex: Exception) {
                Timber.e(ex, "Failed to read contacts from database")
                emptyList()
            }
        }
    }
}