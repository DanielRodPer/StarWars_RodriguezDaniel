package com.dam.liststarwars.data.network

/**
sealed class BaseResult {
    data class Success<T> (var data: T): BaseResult()
    data class Error(var exception: Exception): BaseResult()
}

Este formato exige el siguiente código del viewModel

state = when(result){
    is BaseResult.Error ->{
        state.copy(emailError = result.exception.message?:"Error desconocido")
    }
    is BaseResult.Success<*> ->{
        state.copy(account = result.data as Account)
    }
}
**/


sealed class BaseResult<out T> {
    data class Success<T> (var data: T): BaseResult<T>()
    data class Error(var exception: Exception): BaseResult<Nothing>()
}