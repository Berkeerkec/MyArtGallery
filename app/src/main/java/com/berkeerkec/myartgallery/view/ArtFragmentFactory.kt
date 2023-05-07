package com.berkeerkec.myartgallery.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.berkeerkec.myartgallery.adapter.ArtRecyclerAdapter
import com.berkeerkec.myartgallery.adapter.ImageRecyclerAdapter
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
private val glide : RequestManager,
private val artAdapter : ArtRecyclerAdapter,
private val imageAdapter : ImageRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){

            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ArtsFragment::class.java.name -> ArtsFragment(artAdapter)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }


}