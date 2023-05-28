package com.example.feature_supervisor_research.aspirantdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Article
import com.example.core.model.Aspirant
import com.example.core.model.Research
import com.example.core.researchUseCase.GetAllResearchesByIdUseCase
import com.example.core.researchUseCase.GetResearchByIdUseCase
import com.example.core.researchUseCase.UpdateArticleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AspirantDetailsViewModel @Inject constructor(
    private val getAllResearchesByIdUseCase: GetAllResearchesByIdUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val getResearchByIdUseCase: GetResearchByIdUseCase
) : ViewModel() {

    private var _listOfResearch = MutableLiveData<List<Article>>()
    val listOfResearch: LiveData<List<Article>>
        get() = _listOfResearch

    private var _research = MutableLiveData<Research>()
    val research: LiveData<Research>
        get() = _research

    fun getInformation(aspirantId: String) {
        viewModelScope.launch {
            _listOfResearch.value = getAllResearchesByIdUseCase.execute(aspirantId)
        }
    }

    fun getResearchByIdUseCase(researchId: String) {
        viewModelScope.launch {
            _research.value = getResearchByIdUseCase.execute(researchId)
        }
    }

    fun updateArticle(researchId: String, listOfArticles: List<Article>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateArticleUseCase.execute(listOfArticles, researchId)
            }
            withContext(Dispatchers.IO) {
                _listOfResearch.postValue(getAllResearchesByIdUseCase.execute(researchId))
            }
        }
    }
}