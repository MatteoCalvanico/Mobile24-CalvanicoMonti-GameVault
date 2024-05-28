package it.unibo.gamevault.ui.fragment

import android.os.Bundle
import it.unibo.gamevault.ui.model.Game

/**
 * Need to pass the game to the dialog
 */
class AddGameDialogFragmentFactory {
    companion object {
        fun newInstance(game: Game?): AddGameDialog {
            return AddGameDialog().apply {
                arguments = Bundle().apply {
                    putParcelable("game", game)
                }
            }
        }
    }
}
