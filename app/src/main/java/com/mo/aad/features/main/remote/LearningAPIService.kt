package com.mo.aad.features.main.remote

import androidx.lifecycle.LiveData
import com.mo.aad.features.main.data.LearningHoursUser
import retrofit2.http.GET

interface LearningAPIService {

    @GET("/api/hours")
    suspend fun getTopLearnerUsers() : List<LearningHoursUser>


    @GET("/api/hours")
    suspend fun getLiveDataTopLearnerUsers() : LiveData<List<LearningHoursUser>>
}