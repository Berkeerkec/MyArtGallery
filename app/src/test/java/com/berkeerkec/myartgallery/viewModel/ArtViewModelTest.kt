package com.berkeerkec.myartgallery.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.berkeerkec.myartgallery.MainCoroutineRule
import com.berkeerkec.myartgallery.getOrAwaitValue
import com.berkeerkec.myartgallery.repo.FakeArtRepository
import com.berkeerkec.myartgallery.util.Status
import com.berkeerkec.myartgallery.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    private lateinit var viewModel : ArtViewModel

    @get:Rule
    var instTaskExecuterRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("Mona","Vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("","Vinci", "1700")
        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("Mona","", "1700")
        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}