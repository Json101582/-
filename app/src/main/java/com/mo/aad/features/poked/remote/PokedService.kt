package com.mo.aad.features.poked.remote

import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:神兽Api
 */
interface PokedService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(@Path("name") name: String): PokemonInfo
}