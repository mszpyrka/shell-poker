package shellPoker.gameEngine.gameplay.status

import shellPoker.core.cards.Card
import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding.{CompleteHandResults, SinglePlayerHandResults}

object EndingStatus{
  def apply(results: CompleteHandResults) = {
    var finalString: String = ""

    for (singleResults <- results.results if singleResults.chipsWon > 0) {

      var playerString = singleResults.player.name + " wins " + singleResults.chipsWon

      if (singleResults.player.showedCards) {
        playerString += " with " + singleResults.hand.getClass.getSimpleName + ": "
        singleResults.hand.cards.foreach((card: Card) => playerString += card.toString + " ")
      }

      finalString += playerString + "\n"
    }

    new EndingStatus((finalString))
  }
}



case class EndingStatus private(result: String)
