package it.unibo.gamevault.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey //TODO: Search documentation

@Entity(tableName = "user")
data class User(
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