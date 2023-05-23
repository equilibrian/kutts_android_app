/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import su.th2empty.kutts.model.PdfDocument
import timber.log.Timber

class HtmlParser {

    companion object {

        @Throws(Exception::class)
        fun getPdfDocuments(url: String): List<PdfDocument> {
            val pdfDocuments = mutableListOf<PdfDocument>()

            try {
                val doc: Document = Jsoup.connect(url).get()
                val links = doc.select("a[href$=.pdf]")

                for (link in links) {
                    val pdfUrl = link.absUrl("href")
                    val pdfTitle = link.text()
                    val pdfDocument = PdfDocument(pdfTitle, pdfUrl)
                    pdfDocuments.add(pdfDocument)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }

            return pdfDocuments
        }
    }
}