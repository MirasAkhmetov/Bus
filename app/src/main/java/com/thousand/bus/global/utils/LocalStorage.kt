package com.thousand.bus.global.utils

import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.thousand.bus.entity.User
import kotlin.math.min

object LocalStorage {

    const val PREF_NO_VAL = "no_val"

    private const val PREF_ACCESS_TOKEN = "access_token"
    private const val PREF_USER = "user"
    private const val PREF_TIME = "time"
    private const val PREF_IDS = "order_ids"
    const val PREF_LANGUAGE = "language"

    fun setAccessToken(accessToken: String?) = Prefs.putString(PREF_ACCESS_TOKEN, accessToken)

    fun getAccessToken(): String = Prefs.getString(PREF_ACCESS_TOKEN, PREF_NO_VAL)
    //fun getAccessToken(): String = "token"

    fun setUser(user: User?) = Prefs.putString(PREF_USER, Gson().toJson(user))

    fun getUser(): User? = Gson().fromJson(Prefs.getString(PREF_USER, null), User::class.java)

    fun setCurrentTime(minute: Int) = Prefs.putInt(PREF_TIME, minute)

    fun getCurrentTime() = Prefs.getInt(PREF_TIME, 0)

    fun setOrderIds(ids: String) = Prefs.putString(PREF_IDS, ids)

    fun getOrderIds(): String = Prefs.getString(PREF_IDS, PREF_NO_VAL)

    fun setLanguage(lang: String) = Prefs.putString(PREF_LANGUAGE, lang)

    fun getLanguage(): String = Prefs.getString(PREF_LANGUAGE, AppConstants.LANG_RU)

}