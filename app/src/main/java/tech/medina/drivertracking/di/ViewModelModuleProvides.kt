package tech.medina.drivertracking.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.medina.drivertracking.BuildConfig
import tech.medina.drivertracking.data.datasource.local.db.Database
import tech.medina.drivertracking.data.datasource.remote.api.DeliveryService
import tech.medina.drivertracking.data.datasource.remote.api.TrackingService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ViewModelModuleProvides {

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}