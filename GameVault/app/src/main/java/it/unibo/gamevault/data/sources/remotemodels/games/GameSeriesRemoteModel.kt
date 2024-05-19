package it.unibo.gamevault.data.sources.remotemodels.games

import com.squareup.moshi.Json

data class GameSeriesRemoteModel (
    val count: Int,
    val next: String?,
    val previous: String?,
    val result: List<GamesInSeriesRemoteModel>?
)

/**
 * The game in the list returned by gameSeries are different
 */
data class GamesInSeriesRemoteModel(
    val id: Int,
    val slug: String?,
    val name: String?,
    val released: String?,
    val tba: Boolean?,
    @Json(name = "background_image") val backgroundImage: String?,
    val rating: Float,
    @Json(name = "rating_top") val ratingTop: Int,
    val ratings: List<Rating>?,
    @Json(name = "ratings_count") val ratingsCount: Int,
    @Json(name = "reviews_text_count") val reviewsTextCount: String?,
    val added: Int,
    @Json(name = "added_by_status") val addedByStatus: Map<String, Int>?,
    val metacritic: Int,
    val playtime: Int,
    @Json(name = "suggestions_count") val suggestionsCount: Int,
    val updater: String,
    @Json(name = "esrb_rating") val esrbRating: EsrbRating?,
    val platforms: List<PlatformDetailsRemoteModel>?
)