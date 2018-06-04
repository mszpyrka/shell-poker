package shellPoker.gameEngine

import shellPoker.core.cards.Card

class GameManager{
  var tableSeats: List[tableSeat] = _
  val dealer: Dealer = new Dealer
  val communityCards: Option[List[Card]] = None
  val pot: ChipStack = new ChipStack(0)
  val positionManager: PositionManager = new PositionManager
}