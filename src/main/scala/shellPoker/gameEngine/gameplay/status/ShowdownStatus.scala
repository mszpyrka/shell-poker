package shellPoker.gameEngine.gameplay.status

import shellPoker.gameEngine.gameplay.GameState

object  ShowdownStatus {
  def apply(gameState: GameState) = {
    new ShowdownStatus("")
  }
}

class ShowdownStatus private (result: String)
