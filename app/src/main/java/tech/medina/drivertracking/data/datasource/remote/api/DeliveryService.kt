package tech.medina.drivertracking.data.datasource.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesResponse
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryResponse

interface DeliveryService {

    @GET("deliveries")
    suspend fun getDeliveryList(): DeliveriesResponse

    @GET("delivery")
    suspend fun getDeliveryDetailForId(@Query("id") id: Long): DeliveryResponse

}