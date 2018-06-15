package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.PokerTable

object HandEndingHelper {

  /** Factory-style method for obtaining proper HandEndingHelper instance. */
  def getHelper(table: PokerTable, endingType: HandEndingType): HandEndingHelper = {

    endingType match {

      case ClassicShowdown => new ClassicShowdownEnding(table)
      case PlayersAllIn => new PlayersAllInEnding(table)
      case AllFoldedToBet => new AllFoldedToBetEnding(table)
    }
  }
}

/** Represents some type of single hand ending (when no further action from players id possible
  * and it is necessary to determine which cards should be showed and which players win what part of the pot).
  *
  * @param table Table at which the game is played.
  * @param endingType The type of hand ending.
  */
abstract class HandEndingHelper(table: PokerTable, endingType: HandEndingType) {

  def proceedWithFinalActions(): Unit
  def calculateHandResults(): CompleteHandResults
}
