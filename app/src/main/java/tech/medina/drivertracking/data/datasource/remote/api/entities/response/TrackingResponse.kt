package tech.medina.drivertracking.data.datasource.remote.api.entities.response

data class TrackingResponse(
    val status: String
) {

    //This declaration is necessary to provide mocks of this class by extensions in the tests
    companion object { }

}