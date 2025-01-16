package build.learning.jonathansniderlogintesting.data.api

import com.learningplaystore.jonathansnidervirginmoney.data.model.Users
import retrofit2.http.GET

interface APIClient {
    @GET(APIDetails.ENDPOINT_USERS)
    suspend fun getUsers(

    ): Users
}