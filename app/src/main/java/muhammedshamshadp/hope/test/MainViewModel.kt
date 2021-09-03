package muhammedshamshadp.hope.test

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import muhammedshamshadp.hope.test.data.model.UserData
import muhammedshamshadp.hope.test.repository.ListRepository
import muhammedshamshadp.hope.test.repository.utils.ResultOf


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val listRepo: ListRepository = ListRepository(getApplication())

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _userData = MutableLiveData<UserData>()

     val userData:LiveData<UserData> =_userData
/*    var userID = MutableLiveData<String>("")
    var userName = MutableLiveData<String>("")
    var userDob = MutableLiveData<String>("")
    var userEmail = MutableLiveData<String>("")
    var name = MutableLiveData<String>("")*/


init {
 getData()
}

     fun getData() {
        viewModelScope.launch(Dispatchers.Default) {
            listRepo.getUserDetails().collect{ result ->
                when (result) {
                    is ResultOf.Success -> {
                        _dataLoading.postValue(false)
                        _userData.postValue(result.value!!)
                    }
                    is ResultOf.Loading -> _dataLoading.postValue(true)
                    is ResultOf.Error -> {
                        _dataLoading.postValue(true)
                    }
                    else -> {

                    }
                }
            }
        }
    }


}




