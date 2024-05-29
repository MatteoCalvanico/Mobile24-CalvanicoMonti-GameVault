package it.unibo.gamevault.data.sources.remotemodels.games

import com.squareup.moshi.Json

data class GameSeriesRemoteModel (
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GamesInSeriesRemoteModel>
)

/**
 * The game in the list returned by gameSeries are different
 */
data class GamesInSeriesRemoteModel(
    val id: String,
    val slug: String?,
    val name: String?,
    val released: String?,
    val tba: Boolean?,
    @Json(name = "background_image") val backgroundImage: String?,
    val rating: Double?,
    @Json(name = "rating_top") val ratingTop: Int?,
    val ratings: List<Rating>?,
    @Json(name = "ratings_count") val ratingsCount: Int?,
    @Json(name = "reviews_text_count") val reviewsTextCount: String?,
    val added: Int?,
    @Json(name = "added_by_status") val addedByStatus: Map<String, Int>?,
    val metacritic: Int?,
    val playtime: Int?,
    @Json(name = "suggestions_count") val suggestionsCount: Int?,
    val updated: String?,
    @Json(name = "user_game") val userGame: Any?,
    @Json(name = "reviews_count") val reviewsCount: Int?,
    @Json(name = "saturated_color") val saturatedColor: String?,
    @Json(name = "dominant_color") val dominantColor: String?,
    val platforms: List<PlatformDetailsRemoteModelSeries>?,
    @Json(name = "parent_platforms") val parentPlatforms: List<ParentPlatform>?,
    val genres: List<Genre>?,
    val stores: List<Store>?,
    val clip: Any?,
    val tags: List<Tag>?,
    @Json(name = "esrb_rating") val esrbRating: EsrbRating?,
    @Json(name = "short_screenshots") val shortScreenshots: List<ShortScreenshot>?
)

data class PlatformDetailsRemoteModelSeries(
    val platform: PlatformInfoSeries,
    @Json(name = "released_at") val releasedAt: String?,
    @Json(name = "requirements_en") val requirementsEn: Requirements?,
    @Json(name = "requirements_ru") val requirementsRu: Requirements?
)

data class PlatformInfoSeries(
    val id: Int,
    val name: String,
    val slug: String,
    val image: String?,
    @Json(name = "year_end") val yearEnd: String?,
    @Json(name = "year_start") val yearStart: String?,
    @Json(name = "games_count") val gamesCount: String,
    @Json(name = "image_background") val imageBack: String
)

data class PlatformInfoSeriesTwo(
    val id: Int,
    val name: String,
    val slug: String
)

data class ParentPlatform(
    val platform: PlatformInfoSeriesTwo
)

data class Store(
    val id: Int,
    val store: StoreDetails
)

data class StoreDetails(
    val id: Int,
    val name: String,
    val slug: String,
    val domain: String?,
    @Json(name = "games_count") val gamesCount: Int,
    @Json(name = "image_background") val imageBackground: String
)

data class Genre(
    val id: Int,
    val name: String,
    val slug: String,
    @Json(name = "games_count") val gamesCount: Int,
    @Json(name = "image_background") val imageBackground: String
)

data class Tag(
    val id: Int,
    val name: String,
    val slug: String,
    val language: String,
    @Json(name = "games_count") val gamesCount: Int,
    @Json(name = "image_background") val imageBackground: String
)

data class ShortScreenshot(
    val id: Int,
    val image: String
)