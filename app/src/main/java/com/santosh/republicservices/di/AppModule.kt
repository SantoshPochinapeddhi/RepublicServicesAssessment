package com.santosh.republicservices.di

import android.content.Context
import androidx.room.Room
import com.santosh.republicservices.BuildConfig
import com.santosh.republicservices.model.DriversAndRoutesRepository
import com.santosh.republicservices.model.data.db.DriversAndRoutesDB
import com.santosh.republicservices.model.data.network.ApiService
import com.santosh.republicservices.model.datasource.LocalDataSource
import com.santosh.republicservices.model.datasource.RemoteDataSource
import com.santosh.republicservices.model.repository.DriversAndRoutesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room
            .databaseBuilder(context, DriversAndRoutesDB::class.java, "DriversAndRoutes")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesLocalDataSource(db: DriversAndRoutesDB): LocalDataSource {
        return LocalDataSource(db)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractDeps {
    @Binds
    abstract fun bindsDriversAndRoutesRepository(impl: DriversAndRoutesRepositoryImpl): DriversAndRoutesRepository
}