package tech.medina.drivertracking.data.repository.delivery

import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import tech.medina.drivertracking.base.BaseTest
import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse
import tech.medina.drivertracking.data.mapper.Mapper
import tech.medina.drivertracking.data.utils.mock
import tech.medina.drivertracking.domain.model.Delivery

@ExperimentalCoroutinesApi
class DeliveryRepositoryTest : BaseTest() {

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
        coEvery { getDeliveryList() } returns DeliveryRemote.mock()
        coEvery { getDeliveryDetailForId(any()) } returns DeliveryRemote.mock(isFull = true).delivery.first()
        coEvery { postTracking(any()) } returns TrackingResponse.mock()
    }

    private val mapper = Mapper()

    private val deliveryRepository = DeliveryRepositoryImpl(localDataSource, remoteDataSource, mapper)

    @Test
    fun getDeliveryListUpdating() {
        with(deliveryRepository.getDeliveryList(forceUpdate = true)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().customerName).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(Delivery.Status.DEFAULT)
        }
        coVerifySequence {
            remoteDataSource.getDeliveryList()
            localDataSource.saveDelivery(any())
            localDataSource.getDeliveryList()
        }
    }

    @Test
    fun getDeliveryListFromCacheEmpty() {
        coEvery { localDataSource.getDeliveryList() } returns emptyList()
        with(deliveryRepository.getDeliveryList()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().customerName).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(Delivery.Status.DEFAULT)
        }
        coVerifySequence {
            localDataSource.getDeliveryList()
            remoteDataSource.getDeliveryList()
            localDataSource.saveDelivery(any())
            localDataSource.getDeliveryList()
        }
    }

    @Test
    fun getDeliveryListFromCachePopulated() {
        with(deliveryRepository.getDeliveryList()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().customerName).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(Delivery.Status.DEFAULT)
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
    fun getDeliveryDetailForIdWithNoFullData() {
        coEvery { localDataSource.getDeliveryWithId(any()) } returns DeliveryLocal.mock()
        with(deliveryRepository.getDeliveryDetailForId(123)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.customerName).isNotEmpty()
            Truth.assertThat(this.status).isEqualTo(Delivery.Status.DEFAULT)
        }
        coVerifySequence {
            localDataSource.getDeliveryWithId(any())
            remoteDataSource.getDeliveryDetailForId(any())
            localDataSource.saveDelivery(any())
            localDataSource.getDeliveryWithId(any())
        }
    }

    @Test
    fun getDeliveryDetailForIdWithFullData() {
        coEvery { localDataSource.getDeliveryWithId(any()) } returns DeliveryLocal.mock(isFull = true)
        with(deliveryRepository.getDeliveryDetailForId(123)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.customerName).isNotEmpty()
            Truth.assertThat(this.status).isEqualTo(Delivery.Status.DEFAULT)
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
    fun activateDelivery() {
        with(deliveryRepository.activateDelivery(Delivery.mock())) {
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence {
            localDataSource.updateDelivery(any())
        }
    }

    @Test
    fun deactivateDelivery() {
        with(deliveryRepository.deactivateDelivery(Delivery.mock())) {
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence {
            localDataSource.updateDelivery(any())
        }
    }

    @Test
    fun deactivateAllDeliveries() {
        with(deliveryRepository.deactivateAllDeliveries()) {
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence {
            localDataSource.updateDelivery(any())
        }
    }
}