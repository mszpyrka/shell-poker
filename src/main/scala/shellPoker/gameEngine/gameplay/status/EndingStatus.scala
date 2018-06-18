package shellPoker.gameEngine.gameplay.status

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding.{CompleteHandResults, SinglePlayerHandResults}

object EndingStatus{
  def apply(results: CompleteHandResults) = {
    var finalString: String = ""

    results.results.filter(_.chipsWon > 0).foreach( (singlePlayerResult: SinglePlayerHandResults) =>
      finalString += singlePlayerResult.player.name + " wins " + singlePlayerResult.chipsWon + "!!\n"
    )

    finalString

    new EndingStatus((finalString))
  }
}



case class EndingStatus private(result: String)
