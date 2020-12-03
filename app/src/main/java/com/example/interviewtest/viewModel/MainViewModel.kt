package com.example.interviewtest.viewModel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.example.interviewtest.db.FavoriteEntity
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.repository.DBRepository
import com.example.interviewtest.repository.MainRepository
import com.example.interviewtest.utils.Event
import com.example.interviewtest.utils.objects.Constants.postAddedAlert
import com.example.interviewtest.utils.objects.Constants.postAlreadyExistAlert
import com.example.interviewtest.utils.objects.Coroutines
import kotlinx.coroutines.Job

class MainViewModel @ViewModelInject constructor(
    private val mainRepo: MainRepository,
    private val dbRepo: DBRepository
//    , private val mWorkManager: WorkManager
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
    private val postsMutableData = MutableLiveData<List<PostEntity>>()
    private val _message = MutableLiveData<Event<String>>()
    private val _allPostFromDB = MutableLiveData<List<PostEntity>>()
    private val _allFavoritePostFromDB = MutableLiveData<List<FavoriteEntity>>()
    val allFavoritePostFromDB:LiveData<List<FavoriteEntity>> get() = _allFavoritePostFromDB
    val postResponseLiveData:LiveData<List<PostEntity>> get() = postsMutableData
    val message :LiveData<Event<String>> get() = _message
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

//    private fun scheduleWork() {
//        val workConstraints =
//            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
//        val workRequest =
//            OneTimeWorkRequestBuilder<MainWorker>().setConstraints(workConstraints).build()
//        //val workStatus = mWorkManager.getWorkInfoByIdLiveData(workRequest.id)
//        mWorkManager.enqueue(workRequest)
//    }


    fun getPostsFromAPI() {
        getPostsJob =
            Coroutines.ioThenMain({
                mainRepo.getPosts()
            }, {
                postsMutableData.value = it
                addAllPostToDB(it!!)
            }, {
                setMessage(it)
            })

    }

    fun getCommentsFromAPI(post_id: Int): LiveData<List<CommentsResponseItem>> {
        val commentsMutableData = MutableLiveData<List<CommentsResponseItem>>()
        getCommentsJob =
            Coroutines.ioThenMain({
                mainRepo.getComments(post_id)
            }, {
                commentsMutableData.value = it
            }, {
                setMessage(it)
            })
        return commentsMutableData
    }


    fun getPostsFromDB() {
        getAllPostDataFromDBJob = Coroutines.ioThenMain(
            {
                dbRepo.getAllPostDataFromPostEntity()
            }, {
                _allPostFromDB.value = it
            }, {
                setMessage(it)
            })
    }


    private fun addAllPostToDB(postEntityList: List<PostEntity>) {
        addPostDataToDBJob = Coroutines.ioThenMain({
            dbRepo.addALLPostDataToPostEntity(postEntityList)
        }, {
            Log.d(TAG, "addPostData_completed")
        }, {
            setMessage(it)
        })
    }
    @VisibleForTesting
    internal fun addPostDataToFavoriteEntityAndInformMiddleWare(favoriteEntity: FavoriteEntity) {
        if (!networkStatus.value!!) {
          //  scheduleWork()
        }
        addPostDataToFavoriteEntityJob = Coroutines.ioThenMain({
            dbRepo.addPostDataToFavoriteEntity(favoriteEntity)
        }, {
            setMessage(postAddedAlert)
        }, {
            setMessage(it)
        })
    }

    fun checkTheFavoritePostAlreadyExist(id: Int) {
        checkThePostAlreadyExistJob = Coroutines.ioThenMain(
            {
                dbRepo.checkTheFavoritePostAlreadyExist(id)
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
                dbRepo.getPostDataFromPostEntity(id)
            }, {
                if (it != null) {
                    addPostDataToFavoriteEntityAndInformMiddleWare(
                        FavoriteEntity(
                            id = it.id,
                            userId = it.userId,
                            body = it.body,
                            title = it.title
                        )
                    )
                }
            }, {
                setMessage(it)
            }
        )
    }

    fun getAllFavoriteDataFromFavoriteEntity() {
        getAllFavoriteDataFromFavoriteEntityJob = Coroutines.ioThenMain({
            dbRepo.getAllFavoriteDataFromFavoriteEntity()
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