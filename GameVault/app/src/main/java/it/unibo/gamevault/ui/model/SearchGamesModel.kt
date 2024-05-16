package it.unibo.gamevault.ui.model


/**
 * Represent how the game from the API are returned
 */
data class Game(
    val id: Int?,
    val slug: String?,
    val name: String?,
    val nameOriginal: String?,
    val description: String?,
    val metacritic: Int?,
    val metacriticPlatforms: List<MetacriticPlatform>?,
    val released: String?,
    val tba: Boolean?,
    val updated: String?,
    val backgroundImage: String?,
    val backgroundImageAdditional: String?,
    val website: String?,
    val rating: Float?,
    val ratingTop: Int?,
    val ratings: Map<String, Int>?,
    val reactions: Map<String, Int>?,
    val added: Int?,
    val addedByStatus: Map<String, Int>?,
    val playtime: Int?,
    val screenshotsCount: Int?,
    val moviesCount: Int?,
    val creatorsCount: Int?,
    val achievementsCount: Int?,
    val parentAchievementsCount: String?,
    val redditUrl: String?,
    val redditName: String?,
    val redditDescription: String?,
    val redditLogo: String?,
    val redditCount: Int?,
    val twitchCount: String?,
    val youtubeCount: String?,
    val reviewsTextCount: String?,
    val ratingsCount: Int?,
    val suggestionsCount: Int?,
    val alternativeNames: List<String>?,
    val metacriticUrl: String?,
    val parentsCount: Int?,
    val additionsCount: Int?,
    val gameSeriesCount: Int?,
    val esrbRating: EsrbRating?,
    val platforms: List<Platform>?
)

data class MetacriticPlatform(
    val metascore: Int?,
    val url: String?
)

data class EsrbRating(
    val id: Int?,
    val slug: String?,
    val name: String?
)

data class Platform(
    val platform: PlatformInfo?,
    val releasedAt: String?,
    val requirements: Requirements?
)

data class PlatformInfo(
    val id: Int?,
    val slug: String?,
    val name: String?
)

data class Requirements(
    val minimum: String?,
    val recommended: String?
)

