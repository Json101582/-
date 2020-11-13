package com.mo.aad.features.poked.repository

import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import com.mo.aad.features.poked.remote.PokedService
import com.mo.aad.network.Resource
import com.mo.aad.network.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:
 */
@ExperimentalCoroutinesApi
@FlowPreview
class PokedRepository(private val pokedService: PokedService) {

    fun getPokedList(size:Int,page:Int):Flow<Resource<PokemonResponse>>{
       return networkBoundResource(
           fetch = {
               pokedService.fetchPokemonList(size, page)
           },
       )
    }

    fun getPokedItem(name: String):Flow<Resource<PokemonInfo>>{
        return networkBoundResource(
            fetch = {
                pokedService.fetchPokemonInfo(name)
            },
        )
    }

}