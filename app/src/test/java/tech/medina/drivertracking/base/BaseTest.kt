package tech.medina.drivertracking.base

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse
import tech.medina.drivertracking.data.mapper.MapperImpl
import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.data.utils.mock
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
open class BaseTest : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    protected lateinit var dispatcher: TestCoroutineDispatcher

    protected val mapper = MapperImpl()

    @Before
    open fun before() {
        MockKAnnotations.init(this)
        setupCoroutines()
    }

    @After
    fun after() {
        clearCoroutines()
    }

    private fun setupCoroutines() {
        dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    private fun clearCoroutines() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        dispatcher.cleanupTestCoroutines()
    }

}