package muhammedshamshadp.hope.test.repository

import android.app.Application
import muhammedshamshadp.hope.test.repository.utils.ResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import muhammedshamshadp.hope.test.data.model.UserData
import muhammedshamshadp.hope.test.data.remote.APIClient

class ListRepository (c: Application) {
    private var mContext: Application = c

    suspend fun getUserDetails(): Flow<ResultOf<UserData?>> {
        return flow {
            try {
                var resp = APIClient.getApiService(mContext).getUserData()

                if (resp.isSuccessful && resp.code() == 200) {
                    val savedData = resp.body() as UserData
                    emit(ResultOf.Success(savedData))
                } else {
                    emit(ResultOf.Error(Exception(resp.message())))
                }
            }catch (e: Exception) {
                emit(ResultOf.Error(e))
            }
        }.flowOn(Dispatchers.IO)
            }


}