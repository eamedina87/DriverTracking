package tech.medina.drivertracking.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.medina.drivertracking.BuildConfig
import tech.medina.drivertracking.data.datasource.local.db.Database
import tech.medina.drivertracking.data.datasource.remote.api.DeliveryService
import tech.medina.drivertracking.data.datasource.remote.api.TrackingService
import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.data.repository.delivery.DeliveryRepositoryImpl
import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.data.repository.tracking.TrackingRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModuleProvides {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun providesRetrofitConverter(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideDeliveryService(
        converter: Converter.Factory
    ): DeliveryService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(converter)
            .build()
            .create(DeliveryService::class.java)
    }

    @Singleton
    @Provides
    fun provideTrackingService(
        converter: Converter.Factory
    ): TrackingService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(converter)
            .build()
            .create(TrackingService::class.java)
    }

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            BuildConfig.DB_NAME)
            .build()
    }

}