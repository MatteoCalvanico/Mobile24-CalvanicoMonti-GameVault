package it.unibo.gamevault.data

import java.util.Date

/**
 * Represent how the game in the vault are saved
 */

//The rating and the two dates are not mandatory
data class VaultGames (val imgLink : String, val gameName : String, val yourRating : Float?, val startDate : Date?, val endDate : Date?)
