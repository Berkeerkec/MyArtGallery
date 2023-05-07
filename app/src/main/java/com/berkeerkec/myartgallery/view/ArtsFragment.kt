package com.berkeerkec.myartgallery.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkeerkec.myartgallery.R
import com.berkeerkec.myartgallery.adapter.ArtRecyclerAdapter
import com.berkeerkec.myartgallery.databinding.FragmentArtsBinding
import com.berkeerkec.myartgallery.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtsFragment @Inject constructor(
    private val artAdapter : ArtRecyclerAdapter
): Fragment(R.layout.fragment_arts) {

    private var fragmentBinding : FragmentArtsBinding? = null
    lateinit var viewModel : ArtViewModel

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        subscribeToObserver()

        binding.recyclerViewArt.adapter = artAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerViewArt)



        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObserver(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artAdapter.arts = it
        })
    }
}