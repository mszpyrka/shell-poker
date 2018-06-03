package shellPoker.gameEngine

import shellPoker.core.cards.Card

/*Represents a poker player
  *Responsible for storing player's game state
  */
class Player(val initialChipStack: ChipStack) {
  var holeCards: (Card, Card) = _
  val currentBet: ChipStack = new ChipStack(0)
}