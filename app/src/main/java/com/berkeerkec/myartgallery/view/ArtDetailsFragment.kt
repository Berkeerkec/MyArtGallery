package com.berkeerkec.myartgallery.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.berkeerkec.myartgallery.R
import com.berkeerkec.myartgallery.databinding.FragmentArtDetailsBinding
import com.berkeerkec.myartgallery.util.Status
import com.berkeerkec.myartgallery.viewmodel.ArtViewModel
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    private val glide : RequestManager
): Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding : FragmentArtDetailsBinding? = null
    lateinit var viewModel : ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        subscribeToObserver()
        binding.selectedImage.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.button.setOnClickListener {
            viewModel.makeArt(
                binding.nameText.text.toString(),
                binding.artistNameText.text.toString(),
                binding.yearText.text.toString())
        }

    }

    private fun subscribeToObserver(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url->

            fragmentBinding?.let {
                glide.load(url).into(it.selectedImage)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    findNavController().popBackStack()
                    viewModel.resetInsertMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}