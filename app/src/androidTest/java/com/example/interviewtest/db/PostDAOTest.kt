package com.example.interviewtest.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class PostDAOTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var postDAO: PostDAO

    @Inject
    @Named("Test_PostDataBase")
    lateinit var postDataBase: PostDataBase

    @Before
    fun setUp() {
        hiltRule.inject()
        postDAO = postDataBase.getPostDao()
    }

    @After
    fun tearDown() {
        postDataBase.close()
    }

    @Test
    fun addALLPostDataToPostEntity_getAllPostDataFromPostEntity() = runBlockingTest {
        val posts = listOf(
            PostEntity(1, 1, "body1", "title1"),
            PostEntity(2, 2, "body2", "title2"),
            PostEntity(3, 3, "body3", "title3"),
            PostEntity(4, 4, "body4", "title4")
        )
        //adding data into db
        postDAO.addALLPostDataToPostEntity(posts)
        //getting data from db
        val getAllPosts = postDAO.getAllPostDataFromPostEntity()
        Truth.assertThat(getAllPosts).isEqualTo(posts)
    }


    @Test
    fun getOnePostDataFromPostEntity() = runBlockingTest {
        val posts = listOf(
            PostEntity(1, 1, "body1", "title1"),
            PostEntity(2, 2, "body2", "title2"),
            PostEntity(3, 3, "body3", "title3"),
            PostEntity(4, 4, "body4", "title4")
        )
        postDAO.addALLPostDataToPostEntity(posts)
        val getOnePostEntity = postDAO.getPostDataFromPostEntity(1)
        Truth.assertThat(posts[0]).isEqualTo(getOnePostEntity)
    }

    @Test
    fun addPostDataToFavoriteEntity() = runBlockingTest {
        val favorites = listOf(
            FavoriteEntity(1, 1, "body1", "title1"),
            FavoriteEntity(2, 2, "body2", "title2"),
            FavoriteEntity(3, 3, "body3", "title3"),
            FavoriteEntity(4, 4, "body4", "title4")
        )
        //adding data into db
        for (i in favorites.indices)
            postDAO.addPostDataToFavoriteEntity(favoriteEntity = favorites[i])
        //getting data from db
        val getAllFavoritePosts = postDAO.getAllFavoriteDataFromFavoriteEntity()
        Truth.assertThat(getAllFavoritePosts).isEqualTo(favorites)
    }

    @Test
    fun checkThePostAlreadyExist() = runBlockingTest {
        val favorites = listOf(
            FavoriteEntity(1, 1, "body1", "title1"),
            FavoriteEntity(2, 2, "body2", "title2"),
            FavoriteEntity(3, 3, "body3", "title3"),
            FavoriteEntity(4, 4, "body4", "title4")
        )
        //adding data into db
        for (i in favorites.indices)
            postDAO.addPostDataToFavoriteEntity(favoriteEntity = favorites[i])
        //checking the favorite Post Exist
        val post = postDAO.checkThePostAlreadyExist(4)
        Truth.assertThat(post).isNotNull()
    }
}