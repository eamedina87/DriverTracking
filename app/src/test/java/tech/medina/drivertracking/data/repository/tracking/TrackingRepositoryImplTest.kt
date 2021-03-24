package tech.medina.drivertracking.data.repository.tracking

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import tech.medina.drivertracking.base.BaseTest
import org.junit.Test
import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.utils.TestingUtils
import tech.medina.drivertracking.domain.model.TrackingStatus

@ExperimentalCoroutinesApi
class TrackingRepositoryImplTest : BaseTest() {

    private val localDataSource = mockk<LocalDataSource> {
        coEvery { saveDelivery(any()) } returns true
        coEvery { updateDelivery(any()) } returns true
        coEvery { getDeliveryList() } returns listOf(TestingUtils.mockDeliveryLocal())
        coEvery { getDeliveryWithId(any()) } returns TestingUtils.mockDeliveryLocal()
        coEvery { saveTracking(any()) } returns true
        coEvery { updateTracking(any()) } returns true
        coEvery { deleteTracking(any()) } returns true
        coEvery { getAllTracking() } returns listOf(TestingUtils.mockTrackingLocal())
        coEvery { getAllTrackingWithStatus(any()) } returns listOf(TestingUtils.mockTrackingLocal())
        every { getDriverId() } returns 123
    }

    private val remoteDataSource = mockk<RemoteDataSource> {
        coEvery { getDeliveryList() } returns TestingUtils.mockDeliveriesResponse()
        coEvery { getDeliveryDetailForId(any()) } returns TestingUtils.mockDeliveriesResponse(isFull = true).deliveries.first()
        coEvery { postTracking(any()) } returns TestingUtils.mockTrackingResponse()
    }

    private val trackingRepository = TrackingRepositoryImpl(localDataSource, remoteDataSource, mapper)

    @Test
    fun saveTrackingData() = dispatcher.runBlockingTest {
        with(trackingRepository.saveTrackingData(TestingUtils.mockTracking())) {
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
        with(trackingRepository.getTrackingDataWithStatus(TrackingStatus.DEFAULT.ordinal)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().status).isNotEqualTo(TrackingStatus.SENT)
        }
        coVerifySequence{
            localDataSource.getAllTrackingWithStatus(any())
        }
    }

    @Test
    fun postTrackingData() = dispatcher.runBlockingTest {
        with(trackingRepository.postTrackingData(listOf(TestingUtils.mockTracking()))) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence {
            remoteDataSource.postTracking(any())
        }
    }

}