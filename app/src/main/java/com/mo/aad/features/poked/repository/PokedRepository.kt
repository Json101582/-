package com.mo.aad.features.poked.repository


import androidx.annotation.WorkerThread
import com.mo.aad.features.poked.dao.PokemonDao
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import com.mo.aad.features.poked.remote.PokedService
import com.mo.aad.network.Resource
import com.mo.aad.network.Status
import com.mo.aad.network.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach


/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:
 */
@ExperimentalCoroutinesApi
@FlowPreview
class PokedRepository(
    private val pokedService: PokedService
      ) {
   fun getPokedList(size: Int, page: Int): Flow<Resource<PokemonResponse>> {
        return networkBoundResource {
            pokedService.fetchPokemonList(size, page)
        }
    }

    fun getPokedItem(name: String): Flow<Resource<PokemonInfo>> {
        return networkBoundResource(
            fetch = {
                pokedService.fetchPokemonInfo(name)
            }
        )
    }

    @WorkerThread
    fun getPokedListData(
        size: Int, page: Int, onSuccess: (List<Pokemon>) -> Unit,
        onError: (String) -> Unit
    ) = flow {
        var mPokemon = listOf<Pokemon>()
//            mPokemonDao.getPokemonList(page_ = page)
        if (mPokemon.isEmpty()) {
            val resource = pokedService.fetchPokemonList1(size, page)
            resource.apply {
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        resource.data?.let {
                            mPokemon = it.results
                            mPokemon.forEach { pokemon -> pokemon.page = page }
//                            mPokemonDao.insertPokemonList(mPokemon)
                            emit(mPokemon)
                            onSuccess(mPokemon)
                        }
                    }
                    Status.ERROR -> {
                        resource.message?.let { onError(it) }
                    }
                }
            }
        } else {
            emit(mPokemon)
            onSuccess(mPokemon)
        }
    }.flowOn(Dispatchers.IO)


    @WorkerThread
    fun getPokedDaoListData(
        size: Int, page: Int, onSuccess: (List<Pokemon>) -> Unit,
        onError: (String) -> Unit
     ): Flow<Resource<PokemonResponse>>{
        return flow<Resource<PokemonResponse>> {
            var mPokemon = listOf<Pokemon>()
//            var mPokemon = mPokemonDao.getPokemonList(page_ = page)
            if (mPokemon.isEmpty()) {
                val resource = networkBoundResource {
                    pokedService.fetchPokemonList(size, page)
                }
                resource.onEach {
                    when (it.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            it.data?.let { items ->
                                mPokemon = items.results
                                mPokemon.forEach { pokemon -> pokemon.page = page }
//                                mPokemonDao.insertPokemonList(mPokemon)
                                onSuccess(mPokemon)
                            }
                        }
                        Status.ERROR -> {
                            onError(it.message!!)
                        }
                    }
                }.flowOn(Dispatchers.IO)
            } else {
                onSuccess(mPokemon)
            }
        }.flowOn(Dispatchers.IO)
    }

}