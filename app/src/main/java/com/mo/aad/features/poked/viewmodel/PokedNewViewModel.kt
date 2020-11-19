package com.mo.aad.features.poked.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mo.aad.extensions.LiveCoroutinesViewModel
import com.mo.aad.features.poked.dao.PokemonDao
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.repository.PokedRepository
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
class PokedNewViewModel(
    private val mPokedRepository: PokedRepository,
    private val mPokemonDao: PokemonDao
) : LiveCoroutinesViewModel() {
    //这是数据库取数据
    var pokemonLiveData: MutableLiveData<List<Pokemon>> = MutableLiveData()

    suspend fun getDaoPokedList(size: Int, page: Int) {
      val  mlist = mPokemonDao.getPokemonList(page_ = page)
      mPokedRepository.getPokedList(size,page).onEach {
       it.data?.let { items->
           pokemonLiveData.value = items.results
           mPokemonDao.insertPokemonList(items.results)
       }
      }.launchIn(viewModelScope)
    }
}