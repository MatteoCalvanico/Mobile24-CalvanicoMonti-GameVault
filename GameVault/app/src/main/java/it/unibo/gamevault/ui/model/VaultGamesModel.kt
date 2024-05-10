package it.unibo.gamevault.ui.model

import java.util.Date

/**
 * Represent how the game in the vault are saved
 */

//The rating and the two dates are not mandatory
data class VaultGamesModel (val imgLink : String, val gameName : String, val yourRating : Double?, val startDate : String?, val endDate : String?)
