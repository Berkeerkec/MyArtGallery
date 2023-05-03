package com.berkeerkec.myartgallery.api

import com.berkeerkec.myartgallery.model.ImageResponse
import com.berkeerkec.myartgallery.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/api/")
    suspend fun ImageQuery(
        @Query("q") searchQuery : String,
        @Query("key") apikey : String = API_KEY
    ) : Response<ImageResponse>
}