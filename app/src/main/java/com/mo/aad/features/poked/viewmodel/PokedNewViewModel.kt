package com.mo.aad.features.poked.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mo.aad.extensions.LiveCoroutinesViewModel
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.repository.PokedNewRepository
import com.mo.aad.network.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:
 */
class PokedNewViewModel constructor(
    private val mPokedRepository: PokedNewRepository
) : LiveCoroutinesViewModel() {
    //这是数据库取数据
    var pokemonLiveData: MutableLiveData<Resource<List<Pokemon>>> = MutableLiveData()


    fun getPokedChildList(size: Int, page: Int){
        Log.e("PokedViewModel", "getPokedList"+"这个是由基本数据筛选后的数据")
        mPokedRepository.getPokedChildList(size, page).onEach {
            pokemonLiveData.value = it
        }.launchIn(viewModelScope)
    }
}