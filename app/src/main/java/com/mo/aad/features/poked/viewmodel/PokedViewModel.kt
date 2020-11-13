package com.mo.aad.features.poked.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import com.mo.aad.features.poked.repository.PokedRepository
import com.mo.aad.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:
 */
@FlowPreview
@ExperimentalCoroutinesApi
class PokedViewModel(private val mPokedRepository: PokedRepository):ViewModel() {
     var mPokemonLiveData: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
     val mPokemonInfoLiveData: MutableLiveData<Resource<PokemonInfo>> = MutableLiveData()


    fun getPokedList(size:Int,page:Int){
       mPokedRepository.getPokedList(size,page).onEach {
           mPokemonLiveData.value = it
       }.launchIn(viewModelScope)
    }


    fun getPokedItem(name: String){
      mPokedRepository.getPokedItem(name).onEach {
          mPokemonInfoLiveData.value = it
      }.launchIn(viewModelScope)
    }
}