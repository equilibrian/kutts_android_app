package su.th2empty.kutts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.th2empty.kutts.model.PdfDocument
import su.th2empty.kutts.utils.HtmlParser
import timber.log.Timber

class DocumentsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val DOCUMENTS_WEB_URL = "https://kutts.ru/abiturientam/dokumenty-priemnoj-komissii/"
    }

    private val _pdfDocuments = MutableLiveData<List<PdfDocument>>()
    val pdfDocuments: LiveData<List<PdfDocument>> get() = _pdfDocuments

    @Throws(Exception::class)
    fun fetchPdfDocuments() {
        viewModelScope.launch {
            try {
                val documents = withContext(Dispatchers.IO) {
                    HtmlParser.getPdfDocuments(DOCUMENTS_WEB_URL)
                }
                _pdfDocuments.value = documents
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }
}