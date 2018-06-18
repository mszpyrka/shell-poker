package shellPoker.gameEngine.gameplay.status

import shellPoker.gameEngine.gameplay.GameState

object GameStatus {
  def apply(gameState: GameState) = {
    var result: String = ""

    result += "========================NEW HAND============================\n"
    result += s"Hand number: ${gameState.handNumber}\n"
    result += "============================================================\n"

    new GameStatus(result)
  }
}

class GameStatus private (result: String)
