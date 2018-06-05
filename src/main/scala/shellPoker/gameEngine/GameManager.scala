package shellPoker.gameEngine

import shellPoker.core.cards.Card

/**Manages a whole game
  *Responsible for managing players, table seats dealer etc
  */
class GameManager(seatsAmount: Int){

  var communityCards: List[Card] = _

  val tableSeats: List[TableSeat] = (for(_ <- 0 until seatsAmount) yield new TableSeat).toList 
  val dealer: Dealer = new Dealer
  val pot: ChipStack = new ChipStack(0)
  val positionManager: PositionManager = new PositionManager
  
}