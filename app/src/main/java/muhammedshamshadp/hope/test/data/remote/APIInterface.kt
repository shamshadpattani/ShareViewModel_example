package muhammedshamshadp.hope.test.data.remote

import muhammedshamshadp.hope.test.data.model.UserData
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {

    @GET("/api")
    suspend fun getUserData(): Response<UserData>


    companion object {
        const val BASE_API_URL = "https://randomuser.me"
    }
}
