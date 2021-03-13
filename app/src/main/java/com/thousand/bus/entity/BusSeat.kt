package com.thousand.bus.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class BusSeat(
    var state: Int = STATE_EMPTY,
    var id: Int? = null,
    var price: Int? = null,
    var passengerId: Int? = null,
    var placeId: Int? = null,
    var upDownId: Int? = null,
    var typeUpDown: Int = 1,
    var freePlacesCount: Int? = null
) : Parcelable {

    companion object {

        const val STATE_FREE = 1
        const val STATE_NOT_FREE = 2
        const val STATE_IN_PROCESS = 3
        const val STATE_YOUR = 4
        const val STATE_CANCEL = 13

        const val STATE_DRIVER = 5
        const val STATE_EMPTY = 6
        const val STATE_OUT = 7
        const val STATE_PASS_TOP_LEFT = 8
        const val STATE_PASS_TOP_RIGHT = 9
        const val STATE_PASS_CENTER = 10
        const val STATE_PASS_BOTTOM_LEFT = 11
        const val STATE_PASS_BOTTOM_RIGHT = 12

        fun get50BusSeat(): List<BusSeat> = listOf(

            //1
            BusSeat(STATE_FREE, 1),
            BusSeat(STATE_FREE, 2),
            BusSeat(STATE_PASS_TOP_LEFT),
            BusSeat(STATE_PASS_TOP_RIGHT),
            BusSeat(STATE_FREE, 3),
            BusSeat(STATE_FREE, 4),

            //2
            BusSeat(STATE_FREE, 5),
            BusSeat(STATE_FREE, 6),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_OUT),


            //3
            BusSeat(STATE_FREE, 9),
            BusSeat(STATE_FREE, 10),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 7),
            BusSeat(STATE_FREE, 8),

            //4
            BusSeat(STATE_FREE, 13),
            BusSeat(STATE_FREE, 14),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 11),
            BusSeat(STATE_FREE, 12),

            //6
            BusSeat(STATE_FREE, 17),
            BusSeat(STATE_FREE, 18),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 15),
            BusSeat(STATE_FREE, 16),

            //7
            BusSeat(STATE_FREE, 21),
            BusSeat(STATE_FREE, 22),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 19),
            BusSeat(STATE_FREE, 20),

            //8
            BusSeat(STATE_FREE, 25),
            BusSeat(STATE_FREE, 26),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 23),
            BusSeat(STATE_FREE, 24),

            //9
            BusSeat(STATE_FREE, 29),
            BusSeat(STATE_FREE, 30),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_FREE, 27),
            BusSeat(STATE_FREE, 28),


            //10
            BusSeat(STATE_FREE, 31),
            BusSeat(STATE_FREE, 32),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_OUT),

            //11
            BusSeat(STATE_FREE, 35),
            BusSeat(STATE_FREE, 36),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_FREE, 33),
            BusSeat(STATE_FREE, 34),

            //12
            BusSeat(STATE_FREE, 39),
            BusSeat(STATE_FREE, 40),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_FREE, 37),
            BusSeat(STATE_FREE, 38),

            //13
            BusSeat(STATE_FREE, 43),
            BusSeat(STATE_FREE, 44),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_FREE, 41),
            BusSeat(STATE_FREE, 42),

            //14
            BusSeat(STATE_FREE, 47),
            BusSeat(STATE_FREE, 48),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_FREE, 45),
            BusSeat(STATE_FREE, 46),

            //15
            BusSeat(STATE_FREE, 49),
            BusSeat(STATE_FREE, 50),
            BusSeat(STATE_FREE, 51),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 52),
            BusSeat(STATE_FREE, 53)
        )

        fun get36BusSeat(): List<BusSeat> = listOf(

            //empty
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),

            //1
            BusSeat(STATE_FREE, 33, upDownId = 0, typeUpDown = 2),
            BusSeat(STATE_FREE, 34, upDownId = 0, typeUpDown = 2),
            BusSeat(STATE_PASS_TOP_LEFT),
            BusSeat(STATE_PASS_TOP_RIGHT),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_OUT),

            //2
            BusSeat(STATE_FREE, 35, upDownId = 0, typeUpDown = 1),
            BusSeat(STATE_FREE, 36, upDownId = 0, typeUpDown = 1),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),

            //3
            BusSeat(STATE_FREE, 1, upDownId = 1, typeUpDown = 1),
            BusSeat(STATE_FREE, 17, upDownId = 1, typeUpDown = 2),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 2, upDownId = 2, typeUpDown = 1),
            BusSeat(STATE_FREE, 18, upDownId = 2, typeUpDown = 2),

            //4
            BusSeat(STATE_FREE, 3, upDownId = 3, typeUpDown = 1),
            BusSeat(STATE_FREE, 19, upDownId = 3, typeUpDown = 2),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 4, upDownId = 4, typeUpDown = 1),
            BusSeat(STATE_FREE, 20, upDownId = 4, typeUpDown = 2),

            //5
            BusSeat(STATE_FREE, 5, upDownId = 5, typeUpDown = 1),
            BusSeat(STATE_FREE, 21, upDownId = 5, typeUpDown = 2),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 6, upDownId = 6, typeUpDown = 1),
            BusSeat(STATE_FREE, 22, upDownId = 6, typeUpDown = 2),

            //6
            BusSeat(STATE_FREE, 7, upDownId = 7, typeUpDown = 1),
            BusSeat(STATE_FREE, 23, upDownId = 7, typeUpDown = 2),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 8, upDownId = 8, typeUpDown = 1),
            BusSeat(STATE_FREE, 24, upDownId = 8, typeUpDown = 2),

            //7
            BusSeat(STATE_FREE, 9, upDownId = 9, typeUpDown = 1),
            BusSeat(STATE_FREE, 25, upDownId = 9, typeUpDown = 2),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 10, upDownId = 10, typeUpDown = 1),
            BusSeat(STATE_FREE, 26, upDownId = 10, typeUpDown = 2),

            //8
            BusSeat(STATE_FREE, 11, upDownId = 11, typeUpDown = 1),
            BusSeat(STATE_FREE, 27, upDownId = 11, typeUpDown = 2),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_FREE, 12, upDownId = 12, typeUpDown = 1),
            BusSeat(STATE_FREE, 28, upDownId = 12, typeUpDown = 2),

            //9
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 13, upDownId = 13, typeUpDown = 1),
            BusSeat(STATE_FREE, 14, upDownId = 14, typeUpDown = 1),
            BusSeat(STATE_FREE, 15, upDownId = 15, typeUpDown = 1),
            BusSeat(STATE_FREE, 16, upDownId = 16, typeUpDown = 1),
            BusSeat(STATE_EMPTY),

            //10
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 29, upDownId = 13, typeUpDown = 2),
            BusSeat(STATE_FREE, 30, upDownId = 14, typeUpDown = 2),
            BusSeat(STATE_FREE, 31, upDownId = 15, typeUpDown = 2),
            BusSeat(STATE_FREE, 32, upDownId = 16, typeUpDown = 2),
            BusSeat(STATE_EMPTY)
        )

        fun getAlphardSeat(): List<BusSeat> = listOf(


            //1
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_DRIVER),
            BusSeat(STATE_PASS_TOP_LEFT),
            BusSeat(STATE_FREE, 1),
            BusSeat(STATE_OUT),
            BusSeat(STATE_EMPTY),

            //empty
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            //2
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 2),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 3),
            BusSeat(STATE_OUT),
            BusSeat(STATE_EMPTY),

            //empty
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),

            //3
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 4),
            BusSeat(STATE_FREE, 5),
            BusSeat(STATE_FREE, 6),
            BusSeat(STATE_EMPTY)


        )

        fun getTaxiSeat(): List<BusSeat> = listOf(


            //1
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_DRIVER),
            BusSeat(STATE_PASS_TOP_LEFT),
            BusSeat(STATE_FREE, 1),
            BusSeat(STATE_OUT),
            BusSeat(STATE_EMPTY),


            //empty
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),

            //3
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 2),
            BusSeat(STATE_FREE, 3),
            BusSeat(STATE_FREE, 4),
            BusSeat(STATE_EMPTY)


        )

        fun getMinivanSeat(): List<BusSeat> = listOf(


            //1
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_DRIVER),
            BusSeat(STATE_PASS_TOP_LEFT),
            BusSeat(STATE_FREE, 1),
            BusSeat(STATE_OUT),
            BusSeat(STATE_EMPTY),


            //empty
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),

            //3
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 2),
            BusSeat(STATE_FREE, 3),
            BusSeat(STATE_FREE, 4),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),

            //empty
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_EMPTY),

            //5
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 5),
            BusSeat(STATE_FREE, 6),
            BusSeat(STATE_FREE, 7),
            BusSeat(STATE_EMPTY)

        )


        fun get26BusSeat(): List<BusSeat> = listOf(

            //1
            BusSeat(STATE_DRIVER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_PASS_TOP_LEFT),
            BusSeat(STATE_PASS_TOP_RIGHT),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_OUT),

            //2
            BusSeat(STATE_FREE, 1),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_FREE, 2),

            //3
            BusSeat(STATE_FREE, 3),
            BusSeat(STATE_FREE, 1),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 5),
            BusSeat(STATE_FREE, 6),

            //4
            BusSeat(STATE_FREE, 7),
            BusSeat(STATE_FREE, 8),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 9),
            BusSeat(STATE_FREE, 10),

            //5
            BusSeat(STATE_FREE, 11),
            BusSeat(STATE_FREE, 12),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_EMPTY),
            BusSeat(STATE_OUT),

            //6
            BusSeat(STATE_FREE, 13),
            BusSeat(STATE_FREE, 14),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_PASS_CENTER),
            BusSeat(STATE_FREE, 15),
            BusSeat(STATE_FREE, 16),

            //7
            BusSeat(STATE_FREE, 17),
            BusSeat(STATE_FREE, 18),
            BusSeat(STATE_PASS_BOTTOM_LEFT),
            BusSeat(STATE_PASS_BOTTOM_RIGHT),
            BusSeat(STATE_FREE, 19),
            BusSeat(STATE_FREE, 20),

            //8
            BusSeat(STATE_FREE, 21),
            BusSeat(STATE_FREE, 22),
            BusSeat(STATE_FREE, 23),
            BusSeat(STATE_FREE, 24),
            BusSeat(STATE_FREE, 25),
            BusSeat(STATE_FREE, 26)
        )
    }
}