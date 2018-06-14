package shellPoker.gameEngine

import shellPoker.core.cards.Card
import shellPoker.core.pokerHands.{PokerHand, PokerHandFactory}

sealed trait ShowdownAction
case object Mock extends ShowdownAction
case object Show extends ShowdownAction

class ShowdownStatus(seat: TableSeat, showdownAction: ShowdownAction, chipsWon: Int)

/** Responsible for creating proper showdown order and calculating which players are obliged to show their hole cards.
  */
class ShowdownManager {

  /* Calculates the amount of chips won and correct showdown action for every non-empty seat at the table. */
  def getShowdownStatuses(table: PokerTable, lastAggressivePlayerSeat: TableSeat): List[ShowdownStatus] = {

    val orderedSeats: List[TableSeat] = getShowdownOrder(table, lastAggressivePlayerSeat)
    var showingSeats: List[TableSeat] = Nil

    for (seat <- orderedSeats) {

      if (shouldShowCards(
          seat.player,
          showingSeats.map(_.player),
          table.potManager.pots,
          table.communityCards))
        showingSeats ++= List(seat)
    }

    for (seat <- table.seats) yield {

      val player = seat.player
      val otherPlayers = showingSeats.map(_.player).filter(_ != player)
      val totalWin: Int = table.potManager.pots.
        map((pot: Pot) => getSinglePotWin(player, pot, otherPlayers, table.communityCards)).sum
      val status: ShowdownAction = if (showingSeats.contains(seat)) Show else Mock
      new ShowdownStatus(seat, status, totalWin)
    }
  }


  /* Determines if given player possibly benefits from showing the cards. */
  private def shouldShowCards(
      player: Player,
      showingPlayers: List[Player],
      pots: List[Pot],
      communityCards: List[Card]): Boolean = {

    if (pots.exists(getSinglePotWin(player, _, showingPlayers, communityCards) > 0))
      true

    else
      false
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
  private def getShowdownOrder(table: PokerTable, startingSeat: TableSeat): List[TableSeat] = {

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
}
