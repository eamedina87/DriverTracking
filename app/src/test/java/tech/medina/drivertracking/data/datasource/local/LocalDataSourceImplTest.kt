package tech.medina.drivertracking.data.datasource.local

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import tech.medina.drivertracking.base.BaseTest
import tech.medina.drivertracking.data.datasource.local.db.Database
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal
import tech.medina.drivertracking.data.utils.mock

@ExperimentalCoroutinesApi
class LocalDataSourceImplTest : BaseTest() {

    private val database = mockk<Database>() {
        coEvery { deliveryDao().insert(any()) } returns listOf(1, 2, 3)
        coEvery { deliveryDao().getDeliveryWithId(any())} returns DeliveryLocal.mock()
        coEvery { deliveryDao().getAll() } returns listOf(DeliveryLocal.mock())
        coEvery { deliveryDao().update(any()) } returns 1
        coEvery { trackingDao().insert(any()) } returns listOf(1, 2, 3)
        coEvery { trackingDao().update(any()) } returns 1
        coEvery { trackingDao().delete(any()) } returns 1
        coEvery { trackingDao().getAll() } returns listOf(TrackingLocal.mock())
        coEvery { trackingDao().getAllWithStatus(any()) } returns listOf(TrackingLocal.mock())
    }

    private val localDataSource = LocalDataSourceImpl(database)

    @Test
    fun saveDeliveryList() = dispatcher.runBlockingTest {
        with(localDataSource.saveDelivery(DeliveryLocal.mock())) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence { database.deliveryDao().insert(any()) }
    }

    @Test
    fun updateDelivery() = dispatcher.runBlockingTest {
        with(localDataSource.updateDelivery(DeliveryLocal.mock())) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence { database.deliveryDao().update(any()) }
    }

    @Test
    fun getDeliveryList() = dispatcher.runBlockingTest {
        with(localDataSource.getDeliveryList()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().customerName).isNotNull()
        }
        coVerifySequence { database.deliveryDao().getAll() }
    }

    @Test
    fun getDeliveryWithId() = dispatcher.runBlockingTest {
        with(localDataSource.getDeliveryWithId(15)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.customerName).isNotNull()
        }
        coVerifySequence { database.deliveryDao().getDeliveryWithId(any()) }
    }

    @Test
    fun saveTracking() = dispatcher.runBlockingTest {
        with(localDataSource.saveTracking(TrackingLocal.mock())) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence { database.trackingDao().insert(any()) }
    }

    @Test
    fun updateTracking() = dispatcher.runBlockingTest {
        with(localDataSource.updateTracking(TrackingLocal.mock())) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence { database.trackingDao().update(any()) }
    }

    @Test
    fun deleteTracking() = dispatcher.runBlockingTest {
        with(localDataSource.deleteTracking(TrackingLocal.mock())) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isTrue()
        }
        coVerifySequence { database.trackingDao().delete(any()) }
    }

    @Test
    fun getAllTracking() = dispatcher.runBlockingTest {
        with(localDataSource.getAllTracking()) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
        }
        coVerifySequence { database.trackingDao().getAll() }
    }

    @Test
    fun getAllTrackingWithStatus() = dispatcher.runBlockingTest {
        with(localDataSource.getAllTrackingWithStatus(0)) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this).isNotEmpty()
            Truth.assertThat(this.first().status).isEqualTo(0)
        }
        coVerifySequence { database.trackingDao().getAllWithStatus(any()) }
    }

}