package tech.medina.drivertracking.data.datasource.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote

interface DeliveryService {

    @GET("deliveries")
    suspend fun getDeliveryList(): DeliveriesRemote

    @GET("delivery")
    suspend fun getDeliveryDetailForId(@Query("id") id: Long): DeliveryRemote

}