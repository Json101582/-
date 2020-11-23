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
import kotlinx.coroutines.async
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
    var mPokemonLiveData: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
    val mPokemonInfoLiveData: MutableLiveData<Resource<PokemonInfo>> = MutableLiveData()

    //这是数据库取数据
    var pokemonLiveData: LiveData<Resource<List<Pokemon>>> = MutableLiveData()


    fun getPokedList(size: Int, page: Int) {
        mPokedRepository.getPokedList(size, page).onEach {
            mPokemonLiveData.value = it
        }.launchIn(viewModelScope)
    }


    fun getPokedItem(name: String) {
        mPokedRepository.getPokedItem(name).onEach {
            mPokemonInfoLiveData.value = it
        }.launchIn(viewModelScope)
    }

    //测试数据
    fun getListData(size: Int, page: Int) {
        viewModelScope.launch {
              pokemonLiveData =mPokedRepository.getListData(size, page)
        }
    }

}