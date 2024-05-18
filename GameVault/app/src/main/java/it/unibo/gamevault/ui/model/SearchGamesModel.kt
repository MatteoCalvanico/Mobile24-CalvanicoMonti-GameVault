package it.unibo.gamevault.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represent how the game in the search are saved
 */
@Parcelize
data class Game(
    val id: String,
    val slug: String?,
    val name: String?,
    val description: String?,
    val metacritic: Int?,
    val released: String?,
    val tba: Boolean?,
    val backgroundImage: String?,
    val platforms: List<Platform>?
) : Parcelable

@Parcelize
data class Platform(
    val platform: String?
) : Parcelable

