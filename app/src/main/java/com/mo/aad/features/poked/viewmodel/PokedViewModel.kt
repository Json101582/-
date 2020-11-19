package com.mo.aad.features.poked.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mo.aad.extensions.LiveCoroutinesViewModel
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
class PokedViewModel(private val mPokedRepository: PokedRepository): ViewModel() {
     var mPokemonLiveData: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
     val mPokemonInfoLiveData: MutableLiveData<Resource<PokemonInfo>> = MutableLiveData()
     //这是数据库取数据
     var pokemonLiveData:MutableLiveData<List<Pokemon>> = MutableLiveData()

    fun getPokedList(size:Int, page:Int){
       mPokedRepository.getPokedList(size,page).onEach {
           mPokemonLiveData.value = it
       }.launchIn(viewModelScope)
    }


    fun getPokedItem(name: String){
      mPokedRepository.getPokedItem(name).onEach {
          mPokemonInfoLiveData.value = it
      }.launchIn(viewModelScope)
    }

  fun getDaoPokedList(size:Int, page:Int) {
        this.mPokedRepository.getPokedDaoListData(
          size,page,{
                Log.e("走进来>>>", "getDaoPokedList: $it")
                pokemonLiveData.value = it
            },{
                Log.e("走进来>>>错误", "getDaoPokedList: $it")
            }
        ).asLiveData()
  }
}