package shellPoker.gameEngine.handEnding

import shellPoker.core.cards.Card
import shellPoker.core.pokerHands.{PokerHand, PokerHandFactory}
import shellPoker.gameEngine.player.Player
import shellPoker.gameEngine.table.{PokerTable, Pot}

/** Defines some helper methods for determining the winners of the hand
  * and the amount of chips that they should be given.
  */
private[handEnding] object PotDistributionHelper {


  /* Gets hand results for a single pot. */
  def getSinglePotResults(pot: Pot, communityCards: List[Card]): CompleteHandResults = {

    if (pot.entitledPlayers.length == 1) {

      val winner: Player = pot.entitledPlayers.head
      val results: CompleteHandResults = new CompleteHandResults(List(winner))
      results.addChipsToPlayerResults(winner, pot.size)
      return results
    }

    val bestHand: PokerHand = getBestHand(pot.entitledPlayers, communityCards)
    val winners = pot.entitledPlayers.filter(
      (player: Player) => getPlayerHand(player, communityCards).isEquallyStrongAs(bestHand))

    val win = pot.size / winners.length
    val results: CompleteHandResults = new CompleteHandResults(pot.entitledPlayers)
    for (winner <- winners)
      results.addChipsToPlayerResults(winner, win)

    results
  }


  /* Finds proper order of players showing cards during showdown according to showdown order rules. */
  def getShowdownOrder(table: PokerTable, lastAggressivePlayer: Player): List[Player] = {

    var result = lastAggressivePlayer :: Nil
    var nextToShow = table.getNextInGameSeat(lastAggressivePlayer.seat).player

    while(nextToShow != lastAggressivePlayer) {

      result ++= nextToShow :: Nil
      nextToShow = table.getNextInGameSeat(nextToShow.seat).player
    }

    result
  }



  // ===================================================================================================================
  // Methods related to certain players' hands strength:
  // ===================================================================================================================


  /* Finds the strongest hand at the table. */
  def getBestHand(players: List[Player], communityCards: List[Card]): PokerHand = {

    players.map(getPlayerHand(_, communityCards)).
      reduce((hand1: PokerHand, hand2: PokerHand) => PokerHandFactory.getBetterHand(hand1, hand2))
  }


  /* Gets the hand of given player. */
  def getPlayerHand(player: Player, communityCards: List[Card]): PokerHand = {

    val (c1, c2) = player.holeCards
    PokerHandFactory.pickBestHand(communityCards ++ List(c1, c2))
  }


  /* Checks if given player's hand is winning or splitting hand among other players' hands */
  def hasBestHand(player: Player, otherPlayers: List[Player], communityCards: List[Card]): Boolean = {

    val thisHand = getPlayerHand(player, communityCards)
    val otherBestHand = getBestHand(otherPlayers, communityCards)

    !thisHand.isWeakerThan(otherBestHand)
  }
}
