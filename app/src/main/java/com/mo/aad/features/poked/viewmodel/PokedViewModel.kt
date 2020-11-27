package com.mo.aad.features.poked.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import com.mo.aad.features.poked.repository.PokedRepository
import com.mo.aad.network.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch



/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:
 */

class PokedViewModel(private val mPokedRepository: PokedRepository) :ViewModel() {
    var mPokemonLiveData: LiveData<Resource<PokemonResponse>> = MutableLiveData()
    val mPokemonInfoLiveData: MutableLiveData<Resource<PokemonInfo>> = MutableLiveData()
    //ceshi
    var mPokemonLiveData1: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()

    //ceshi
    var mPokemonListLiveData: MutableLiveData<Resource<List<Pokemon>>> = MutableLiveData()
//    private val mDao: PokemonDao = scope.get<AppDatabase>().mPokemonDao()
    fun getPokedList(size: Int, page: Int) {
        mPokedRepository.getPokedList(size, page).onEach {
            mPokemonLiveData1.value = it
        }.launchIn(viewModelScope)
    }

   fun getPokedChildList(size: Int, page: Int){
       Log.e("PokedViewModel", "getPokedList"+"这个是由基本数据筛选后的数据")
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
        Log.e("PokedViewModel", "getPokedList"+"这里是liveData返回")
        viewModelScope.launch {
            mPokemonLiveData =mPokedRepository.getListData(size, page)
        }
    }

    //返回Flow最大层json数据强转为livedata
    fun getListFlowData(size: Int, page: Int) {
        Log.e("PokedViewModel", "getPokedList"+"这里是以flow返回")
       mPokedRepository.getListFlowData(size, page).onEach {
               mPokemonLiveData1.value =  it
                    }.launchIn(viewModelScope)
    }


    //返回Flow最大层但是接收是具体数据强转为livedata
    fun getListFlowData1(size: Int, page: Int) {
        Log.e("PokedViewModel", "getPokedList"+"这里是以flow返回筛选后的数据")
//        mDao.getPokemonList(page)
        mPokedRepository.getListFlowData1(size, page).onEach {
            mPokemonListLiveData.value =  it
        }.launchIn(viewModelScope)
    }
}