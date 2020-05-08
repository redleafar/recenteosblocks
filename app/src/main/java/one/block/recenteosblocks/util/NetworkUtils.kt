package one.block.recenteosblocks.util

import com.google.gson.JsonObject

fun getRequestBody(key: String, value: String) : JsonObject {
    val requestBody = JsonObject()
    requestBody.addProperty(key, value)
    return requestBody
}