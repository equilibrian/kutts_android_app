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

import su.th2empty.kutts.R

enum class AdmissionVariant(
    val titleResId: Int,
    val submitButtonEnabled: Boolean,
    val requiredDocuments: List<AdmissionDocuments>
) {

    PERSONAL_CABINET(R.string.st_through_personal_account, true, listOf(
        AdmissionDocuments.STATEMENT,
        AdmissionDocuments.PASSPORT,
        AdmissionDocuments.EDUCATION_DOCUMENT,
        AdmissionDocuments.PHOTO,
        AdmissionDocuments.SNILS,
        AdmissionDocuments.PROCESSING_PERSONAL_DATA
    )),

    ADMISSION_OFFICE(R.string.st_admission_committee, false, listOf(
        AdmissionDocuments.STATEMENT,
        AdmissionDocuments.PASSPORT,
        AdmissionDocuments.EDUCATION_DOCUMENT,
        AdmissionDocuments.PHOTO,
        AdmissionDocuments.SNILS
    )),

    POSTAL_OPERATOR(R.string.st_postal_operator, false, listOf(
        AdmissionDocuments.STATEMENT,
        AdmissionDocuments.PASSPORT,
        AdmissionDocuments.EDUCATION_DOCUMENT,
        AdmissionDocuments.PHOTO,
        AdmissionDocuments.SNILS,
        AdmissionDocuments.PROCESSING_PERSONAL_DATA
    )),

    EMAIL(R.string.st_by_email, false, listOf(
        AdmissionDocuments.STATEMENT,
        AdmissionDocuments.PASSPORT,
        AdmissionDocuments.EDUCATION_DOCUMENT,
        AdmissionDocuments.PHOTO,
        AdmissionDocuments.SNILS,
        AdmissionDocuments.PROCESSING_PERSONAL_DATA
    ))
}
