package com.berkeerkec.myartgallery.di

import android.content.Context
import androidx.room.Room
import com.berkeerkec.myartgallery.R
import com.berkeerkec.myartgallery.api.RetrofitApi
import com.berkeerkec.myartgallery.repo.ArtRepository
import com.berkeerkec.myartgallery.repo.ArtRepositoryInterface
import com.berkeerkec.myartgallery.roomdb.ArtDao
import com.berkeerkec.myartgallery.roomdb.ArtDatabase
import com.berkeerkec.myartgallery.util.Util.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api : RetrofitApi) = ArtRepository(dao,api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context : Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )
}