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

enum class AdmissionDocuments(val document: Int) {
    STATEMENT(R.string.document_statement),
    PASSPORT(R.string.document_passport),
    EDUCATION_DOCUMENT(R.string.document_education),
    PHOTO(R.string.document_photo),
    SNILS(R.string.document_snils),
    PROCESSING_PERSONAL_DATA(R.string.document_personal_data)
}