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
) : Parcelable {

    /**
     * Return the description without the format string symbol (like: /n or &quot;)
     */
    fun getDescriptionFormat(): String {
        return description
            ?.replace("&quot;", "")
            ?.replace("\n", " ")
            ?.replace("<p>", "")
            ?.replace("</p>", "")
            ?.replace("<br />", " ")
            ?.replace("&#39;s", "'s")
            ?.replace("<h3>", "")
            ?.replace("</h3>", "")
            ?.replace("<br/>", "")
            ?.replace("&amp", "")
            ?: "No description found"
    }
}

@Parcelize
data class Platform( val platform: String) : Parcelable {

    /**
     * Return all the platform in a String with a good format
     */
    fun getPlatformFormat(): String {
        return (platform.substringAfter("=") + ", ") ?: "No platform"
    }
}

