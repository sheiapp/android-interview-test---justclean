package com.example.interviewtest.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.interviewtest.MainCoroutineRule
import com.example.interviewtest.db.FavoriteEntity
import com.example.interviewtest.getOrAwaitValueTest
import com.example.interviewtest.repository.FakeDBRepo
import com.example.interviewtest.repository.FakeMainRepoTest
import com.example.interviewtest.utils.extensions.Status
import com.example.interviewtest.utils.Constants.postAddedAlert
import com.example.interviewtest.utils.Constants.postAlreadyExistAlert
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var fakeMainRepoTest: FakeMainRepoTest
    private lateinit var fakeDBRepo: FakeDBRepo


    @Before
    fun setUp() {
        fakeMainRepoTest = FakeMainRepoTest()
        fakeDBRepo = FakeDBRepo()
        mainViewModel = MainViewModel(fakeMainRepoTest, fakeDBRepo)
    }

    @Test
    fun `check post data getting from api when error occurs due to network or server related issue`() {
        fakeMainRepoTest.setShouldReturnNetworkError(true)
        mainViewModel.getPostsFromAPI()
        val data = mainViewModel.postResponseLiveData.getOrAwaitValueTest()
        assertThat(data.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `check comments data getting from api when error occurs due to network or server related issue`() {
        fakeMainRepoTest.setShouldReturnNetworkError(true)
        val comment = mainViewModel.getCommentsFromAPI(2).getOrAwaitValueTest()
        assertThat(comment.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `check the post data getting from api and update the data to db`() {
        mainViewModel.getPostsFromAPI()
        val post = mainViewModel.postResponseLiveData.getOrAwaitValueTest()
        mainViewModel.getPostsFromDB()
        val postFromDB = mainViewModel.allPostFromDB.getOrAwaitValueTest()
        print(post)
        print(postFromDB)
        assertThat(post.data).isNotEmpty()
        assertThat(postFromDB).isNotEmpty()
    }

    @Test
    fun `check the comment against post is getting from api`() {
        val comment = mainViewModel.getCommentsFromAPI(2).getOrAwaitValueTest()
        assertThat(comment.data).isNotEmpty()
    }

    @Test
    fun `check if the post already in favorite db then should getting alert message`() {
        mainViewModel.addPostDataToFavoriteEntityAndInformMiddleWare(
            FavoriteEntity(
                1,
                1,
                "body1",
                "title1"
            )
        )
        mainViewModel.addPostDataToFavoriteEntityAndInformMiddleWare(
            FavoriteEntity(
                2,
                2,
                "body2",
                "title2"
            )
        )
        mainViewModel.addPostDataToFavoriteEntityAndInformMiddleWare(
            FavoriteEntity(
                3,
                3,
                "body3",
                "title3"
            )
        )
        mainViewModel.addPostDataToFavoriteEntityAndInformMiddleWare(
            FavoriteEntity(
                4,
                4,
                "body4",
                "title4"
            )
        )
        /****/
        mainViewModel.checkTheFavoritePostAlreadyExist(2)
        val message = mainViewModel.message.getOrAwaitValueTest().getContentIfNotHandled()
        print(message)
        assertThat(message).isEqualTo(postAlreadyExistAlert)
    }

    @Test
    fun `check if the post not already in favorite db then should allow to add`() {
        mainViewModel.addPostDataToFavoriteEntityAndInformMiddleWare(
            FavoriteEntity(
                1,
                1,
                "body1",
                "title1"
            )
        )
        mainViewModel.addPostDataToFavoriteEntityAndInformMiddleWare(
            FavoriteEntity(
                3,
                3,
                "body3",
                "title3"
            )
        )
        mainViewModel.addPostDataToFavoriteEntityAndInformMiddleWare(
            FavoriteEntity(
                4,
                4,
                "body4",
                "title4"
            )
        )
        mainViewModel.getPostsFromAPI()
        mainViewModel.checkTheFavoritePostAlreadyExist(2)
        val message = mainViewModel.message.getOrAwaitValueTest()
        assertThat(message.peekContent()).isEqualTo(postAddedAlert)
        print(message.peekContent())
    }


}