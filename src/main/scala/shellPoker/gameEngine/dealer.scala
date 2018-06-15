package shellPoker.gameEngine

import shellPoker.core.cards.{Card, CardDeck}

import scala.util.Random

/** Indicators of current dealer's status. */
sealed trait DealerStatus
case object PreGame extends DealerStatus
case object PreFlop extends DealerStatus
case object Flop extends DealerStatus
case object Turn extends DealerStatus
case object River extends DealerStatus

/** Represents cards dealer.
  * Responsible for picking random cards from the deck and dealing hole cards,
  * as well as all streets during the game.
  *
  * @param table PokerTable at which the dealer is operating.
  */
class Dealer(table: PokerTable) {

  private var deckIterator: Iterator[Card] = _
  private var _status: DealerStatus = _


  /* Gets current dealer status. */
  def status: DealerStatus = _status


  /* Restores the deck, clears table's community cards and players' hole cards, sets the status to PreGame. */
  def clearAllCards(): Unit = {

    deckIterator = Random.shuffle(CardDeck.deck).iterator
    table.resetCommunityCards()
    table.players.foreach(_.resetHoleCards())
    _status = PreGame
  }


  /* Deals two cards to every player at the table and changes status to PreFlop. */
  def dealHoleCards(): Unit = {

    table.players.foreach(_.setHoleCards(deckIterator.next(), deckIterator.next()))
    _status = PreFlop
  }


  /* Deals three cards to the table representing flop. */
  def dealFlop(): Unit = {

    table.addFlop(deckIterator.next(), deckIterator.next(), deckIterator.next())
    _status = Flop
  }


  /* Deals one card to the table representing turn. */
  def dealTurn(): Unit = {

    table.addTurn(deckIterator.next())
    _status = Turn
  }


  /* Deals one card to the table representing river. */
  def dealRiver(): Unit = {

    table.addRiver(deckIterator.next())
    _status = River
  }
}
