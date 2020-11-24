package com.mo.aad.features.poked.repository



import androidx.annotation.WorkerThread
import androidx.lifecycle.liveData
import com.mo.aad.features.poked.dao.PokemonDao
import com.mo.aad.features.poked.data.Pokemon
import com.mo.aad.features.poked.data.PokemonInfo
import com.mo.aad.features.poked.data.PokemonResponse
import com.mo.aad.features.poked.remote.PokedService
import com.mo.aad.network.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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
class PokedRepository constructor(
    private val pokedService: PokedService,
//    private val mDao:PokemonDao
      ) {

   fun getPokedList(size: Int, page: Int): Flow<Resource<PokemonResponse>> {
        return networkBoundResource(
            fetch = {
                pokedService.fetchPokemonList(size, page)
            }
        )
    }

    fun getPokedItem(name: String): Flow<Resource<PokemonInfo>> {
        return networkBoundResource(
            fetch = {
                pokedService.fetchPokemonInfo(name)
            }
        )
    }


    //这里是liveData返回
    suspend fun getListData(size: Int, page: Int) = liveData{
          emit(Resource.loading(null))
           val mResponse= pokedService.fetchPokemonList1(size, page)
           try {
               emit(Resource.success(mResponse))
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

    //这里是以flow返回
    @WorkerThread
   fun getListFlowData(size: Int, page: Int): Flow<Resource<PokemonResponse>> {
    return flow{
        emit(Resource.loading(null))
        val mResponse= pokedService.fetchPokemonList1(size, page)
        try {
            emit(Resource.success(mResponse))
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
     }.flowOn(Dispatchers.IO)
   }


    //这里是以flow返回
    @WorkerThread
    fun getListFlowData1(size: Int, page: Int): Flow<Resource<List<Pokemon>>> {
        return flow{
            emit(Resource.loading(null))
//            val mDaoList = mDao.getPokemonList(page)
//            if (mDaoList.isEmpty()){
                val mResponse= pokedService.fetchPokemonList1(size, page)
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
//            }else{
//                emit(Resource.success(mDaoList))
//            }
        }.flowOn(Dispatchers.IO)
    }
}