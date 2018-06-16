package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.GameState
import shellPoker.gameEngine.player.Player

/** Represents the type of ending when at least two players did not fold on the river.
  * Final actions in this case contain determining which players show / muck their cards.
  *
  * @param gameState The state of the game when there is no further action possibility.
  */
class ClassicShowdownEnding(val gameState: GameState) extends HandEndingHelper(gameState, ClassicShowdown) {

  override def proceedWithFinalActions(): Unit = {

    val showdownOrderPlayers = PotDistributionHelper.getShowdownOrder(gameState.table, gameState.roundEndingPlayer)
    var showingPlayers = Nil

    for (player <- showdownOrderPlayers) {

      if(shouldShowCards(player, showingPlayers)) {

        player.showCards()
        showingPlayers ++= List(player)
      }

      else
        player.muckCards()
    }
  }

  //override def calculateHandResults(): CompleteHandResults = ???


  /* Checks if given player has any chance of winning any chips from the pot by showing his cards
     after some other players have already revealed theirs. */
  private def shouldShowCards(player: Player, showingPlayers: List[Player]): Boolean = {

    val table = gameState.table

    for (pot <- table.potManager.pots if pot.entitledPlayers.contains(player)) {

      // All potential winners of the pot
      val rivals: List[Player] = pot.entitledPlayers.toSet.intersect(showingPlayers.toSet).toList

      // If player has a chance of winning chips from any pot, then he should show cards.
      if (PotDistributionHelper.hasBestHand(player, rivals, table.communityCards))
        return true
    }

    false
  }
}
