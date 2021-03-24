package tech.medina.drivertracking.data.datasource.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import tech.medina.drivertracking.data.datasource.remote.api.entities.request.TrackingRequest
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse

interface TrackingService {

    @POST("tracking")
    suspend fun postTracking(@Body data: TrackingRequest): TrackingResponse

}