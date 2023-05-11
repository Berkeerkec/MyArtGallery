package com.berkeerkec.myartgallery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.berkeerkec.myartgallery.model.ImageResponse
import com.berkeerkec.myartgallery.roomdb.Art
import com.berkeerkec.myartgallery.util.Resource

class FakeArtRepository : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refleshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refleshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refleshData(){
        artsLiveData.postValue(arts)
    }
}