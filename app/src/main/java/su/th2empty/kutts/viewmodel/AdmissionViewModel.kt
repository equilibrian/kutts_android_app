package su.th2empty.kutts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.th2empty.kutts.model.PdfDocument
import su.th2empty.kutts.utils.HtmlParser
import timber.log.Timber

class AdmissionViewModel : ViewModel() {
    companion object {
        const val ADMISSION_WEB_URL = "https://kutts.ru/abiturientam/podat-zayavlenie-na-postuplenie/"
    }

    private val _pdfDocuments = MutableLiveData<List<PdfDocument>>()
    val pdfDocuments: LiveData<List<PdfDocument>> get() = _pdfDocuments

    @Throws(Exception::class)
    fun fetchPdfDocuments() {
        viewModelScope.launch {
            try {
                val documents = withContext(Dispatchers.IO) {
                    HtmlParser.getPdfDocuments(ADMISSION_WEB_URL)
                }
                _pdfDocuments.value = documents
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }
}