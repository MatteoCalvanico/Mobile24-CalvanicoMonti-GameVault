package it.unibo.gamevault.data.sources.remotemodels.games

import com.squareup.moshi.Json

/**
 * Represent how te API return the Games
*/
data class GamesRemoteModel (
    val id: Int,
    val slug: String,
    val name: String,
    @Json(name = "name_original") val nameOriginal: String,
    val description: String?,
    val metacritic: Int?,
    @Json(name = "metacritic_platforms") val metacriticPlatforms: List<MetacriticPlatform>?,
    val released: String?,
    val tba: Boolean,
    val updated: String,
    @Json(name = "background_image") val backgroundImage: String?,
    @Json(name = "background_image_additional") val backgroundImageAdditional: String?,
    val website: String?,
    val rating: Float,
    @Json(name = "rating_top") val ratingTop: Int,
    val ratings: Map<String, Int>?,
    val reactions: Map<String, Int>?,
    val added: Int,
    @Json(name = "added_by_status") val addedByStatus: Map<String, Int>?,
    val playtime: Int,
    @Json(name = "screenshots_count") val screenshotsCount: Int,
    @Json(name = "movies_count") val moviesCount: Int,
    @Json(name = "creators_count") val creatorsCount: Int,
    @Json(name = "achievements_count") val achievementsCount: Int,
    @Json(name = "parent_achievements_count") val parentAchievementsCount: String?,
    @Json(name = "reddit_url") val redditUrl: String?,
    @Json(name = "reddit_name") val redditName: String?,
    @Json(name = "reddit_description") val redditDescription: String?,
    @Json(name = "reddit_logo") val redditLogo: String?,
    @Json(name = "reddit_count") val redditCount: Int,
    @Json(name = "twitch_count") val twitchCount: String?,
    @Json(name = "youtube_count") val youtubeCount: String?,
    @Json(name = "reviews_text_count") val reviewsTextCount: String?,
    @Json(name = "ratings_count") val ratingsCount: Int,
    @Json(name = "suggestions_count") val suggestionsCount: Int,
    @Json(name = "alternative_names") val alternativeNames: List<String>?,
    @Json(name = "metacritic_url") val metacriticUrl: String?,
    @Json(name = "parents_count") val parentsCount: Int,
    @Json(name = "additions_count") val additionsCount: Int,
    @Json(name = "game_series_count") val gameSeriesCount: Int,
    @Json(name = "esrb_rating") val esrbRating: EsrbRating?,
    val platforms: List<PlatformDetailsRemoteModel>?
)

data class MetacriticPlatform(
    val metascore: Int,
    val url: String
)

data class EsrbRating(
    val id: Int,
    val slug: String,
    val name: String
)

data class PlatformDetailsRemoteModel(
    val platform: PlatformInfo,
    @Json(name = "released_at") val releasedAt: String?,
    val requirements: Requirements?
)

data class PlatformInfo(
    val id: Int,
    val slug: String,
    val name: String
)

data class Requirements(
    val minimum: String?,
    val recommended: String?
)