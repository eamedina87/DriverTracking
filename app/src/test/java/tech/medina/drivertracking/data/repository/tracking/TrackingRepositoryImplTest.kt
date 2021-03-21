package tech.medina.drivertracking.data.repository.tracking

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import tech.medina.drivertracking.base.BaseTest

import org.junit.Test
import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse
import tech.medina.drivertracking.data.utils.mock
import tech.medina.drivertracking.domain.model.Tracking
import tech.medina.drivertracking.domain.model.TrackingStatus

@ExperimentalCoroutinesApi
class TrackingRepositoryImplTest : BaseTest() {

    private val localDataSource = mockk<LocalDataSource> {
        coEvery { saveDelivery(any()) } returns true
        coEvery { updateDelivery(any()) } returns true
        coEvery { getDeliveryList() } returns listOf(DeliveryLocal.mock())
        coEvery { getDeliveryWithId(any()) } returns DeliveryLocal.mock()
        coEvery { saveTracking(any()) } returns true
        coEvery { updateTracking(any()) } returns true
        coEvery { deleteTracking(any()) } returns true
        coEvery { getAllTracking() } returns listOf(TrackingLocal.mock())
        coEvery { getAllTrackingWithStatusNot(any()) } returns listOf(TrackingLocal.mock())
    }

    private val remoteDataSource = mockk<RemoteDataSource> {
        coEvery { getDeliveryList() } returns DeliveriesRemote.mock()
        coEvery { getDeliveryDetailForId(any()) } returns DeliveriesRemote.mock(isFull = true).deliveries.first()
        coEvery { postTracking(any()) } returns TrackingResponse.mock()
    }

    private val trackingRepository = TrackingRepositoryImpl(localDataSource, remoteDataSource, mapper)

    @Test
    fun saveTrackingData() = dispatcher.runBlockingTest {
        with(trackingRepository.saveTrackingData(Tracking.mock())) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence{
            localDataSource.saveTracking(any())
        }
    }

    @Test
    fun getTrackingDataWithPredicate() = dispatcher.runBlockingTest {
        with(trackingRepository.getTrackingDataWithPredicate {
                it.status == TrackingStatus.DEFAULT
        }) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(TrackingStatus.DEFAULT)
        }
        coVerifySequence{
            localDataSource.getAllTracking()
        }
    }

    @Test
    fun getUnsentData() = dispatcher.runBlockingTest {
        with(trackingRepository.getUnsentData()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().status).isNotEqualTo(TrackingStatus.SENT)
        }
        coVerifySequence{
            localDataSource.getAllTrackingWithStatusNot(any())
        }
    }

    @Test
    fun postTrackingData() = dispatcher.runBlockingTest {
        with(trackingRepository.postTrackingData(listOf(TrackingLocal.mock()))) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence {
            remoteDataSource.postTracking(any())
        }
    }

}