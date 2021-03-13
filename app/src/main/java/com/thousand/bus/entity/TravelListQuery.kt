package com.thousand.bus.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.thousand.bus.global.utils.AppConstants

@Keep
data class TravelListQuery(
    var page: Int = 1,
    var limit: Int = AppConstants.PAGE_LIMIT,
    var fromCityId: Int? = null,
    var toCityId: Int? = null,
    var time: String? = null,
    var isBus: Int? = null ,
    var fromCity: String? = null,
    var toCity: String? = null,
    var fromStation: String? = null,
    var toStation: String? = null,
    var placeCount: Int = 1,
    var baggage: Int = 0,
    var tv: Int = 0,
    var conditioner: Int = 0,
    var filter : String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeInt(limit)
        writeValue(fromCityId)
        writeValue(toCityId)
        writeString(time)
        writeValue(isBus)
        writeString(fromCity)
        writeString(toCity)
        writeString(fromStation)
        writeString(toStation)
        writeInt(placeCount)
        writeInt(baggage)
        writeInt(tv)
        writeInt(conditioner)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TravelListQuery> =
            object : Parcelable.Creator<TravelListQuery> {
                override fun createFromParcel(source: Parcel): TravelListQuery =
                    TravelListQuery(source)

                override fun newArray(size: Int): Array<TravelListQuery?> = arrayOfNulls(size)
            }
    }
}