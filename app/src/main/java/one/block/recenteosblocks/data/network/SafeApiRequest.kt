package one.block.recenteosblocks.data.network

import one.block.recenteosblocks.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend() -> Response<T>) : T? {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()?.let {
                response.body()
            }
        } else {
            val error = response.errorBody()?.toString()
            val message = StringBuilder()

            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) { }
            }
            message.append("Error code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}
