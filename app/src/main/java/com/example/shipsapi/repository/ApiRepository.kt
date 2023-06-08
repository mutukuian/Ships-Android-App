package com.example.shipsapi.repository

import com.example.shipsapi.retrofit.ApiService
import com.example.shipsapi.utils.DataStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ApiRepository @Inject constructor(private val apiService: ApiService){
    suspend fun getShipsList() = flow {
        emit(DataStatus.loading())
        val result = apiService.getShipsList()
        when(result.code()){
            200 ->{
                emit(DataStatus.success(result.body()))
            }
            400 ->{
                emit(DataStatus.error(result.message()))
            }
            500 ->{
                emit(DataStatus.error(result.message()))
            }
        }

    }
        .catch {
            emit(DataStatus.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}