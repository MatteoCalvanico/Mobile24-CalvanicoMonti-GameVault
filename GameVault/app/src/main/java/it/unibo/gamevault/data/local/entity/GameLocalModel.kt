package it.unibo.gamevault.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameLocalModel(
    @PrimaryKey val slug: String,
    @ColumnInfo(name = "img_link") val imgLink: String?,
    @ColumnInfo(name = "game_name") val gameName: String?,
    @ColumnInfo(name = "your_rating") val yourRating: Double?,
    @ColumnInfo(name = "start_date") val startDate: String?,
    @ColumnInfo(name = "end_date") val endDate: String?
)
