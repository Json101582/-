package com.mo.aad.features.poked.repository


import android.widget.Toast
import androidx.lifecycle.liveData
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import com.mo.aad.features.poked.remote.PokedService
import com.mo.aad.network.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException


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


    //这里是要添加数据库操作的
    suspend fun getListData(size: Int, page: Int) = liveData{
       withContext(Dispatchers.IO){
           val mResponse= pokedService.fetchPokemonList1(size, page)
           emit(Resource.loading(null))
           try {
               emit(Resource.success(mResponse.results))
           } catch (throwable: Throwable) {
               when (throwable) {
                   is TimeoutCancellationException -> {
                       emit(Resource.error(NETWORK_ERROR_TIMEOUT, null))
                   }
                   is IOException -> {
                       emit(Resource.error(NETWORK_ERROR, null))
                   }
                   is HttpException -> {
                       emit(Resource.error(convertErrorBody(throwable), null))
                   }
                   else -> {
                       emit(Resource.error(UNKNOWN_ERROR, null))
                   }
               }
           }
       }
    }
}