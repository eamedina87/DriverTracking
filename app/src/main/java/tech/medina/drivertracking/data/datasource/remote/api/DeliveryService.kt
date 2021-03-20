package tech.medina.drivertracking.data.datasource.remote.api

import retrofit2.http.GET
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote

interface DeliveryService {

    @GET
    suspend fun getDeliveryList(): DeliveryRemote

    @GET
    suspend fun getDeliveryDetailForId(id: Long): DeliveryRemote

}