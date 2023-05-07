package com.berkeerkec.myartgallery.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.berkeerkec.myartgallery.R
import com.berkeerkec.myartgallery.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val ImageAdapter : ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}