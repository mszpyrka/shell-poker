package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.table.PokerTable

object HandEndingHelper {

  /** Factory-style method for obtaining proper HandEndingHelper instance. */
  def getHelper(gameState: GameState, endingType: HandEndingType): HandEndingHelper = {

    endingType match {

      case ClassicShowdown => new ClassicShowdownEnding(gameState)
      case PlayersAllIn => new PlayersAllInEnding(gameState)
      case AllFoldedToBet => new AllFoldedToBetEnding(gameState)
    }
  }
}

/** Represents some type of single hand ending (when no further action from players id possible
  * and it is necessary to determine which cards should be showed and which players win what part of the pot).
  *
  * @param gameState The state of the game when there is no further action possibility.
  * @param endingType The type of hand ending.
  */
abstract class HandEndingHelper(gameState: GameState, endingType: HandEndingType) {

  def proceedWithFinalActions(): Unit

  /* Chooses winner (or winners) for every pot on the table. */
  def calculateHandResults(): CompleteHandResults = {

    val table = gameState.table
    val results = new CompleteHandResults(table.players)

    for (pot <- table.potManager.pots) {

      val partialResults: CompleteHandResults = PotDistributionHelper.getSinglePotResults(pot, table.communityCards)
      for (singleResult <- partialResults.results)
        results.addChipsToPlayerResults(singleResult.player, singleResult.chipsWon)
    }

    results
  }
}
