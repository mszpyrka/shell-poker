package shellPoker.gameEngine

import shellPoker.core.cards.Card

class Player(var chipStack: ChipStack) {
    var holeCards: Option[(Card, Card)] = None
    var currentBet: ChipStack = new ChipStack(0)

    def takeAction(input: String): Action = new Action

    def leave = ???
}