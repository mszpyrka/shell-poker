package shellPoker.gameEngine

import shellPoker.core.cards.Card

class Player(val chipStack: ChipStack) {
    var holeCards: (Card, Card) = _
    val currentBet: ChipStack = new ChipStack(0)
}