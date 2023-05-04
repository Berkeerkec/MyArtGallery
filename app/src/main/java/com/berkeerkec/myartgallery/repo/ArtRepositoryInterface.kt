package com.berkeerkec.myartgallery.repo

import androidx.lifecycle.LiveData
import com.berkeerkec.myartgallery.model.ImageResponse
import com.berkeerkec.myartgallery.roomdb.Art
import com.berkeerkec.myartgallery.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art : Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}