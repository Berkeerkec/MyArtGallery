package com.berkeerkec.myartgallery.di

import android.content.Context
import androidx.room.Room
import com.berkeerkec.myartgallery.api.RetrofitApi
import com.berkeerkec.myartgallery.roomdb.ArtDatabase
import com.berkeerkec.myartgallery.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectArtDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        context,ArtDatabase::class.java,"ArtDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database : ArtDatabase)  = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofit() : RetrofitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }
}