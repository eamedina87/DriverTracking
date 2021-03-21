package tech.medina.drivertracking.data.repository.delivery

import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import tech.medina.drivertracking.base.BaseTest
import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesResponse
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse
import tech.medina.drivertracking.data.utils.mock
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.DeliveryStatus

@ExperimentalCoroutinesApi
class DeliveryRepositoryImplTest : BaseTest() {

    private val localDataSource = mockk<LocalDataSource> {
        coEvery { saveDelivery(any()) } returns true
        coEvery { updateDelivery(any()) } returns true
        coEvery { getDeliveryList() } returns listOf(DeliveryLocal.mock())
        coEvery { getDeliveryWithId(any()) } returns DeliveryLocal.mock()
        coEvery { saveTracking(any()) } returns true
        coEvery { updateTracking(any()) } returns true
        coEvery { deleteTracking(any()) } returns true
    }

    private val remoteDataSource = mockk<RemoteDataSource> {
        coEvery { getDeliveryList() } returns DeliveriesResponse.mock()
        coEvery { getDeliveryDetailForId(any()) } returns DeliveriesResponse.mock(isFull = true).deliveries.first()
        coEvery { postTracking(any()) } returns TrackingResponse.mock()
    }

    private val deliveryRepository = DeliveryRepositoryImpl(localDataSource, remoteDataSource, mapper)

    @Test
    fun `getDeliveryList forcing update`() = dispatcher.runBlockingTest {
        with(deliveryRepository.getDeliveryList(forceUpdate = true)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().customerName).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(DeliveryStatus.DEFAULT)
        }
        coVerifySequence {
            localDataSource.getDeliveryList()
            remoteDataSource.getDeliveryList()
            localDataSource.saveDelivery(any())
            localDataSource.getDeliveryList()
        }
    }

    @Test
    fun `getDeliveryList is empty and must update`() = dispatcher.runBlockingTest {
        coEvery { localDataSource.getDeliveryList() } returnsMany listOf(emptyList(),listOf(DeliveryLocal.mock()))
        with(deliveryRepository.getDeliveryList()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.first().customerName).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(DeliveryStatus.DEFAULT)
        }
        coVerifySequence {
            localDataSource.getDeliveryList()
            remoteDataSource.getDeliveryList()
            localDataSource.saveDelivery(any())
            localDataSource.getDeliveryList()
        }
    }

    @Test
    fun `getDeliveryList from local only`() = dispatcher.runBlockingTest {
        with(deliveryRepository.getDeliveryList()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().customerName).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(DeliveryStatus.DEFAULT)
        }
        coVerifySequence {
            localDataSource.getDeliveryList()
        }
        coVerify(exactly = 0) {
            remoteDataSource.getDeliveryList()
            localDataSource.saveDelivery(any())
        }
    }

    @Test
    fun `getDeliveryDetail from local is not complete and must update`() = dispatcher.runBlockingTest {
        coEvery { localDataSource.getDeliveryWithId(any()) } returnsMany listOf(DeliveryLocal.mock(), DeliveryLocal.mock(isFull = true))
        with(deliveryRepository.getDeliveryDetailForId(123)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.customerName).isNotEmpty()
            Truth.assertThat(this.status).isEqualTo(DeliveryStatus.DEFAULT)
        }
        coVerifySequence {
            localDataSource.getDeliveryWithId(any())
            remoteDataSource.getDeliveryDetailForId(any())
            localDataSource.updateDelivery(any())
        }
    }

    @Test
    fun `getDeliveryDetail from local with full data`() = dispatcher.runBlockingTest {
        coEvery { localDataSource.getDeliveryWithId(any()) } returns DeliveryLocal.mock(isFull = true)
        with(deliveryRepository.getDeliveryDetailForId(123)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.customerName).isNotEmpty()
            Truth.assertThat(this.status).isEqualTo(DeliveryStatus.DEFAULT)
        }
        coVerifySequence {
            localDataSource.getDeliveryWithId(any())
        }
        coVerify(exactly = 0) {
            remoteDataSource.getDeliveryDetailForId(any())
            localDataSource.saveDelivery(any())
        }
    }

    @Test
    fun `updateDelivery successfully`() = dispatcher.runBlockingTest {
        with(deliveryRepository.updateDelivery(Delivery.mock())) {
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence {
            localDataSource.updateDelivery(any())
        }
    }

}