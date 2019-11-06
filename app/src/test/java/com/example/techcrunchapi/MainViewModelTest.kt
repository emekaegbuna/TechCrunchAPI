package com.example.techcrunchapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.techcrunchapi.data.model.postModel.Content
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import com.example.techcrunchapi.data.model.postModel.Title
import com.example.techcrunchapi.data.repository.TechCrunchRepository
import com.example.techcrunchapi.ui.activity.MainActivity
import com.example.techcrunchapi.viewModel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.net.UnknownHostException


@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var techCrunchRepository: TechCrunchRepository
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(techCrunchRepository)
    }


    @Test
    fun fetchPost_sucessWithDistinct(){
        val posts = listOf(
            TechCrunchModel(
                345796,
                Content(false,"this is the description"),
                "3,6,19",
                "3,4,16",
                "http//:www.image.com/figure.jpg",
                Title("title of the post")
        ))

        val expectedPost = listOf(
            TechCrunchModel(
                345796,
                Content(false,"this is the description"),
                "3,6,19",
                "3,4,16",
                "http//:www.image.com/figure.jpg",
                Title("title of the post")
            ))

        every { techCrunchRepository.fetchTechCrunchModel() } returns (Single.just(posts))

        mainViewModel.fetchTechCrunchModel()

        Assert.assertEquals(expectedPost, mainViewModel.techCrunchModel.value)
        Assert.assertEquals(MainViewModel.LoadingState.SUCCESS, mainViewModel.loadingState.value)
        Assert.assertEquals(null, mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchPost_networkError() {

        every {techCrunchRepository.fetchTechCrunchModel()} returns (Single.error(UnknownHostException("Abc")))

        mainViewModel.fetchTechCrunchModel()

        Assert.assertEquals(null, mainViewModel.techCrunchModel.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("No Network", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchCake_otherError() {
        every{techCrunchRepository.fetchTechCrunchModel()} returns Single.error(RuntimeException("Abc"))

        mainViewModel.fetchTechCrunchModel()

        Assert.assertEquals(null, mainViewModel.techCrunchModel.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("Abc", mainViewModel.errorMessage.value)
    }

    @Test
    fun getActivityTest(){
        val activityClass = mainViewModel.getActivity()
        Assert.assertTrue(activityClass == MainActivity::class.java)
    }


}