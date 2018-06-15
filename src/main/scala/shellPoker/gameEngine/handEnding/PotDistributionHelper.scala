package shellPoker.gameEngine.handEnding

import shellPoker.core.cards.Card
import shellPoker.core.pokerHands.{PokerHand, PokerHandFactory}
import shellPoker.gameEngine.{Player, PokerTable, Pot, TableSeat}

/** Defines some helper methods for determining the winners of the hand
  * and the amount of chips that they should be given.
  */
object PotDistributionHelper {


  /* Gets the list of all players from given list that are included in pot's entitled players,
   * who have the strongest hands (usually there will be only one winner of the pot). */
  def getPotWinners(players: List[Player], communityCards: List[Card], pot: Pot): List[Player] = {

    ???
  }

  /* Checks if given player wins any chips from pot (has the best or splitting hand). */
  def getSinglePotWin(player: Player, pot: Pot, otherPlayers: List[Player], communityCards: List[Card]): Int = {

    if (!pot.entitledPlayers.contains(player))
      return 0

    val otherPlayersInPot: Set[Player] = otherPlayers.toSet.intersect(pot.entitledPlayers.toSet)

    val otherPlayersHands: Set[PokerHand] = for(player <- otherPlayersInPot) yield {

      val (c1, c2) = player.holeCards
      val cards: List[Card] = List(c1, c2) ++ communityCards
      PokerHandFactory.pickBestHand(cards)
    }

    val (thisC1, thisC2) = player.holeCards
    val thisPlayerCards = List(thisC1, thisC2) ++ communityCards
    val thisPlayerHand = PokerHandFactory.pickBestHand(thisPlayerCards)

    if (otherPlayersHands.exists(_.isStrongerThan(thisPlayerHand)))
      return 0

    val bestHandsNumber = otherPlayersHands.count(_.isEquallyStrongAs(thisPlayerHand))

    pot.size / bestHandsNumber
  }


  /* Finds proper order of seats at the table according to Texas Hold'em showdown order rules. */
  def getShowdownOrder(table: PokerTable, startingSeat: TableSeat): List[TableSeat] = {

    val firstToShow = if(startingSeat.player.hasFolded) table.getNextInGameSeat(startingSeat) else startingSeat
    if(firstToShow == null)
      return Nil

    var result = firstToShow :: Nil
    var nextToShow = table.getNextInGameSeat(firstToShow)

    while(nextToShow != firstToShow) {

      result ++= nextToShow :: Nil
      nextToShow = table.getNextInGameSeat(nextToShow)
    }

    result
  }


  /* Finds the strongest hand at the table. */
  def getStrongestHand(players: List[Player], communityCards: List[Card]): PokerHand = {

    ???
  }
}
