package com.berkeerkec.myartgallery.repo

import androidx.lifecycle.LiveData
import com.berkeerkec.myartgallery.api.RetrofitApi
import com.berkeerkec.myartgallery.model.ImageResponse
import com.berkeerkec.myartgallery.roomdb.Art
import com.berkeerkec.myartgallery.roomdb.ArtDao
import com.berkeerkec.myartgallery.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao : ArtDao,
    private val retrofitApi: RetrofitApi
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insert(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.delete(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.obserArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.ImageQuery(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }else{
                Resource.error("Error", null)
            }

        }catch (e : Exception){
            return Resource.error("No Data!", null)
        }
    }
}