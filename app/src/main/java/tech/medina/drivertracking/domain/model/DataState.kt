package tech.medina.drivertracking.domain.model

sealed class DataState<out T> {

    object Loading: DataState<Nothing>()
    class Success<out T>(val result: T): DataState<T>()
    class Error(val error: Any?): DataState<Any>()

}