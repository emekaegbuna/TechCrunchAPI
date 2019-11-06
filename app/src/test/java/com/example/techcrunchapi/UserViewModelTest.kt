package com.example.techcrunchapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.techcrunchapi.data.model.authorModel.AuthorModel
import com.example.techcrunchapi.data.repository.TechCrunchRepository
import com.example.techcrunchapi.ui.activity.MainActivity
import com.example.techcrunchapi.viewModel.UserViewModel
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
class UserViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var techCrunchRepository: TechCrunchRepository
    lateinit var userViewModel: UserViewModel
    var userID = 59254947

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        userViewModel = UserViewModel(techCrunchRepository, userID)
    }


    @Test
    fun fetchPost_sucessWithDistinct(){
        val author = AuthorModel("Lilly Drpper",
                "this is the description of the author",
                "http//:www.avatar.com/avatar1.jpg")

        val expectedAuthor = AuthorModel("Lilly Drpper",
                    "this is the description of the author",
                    "http//:www.avatar.com/avatar1.jpg")

        every { techCrunchRepository.fetchAuthorModel(userID) } returns (Single.just(author))

        userViewModel.fetchAuthorModel()

        Assert.assertEquals(expectedAuthor, userViewModel.authorModel.value)
        Assert.assertEquals(UserViewModel.LoadingState.SUCCESS, userViewModel.loadingState.value)
        Assert.assertEquals(null, userViewModel.errorMessage.value)
    }

    @Test
    fun fetchPost_networkError() {

        every {techCrunchRepository.fetchAuthorModel(userID)} returns (Single.error(
            UnknownHostException("Abc")
        ))

        userViewModel.fetchAuthorModel()

        Assert.assertEquals(null, userViewModel.authorModel.value)
        Assert.assertEquals(UserViewModel.LoadingState.ERROR, userViewModel.loadingState.value)
        Assert.assertEquals("No Network", userViewModel.errorMessage.value)
    }


    @Test
    fun fetchCake_otherError() {
        every{techCrunchRepository.fetchAuthorModel(userID)} returns Single.error(RuntimeException("Abc"))

        userViewModel.fetchAuthorModel()

        Assert.assertEquals(null, userViewModel.authorModel.value)
        Assert.assertEquals(UserViewModel.LoadingState.ERROR, userViewModel.loadingState.value)
        Assert.assertEquals("Abc", userViewModel.errorMessage.value)
    }

    @Test
    fun getActivityTest(){
        val activityClass = userViewModel.getActivity()
        Assert.assertTrue(activityClass == MainActivity::class.java)
    }


}