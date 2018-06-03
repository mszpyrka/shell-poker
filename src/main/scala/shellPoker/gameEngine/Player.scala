package shellPoker.gameEngine

import shellPoker.core.cards.Card

class Player(private var chipStack: ChipStack) {
    private var holeCards: Option[(Card, Card)] = None
    private var currentBet: ChipStack = new ChipStack(0)

    def takeAction(input: List[String]): Action = {

        input(0) match{
            case "fold" => Fold
            case "allin" => AllIn
            case "check" => Check
            case "call" => Call
            case "bet" => Bet(input(1).toInt)
        }

    }

    def leave = ???
}