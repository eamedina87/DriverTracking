package tech.medina.drivertracking.data.datasource.remote

import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import tech.medina.drivertracking.base.BaseTest
import tech.medina.drivertracking.data.datasource.remote.api.DeliveryService
import tech.medina.drivertracking.data.datasource.remote.api.TrackingService
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse
import tech.medina.drivertracking.data.utils.mock

@ExperimentalCoroutinesApi
class RemoteDataSourceImplTest: BaseTest() {

    private val deliveryService = mockk<DeliveryService>() {
        coEvery { getDeliveryList() } returns DeliveryRemote.mock()
        coEvery { getDeliveryDetailForId(any()) } returns DeliveryRemote.mock(true)
    }

    private val trackingService = mockk<TrackingService>() {
        coEvery { postTracking(any()) } returns TrackingResponse.mock()
    }

    private val remoteDataSource = RemoteDataSourceImpl(deliveryService, trackingService)

    @Test
    fun `getDeliveryList from deliveryService successfully`() = dispatcher.runBlockingTest {
        with(remoteDataSource.getDeliveryList()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.deliveries).isNotNull()
            val first = this.deliveries.first()
            Truth.assertThat(first).isNotNull()
            Truth.assertThat(first.customerName).isNotNull()
            Truth.assertThat(first.requiresSignature).isNull()
            Truth.assertThat(first.specialInstructions).isNull()
        }
        coVerify {
            deliveryService.getDeliveryList()
        }
        coVerify(exactly = 0) {
            deliveryService.getDeliveryDetailForId(any())
            trackingService.postTracking(any())
        }
    }

    @Test
    fun `getDeliveryDetail from deliveryService successfully`() = dispatcher.runBlockingTest {
        with(remoteDataSource.getDeliveryDetailForId(123)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.customerName).isNotNull()
            Truth.assertThat(this.requiresSignature).isNotNull()
            Truth.assertThat(this.specialInstructions).isNotNull()
        }
        coVerify {
            deliveryService.getDeliveryDetailForId(any())
        }
        coVerify(exactly = 0) {
            deliveryService.getDeliveryList()
            trackingService.postTracking(any())
        }
    }

    @Test
    fun `postTracking from trackingService successfully`() = dispatcher.runBlockingTest {
        with(remoteDataSource.postTracking(TrackingRemote.mock())) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.status).isEqualTo("ok")
        }
        coVerify {
            trackingService.postTracking(any())
        }
        coVerify(exactly = 0) {
            deliveryService.getDeliveryList()
            deliveryService.getDeliveryDetailForId(any())
        }
    }
}