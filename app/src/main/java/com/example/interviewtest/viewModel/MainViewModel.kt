package com.example.interviewtest.viewModel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.repository.MainRepository
import com.example.interviewtest.utils.Event
import com.example.interviewtest.utils.extensions.Resource
import com.example.interviewtest.utils.extensions.Status
import com.example.interviewtest.utils.Constants.postAddedAlert
import com.example.interviewtest.utils.Constants.postAlreadyExistAlert
import com.example.interviewtest.utils.Coroutines
import com.example.interviewtest.utils.extensions.setupFavoriteEntity
import kotlinx.coroutines.Job

class MainViewModel @ViewModelInject constructor(
    private val mainRepo: MainRepository
) : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private lateinit var getPostsJob: Job
    private lateinit var getCommentsJob: Job
    private lateinit var addPostDataToDBJob: Job
    private lateinit var getAllPostDataFromDBJob: Job
    private lateinit var getPostDataFroPostEntityJob: Job
    private lateinit var addPostDataToFavoriteEntityJob: Job
    private lateinit var checkThePostAlreadyExistJob: Job
    private lateinit var getAllFavoriteDataFromFavoriteEntityJob: Job
    private val postsMutableData = MutableLiveData<Resource<List<PostEntity>>>()
    private val _message = MutableLiveData<Event<String>>()
    private val _shouldScheduleWork: MutableLiveData<Event<Boolean>> = MutableLiveData()
    private val _allPostFromDB = MutableLiveData<List<PostEntity>>()
    private val _allFavoritePostFromDB = MutableLiveData<List<PostEntity>>()
    val shouldScheduleWork: MutableLiveData<Event<Boolean>> get() = _shouldScheduleWork
    val allFavoritePostFromDB: LiveData<List<PostEntity>> get() = _allFavoritePostFromDB
    val postResponseLiveData: LiveData<Resource<List<PostEntity>>> get() = postsMutableData
    val message: LiveData<Event<String>> get() = _message
    val allPostFromDB get() = _allPostFromDB
    val networkStatus get() = mainRepo.getNetworkConnectionStatus()


    override fun onCleared() {
        super.onCleared()
        if (::getPostsJob.isInitialized) getPostsJob.cancel()
        if (::getCommentsJob.isInitialized) getCommentsJob.cancel()
        if (::addPostDataToDBJob.isInitialized) addPostDataToDBJob.cancel()
        if (::getAllPostDataFromDBJob.isInitialized) getAllPostDataFromDBJob.cancel()
        if (::getPostDataFroPostEntityJob.isInitialized) getPostDataFroPostEntityJob.cancel()
        if (::addPostDataToFavoriteEntityJob.isInitialized) addPostDataToFavoriteEntityJob.cancel()
        if (::checkThePostAlreadyExistJob.isInitialized) checkThePostAlreadyExistJob.cancel()
        if (::getAllFavoriteDataFromFavoriteEntityJob.isInitialized) getAllFavoriteDataFromFavoriteEntityJob.cancel()

    }


    fun getPostsFromAPI() {
        getPostsJob =
            Coroutines.ioThenMain({
                mainRepo.getPosts()
            }, {
                postsMutableData.value = it
                if (it?.data != null && it.status == Status.SUCCESS) {
                    deleteAllPostWhichIsNotFavorite(it.data)
                } else {
                    setMessage(it?.message)
                }
            }, {
                setMessage(it)
            })

    }

    private fun deleteAllPostWhichIsNotFavorite(data: List<PostEntity>) {
        Coroutines.ioThenMain({
            mainRepo.deleteAllPostWhichIsNotFavorite()
        }, {
            addAllPostToDB(data)
        }, {
            setMessage(it)
        })
    }

    fun getCommentsFromAPI(post_id: Int): LiveData<Resource<List<CommentsResponseItem>>> {
        val commentsMutableData = MutableLiveData<Resource<List<CommentsResponseItem>>>()
        getCommentsJob =
            Coroutines.ioThenMain({
                mainRepo.getComments(post_id)
            }, {
                commentsMutableData.value = it
                if (it?.status != Status.SUCCESS) {
                    setMessage(it?.message)
                }
            }, {
                setMessage(it)
            })
        return commentsMutableData
    }


    fun getPostsFromDB() {
        getAllPostDataFromDBJob = Coroutines.ioThenMain(
            {
                mainRepo.getAllPostDataFromPostEntity()
            }, {
                _allPostFromDB.value = it
            }, {
                setMessage(it)
            })
    }


    private fun addAllPostToDB(postEntityList: List<PostEntity>) {
        addPostDataToDBJob = Coroutines.ioThenMain({
            mainRepo.addALLPostDataToPostEntity(postEntityList)
        }, {
            Log.d(TAG, "addPostData_completed")
        }, {
            setMessage(it)
        })
    }

    @VisibleForTesting
    internal fun addPostDataToFavoriteEntityAndInformMiddleWare(postEntity: PostEntity) {
        if (!networkStatus.value!!) {
            //  scheduleWork()
            _shouldScheduleWork.value = Event(true)
        }
        addPostDataToFavoriteEntityJob = Coroutines.ioThenMain({
            mainRepo.addALLPostDataToPostEntity(postEntity.setupFavoriteEntity())
        }, {
            setMessage(postAddedAlert)
        }, {
            setMessage(it)
        })
    }

    fun checkTheFavoritePostAlreadyExist(id: Int) {
        checkThePostAlreadyExistJob = Coroutines.ioThenMain(
            {
                mainRepo.checkTheFavoritePostAlreadyExist(id)
            }, {
                if (it == null) {
                    getPostDataFromPostEntity(id)
                } else {
                    setMessage(postAlreadyExistAlert)
                }
            }, {
                setMessage(it)
            }
        )
    }

    private fun getPostDataFromPostEntity(id: Int) {
        getPostDataFroPostEntityJob = Coroutines.ioThenMain(
            {
                mainRepo.getPostDataFromPostEntity(id)
            }, {
                if (it != null) {
                    addPostDataToFavoriteEntityAndInformMiddleWare(it)
                }
            }, {
                setMessage(it)
            }
        )
    }

    fun getAllFavoriteDataFromFavoriteEntity() {
        getAllFavoriteDataFromFavoriteEntityJob = Coroutines.ioThenMain({
            mainRepo.getAllFavoriteDataFromFavoriteEntity()
        }, {
            _allFavoritePostFromDB.value = it
        }, {
            setMessage(it)
        })
    }

    private fun setMessage(e: String?) {
        _message.value = Event(e!!)
    }

}