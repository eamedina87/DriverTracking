package tech.medina.drivertracking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.local.LocalDataSourceImpl
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSourceImpl
import tech.medina.drivertracking.data.mapper.Mapper
import tech.medina.drivertracking.data.mapper.MapperImpl
import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.data.repository.delivery.DeliveryRepositoryImpl
import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.data.repository.tracking.TrackingRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModuleBinds {

    @Binds
    abstract fun bindDeliveryRepository(
        repositoryImpl: DeliveryRepositoryImpl
    ): DeliveryRepository

    @Binds
    abstract fun bindTrackingRepository(
        repositoryImpl: TrackingRepositoryImpl
    ): TrackingRepository

    @Binds
    abstract fun bindLocalDataSource(
        dataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(
        dataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindMapper(
        mapperImpl: MapperImpl
    ): Mapper

}