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
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import su.th2empty.kutts.KuttsApplication
import su.th2empty.kutts.exceptions.DatabaseValidationException
import su.th2empty.kutts.model.Contact
import su.th2empty.kutts.model.Dormitory
import su.th2empty.kutts.model.EducationalCategory
import su.th2empty.kutts.model.EducationalProgram
import su.th2empty.kutts.model.Location
import su.th2empty.kutts.security.Security
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * The Room database for the application.
 */
@Database(entities = [
    Contact::class,
    Location::class,
    EducationalProgram::class,
    EducationalCategory::class,
    Dormitory::class], version = 2)
abstract class KuttsDatabase : RoomDatabase() {

    /**
     * Returns the Data Access Object (DAO) for contacts.
     * @return [ContactsDao] object.
     */
    abstract fun contactsDao(): ContactsDao

    /**
     * Returns the Data Access Object (DAO) for locations.
     * @return [LocationsDao] object.
     */
    abstract fun locationsDao(): LocationsDao

    /**
     * Returns the Data Access Object (DAO) for educational programs.
     * @return [EducationalProgramsDao] object.
     */
    abstract fun educationalProgramsDao(): EducationalProgramsDao

    /**
     * Returns the Data Access Object (DAO) for dormitory
     * @return [DormitoryDao] object
     */
    abstract fun dormitoryDao(): DormitoryDao

    companion object {
        private const val TAG = "KuttsDatabase"
        private const val DATABASE_NAME = "database.db"
        private val databasePath: String = KuttsApplication.instance.getDatabasePath(DATABASE_NAME).path

        @Volatile
        private var INSTANCE: KuttsDatabase? = null

        private fun compareDatabases(context: Context): Boolean {
            val assetDatabaseStream = context.assets.open("database.db")

            val deviceDatabaseFile = File(databasePath)
            val deviceDatabaseStream = FileInputStream(deviceDatabaseFile)

            return try {
                val assetHash = Security.calculateMD5Hash(assetDatabaseStream)
                val deviceHash = Security.calculateMD5Hash(deviceDatabaseStream)

                assetHash == deviceHash
            } catch (ex: IOException) {
                Timber.tag(TAG).e(ex)
                false
            } finally {
                assetDatabaseStream.close()
                deviceDatabaseStream.close()
            }
        }

        @Throws(DatabaseValidationException::class)
        fun validateDatabase(context: Context) {
            Timber.tag(TAG).i("Validation database...")
            val existingDatabase = File(databasePath)

            if (!existingDatabase.exists() || !compareDatabases(context))
                throw DatabaseValidationException()

            Timber.tag(TAG).i("Validation passed")
        }

        private fun setupDatabase(context: Context): KuttsDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                KuttsDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }

        /**
         * Returns an instance of the KuttsDatabase.
         * @param context The application context.
         * @return KuttsDatabase instance.
         */
        fun getDatabase(context: Context): KuttsDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = setupDatabase(context)
                INSTANCE = database
                return@synchronized database
            }
        }

        /**
         * Copies the pre-populated database from assets to the local storage.
         * @param context The application context.
         */
        fun copyDatabaseFromAssets(context: Context) {
            Timber.tag(TAG).i("copying database from assets...")
            try {
                val inputStream = context.assets.open(DATABASE_NAME)
                val outputStream = FileOutputStream(databasePath)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                Timber.tag(TAG).i("Copying database successfully completed.")
            } catch (ex: IOException) {
                Timber.tag(TAG).e(ex)
            }
        }
    }
}