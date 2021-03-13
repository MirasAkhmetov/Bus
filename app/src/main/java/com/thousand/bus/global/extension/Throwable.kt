package com.thousand.bus.global.extension

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thousand.bus.R
import com.thousand.bus.global.system.ResourceManager
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

fun Throwable.getErrorMessage(): String{
    if (this is HttpException){
        val body = this.response()?.errorBody()
        return body?.string().toString()
    }
    return ""
}

fun Throwable.getObjectErrorMessage(): String{
    if (this is HttpException){
        val body = this.response()?.errorBody()
        val jsonObject = JSONObject(body?.string().toString())
        return jsonObject.getString("message")
    }
    return ""
}

fun Throwable.getObjectErrorMessage(resourceManager: ResourceManager): String{
    if (this is HttpException){
        val body = this.response()?.errorBody()
        val jsonObject = JSONObject(body?.string().toString())
        return try {
            val errors = jsonObject.getJSONArray("errors")
            if (errors.length() > 0) {
                errors.optJSONObject(0).let {
                    val msg = it.getString("message")
                    msg
                }
            }else
                ""
        }catch (e: JSONException){
            e.printStackTrace()
            ""
        }
    }
    return ""
}

class RetrofitError(
    @Expose
    @SerializedName("message")
    val message: String = ""
)