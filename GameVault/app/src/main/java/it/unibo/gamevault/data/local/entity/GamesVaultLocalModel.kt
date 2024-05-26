package it.unibo.gamevault.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "games_vault",
    foreignKeys = [
        ForeignKey(
            entity = GameLocalModel::class,
            parentColumns = ["slug"],
            childColumns = ["game_name"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GamesVaultLocalModel(
    @PrimaryKey @ColumnInfo(name = "game_name") val gameName: String
)
