package com.berkeerkec.myartgallery.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.berkeerkec.myartgallery.R
import com.berkeerkec.myartgallery.adapter.ImageRecyclerAdapter
import com.berkeerkec.myartgallery.databinding.FragmentImageApiBinding
import com.berkeerkec.myartgallery.util.Status
import com.berkeerkec.myartgallery.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val ImageAdapter : ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {

    private var fragmentBinding : FragmentImageApiBinding? = null
    lateinit var viewModel : ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        var job : Job? = null
        binding.searchText.addTextChangedListener {
            job?.cancel()

            job = lifecycleScope.launch {
                delay(1000)

                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }



        subcribeToObserver()
        binding.imageRecyclerView.adapter = ImageAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        ImageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun subcribeToObserver(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map {imageResult ->
                        imageResult.previewURL
                    }
                    ImageAdapter.images = urls ?: listOf()
                    fragmentBinding?.imageProgressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                    fragmentBinding?.imageProgressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.imageProgressBar?.visibility = View.VISIBLE

                }
            }
        })
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}