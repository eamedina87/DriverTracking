package tech.medina.drivertracking.data.datasource.remote.api.entities.response

data class TrackingResponse(
    val status: String
)

enum class ResponseStatus(val status: String) {
    OK("ok"), OTHER("")
}