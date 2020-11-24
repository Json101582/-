package com.mo.aad.features.poked.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import com.mo.aad.features.poked.repository.PokedRepository
import com.mo.aad.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:
 */
@FlowPreview
@ExperimentalCoroutinesApi
class PokedViewModel(private val mPokedRepository: PokedRepository) :ViewModel() {
    var mPokemonLiveData: LiveData<Resource<PokemonResponse>> = MutableLiveData()
    val mPokemonInfoLiveData: MutableLiveData<Resource<PokemonInfo>> = MutableLiveData()
    //ceshi
    var mPokemonLiveData1: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()

    //ceshi
    var mPokemonListLiveData: MutableLiveData<Resource<List<Pokemon>>> = MutableLiveData()

    fun getPokedList(size: Int, page: Int) {
        mPokedRepository.getPokedList(size, page).onEach {
            mPokemonLiveData1.value = it
        }.launchIn(viewModelScope)
    }

   fun getPokedChildList(size: Int, page: Int){
       mPokedRepository.getPokedChildList(size, page).onEach {
           mPokemonListLiveData.value = it
       }.launchIn(viewModelScope)
   }
    fun getPokedItem(name: String) {
        mPokedRepository.getPokedItem(name).onEach {
            mPokemonInfoLiveData.value = it
        }.launchIn(viewModelScope)
    }

    //返回livedata
    fun getListData(size: Int, page: Int) {
        viewModelScope.launch {
            mPokemonLiveData =mPokedRepository.getListData(size, page)
        }
    }

    //返回Flow最大层json数据强转为livedata
    fun getListFlowData(size: Int, page: Int) {
       mPokedRepository.getListFlowData(size, page).onEach {
               mPokemonLiveData1.value =  it
                    }.launchIn(viewModelScope)
        Log.e("测试>>>>flow>>>", "getListFlowData: $mPokemonLiveData1")
    }


    //返回Flow最大层但是接收是具体数据强转为livedata
    fun getListFlowData1(size: Int, page: Int) {
        mPokedRepository.getListFlowData1(size, page).onEach {
            mPokemonListLiveData.value =  it
        }.launchIn(viewModelScope)
        Log.e("测试Flow>>>>>>>", "getListFlowData: $mPokemonLiveData1")
    }
}