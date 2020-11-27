package com.mo.aad.features.main.viewmodel

import androidx.lifecycle.*
import com.mo.aad.features.main.data.LearningHoursUser
import com.mo.aad.features.main.data.SkillsScoreUser
import com.mo.aad.features.main.repository.MainRepository
import com.mo.aad.network.Resource
import kotlinx.coroutines.flow.*


class MainViewModel(private val repo: MainRepository) : ViewModel() {

    val topLearnersLiveData: MutableLiveData<Resource<List<LearningHoursUser>>> = MutableLiveData()
    val topSkillsLiveData: MutableLiveData<Resource<List<SkillsScoreUser>>> = MutableLiveData()

    fun getTopLearningUsers() {
        repo.getTopLearnerUsers().onEach {
            topLearnersLiveData.value = it
        }.launchIn(viewModelScope)
    }


     fun getTopSkillIQUsers() {
         repo.getTopSkillsUsers().onEach {
            topSkillsLiveData.value = it
        }.launchIn(viewModelScope)
    }
}