package it.unibo.gamevault.data.sources.remotemodels.games

/**
 * Represent how te API return the Games Series
 */
data class GamesSeriesRemoteModel(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val result: List<GamesRemoteModel>
)