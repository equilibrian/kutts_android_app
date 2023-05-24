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

import android.net.Uri
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import su.th2empty.kutts.KuttsApplication
import su.th2empty.kutts.R
import su.th2empty.kutts.model.PdfDocument
import timber.log.Timber

class HtmlParser {
    private val context = KuttsApplication.instance.applicationContext

    companion object {
        private const val BASE_URL = "https://kutts.ru/abiturientam/"

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

        @Throws(Exception::class)
        fun getImages(url: String): List<Uri>  {
            val images = mutableListOf<Uri>()

            try {
                val doc: Document = Jsoup.connect(url).get()
                val links = doc.select("img[src~=(?i)\\.(png|jpe?g)]")

                for (element in links) {
                    val imageUrl = element.absUrl("src")
                    val imageUri = Uri.parse(imageUrl)
                    images.add(imageUri)
                }
            } catch (ex: Exception) {
                Timber.e(ex)
            }

            return images
        }
    }

    @Throws(Exception::class)
    fun getMonitoringLink(): Map<String, Uri> {
        val links: MutableMap<String, Uri> = mutableMapOf()

        try {
            val doc: Document = Jsoup.connect(BASE_URL).get()
            val monitoringLinkElement = doc.select("a").firstOrNull {
                it.text().contains(context.getString(R.string.st_title_monitoring))
            }
            val monitoringLink = monitoringLinkElement?.attr("href")

            if (monitoringLink != null) {
                links["monitoring"] = Uri.parse(monitoringLink)
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }

        return links
    }
}