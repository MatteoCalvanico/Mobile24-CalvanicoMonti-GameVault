package it.unibo.gamevault.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "user",
    foreignKeys = [
        ForeignKey(
            entity = GameLocalModel::class,
            parentColumns = ["slug"],
            childColumns = ["favourite_one"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = GameLocalModel::class,
            parentColumns = ["slug"],
            childColumns = ["favourite_two"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = GameLocalModel::class,
            parentColumns = ["slug"],
            childColumns = ["favourite_three"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = GameLocalModel::class,
            parentColumns = ["slug"],
            childColumns = ["favourite_four"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class UserLocalModel(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "profile_image") val profileImage: String?,
    @ColumnInfo(name = "PSN_link") val PSNLink: String?,
    @ColumnInfo(name = "steam_link") val steamLink: String?,
    @ColumnInfo(name = "Xbox_link") val XboxLink: String?,
    @ColumnInfo(name = "API_key") val APIKey: String?,
    @ColumnInfo(name = "favourite_one") val favouriteOne: String?,
    @ColumnInfo(name = "favourite_two") val favouriteTwo: String?,
    @ColumnInfo(name = "favourite_three") val favouriteThree: String?,
    @ColumnInfo(name = "favourite_four") val favouriteFour: String?
)