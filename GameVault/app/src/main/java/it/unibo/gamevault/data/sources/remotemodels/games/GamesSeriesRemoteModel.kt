package it.unibo.gamevault.data.sources.remotemodels.games

import com.squareup.moshi.Json

/**
 * Represent how te API return the Games Series
 */
data class GamesSeriesRemoteModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val result: List<GamesRemoteModel>
)

data class GameInSeriesRemoteModel(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String?,
    val tba: Boolean,
    @Json(name = "background_image") val backgroundImage: String?,
    val rating: Float,
    @Json(name = "rating_top") val ratingTop: Int,
    val ratings: Map<String, Int>?,
    @Json(name = "ratings_count") val ratingsCount: Int,
    @Json(name = "reviews_text_count") val reviewsTextCount: String?,
    val added: Int,
    @Json(name = "added_by_status") val addedByStatus: Map<String, Int>?,
    val metacritic: Int?,
    val playtime: Int,
    @Json(name = "suggestions_count") val suggestionsCount: Int,
    val updated: String,
    @Json(name = "esrb_rating") val esrbRating: EsrbRating?,
    val platforms: List<PlatformDetailsRemoteModel>?
)