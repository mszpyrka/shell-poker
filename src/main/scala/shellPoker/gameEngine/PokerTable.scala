package shellPoker.gameEngine

import shellPoker.core.cards.Card

class PokerTable(seatsAmount: Int){
    val tableSeats = for(_ <- 0 until seatsAmount) yield new TableSeat
    val dealer: Dealer = new Dealer
    val communityCards: Option[List[Card]] = None
    val pot: ChipStack = new ChipStack(0)
    val positionManager: PositionManager = new PositionManager



}