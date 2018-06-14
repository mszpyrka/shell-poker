package shellPoker.gameEngine

import shellPoker.core.cards.{Card, CardDeck}

import scala.util.Random

sealed trait DealerStatus
case object PreFlop extends DealerStatus
case object Flop extends DealerStatus
case object Turn extends DealerStatus
case object River extends DealerStatus

/** Represents cards dealer.
  * Responsible for picking random cards from the deck and dealing hole cards,
  * as well as all streets during the game.
  */
class Dealer {

  private var deckIterator: Iterator[Card] = _
  private var _status: DealerStatus = _

  /* Gets current community cards status. */
  def status: DealerStatus = _status

  /* Restores the deck. */
  def shuffleDeck(): Unit = {

    deckIterator = Random.shuffle(CardDeck.deck).iterator
    _status = PreFlop
  }

  /* Deals two cards representing single player's hole cards. */
  def dealHoleCards(): (Card, Card) = (deckIterator.next(), deckIterator.next()) ???

  /* Deals three cards representing flop. */
  def dealFlop(): (Card, Card, Card) = (deckIterator.next(), deckIterator.next(), deckIterator.next()) ???

  /* Deals one card representing turn. */
  def dealTurn(): Card = deckIterator.next() ???

  /* Deals one card representing river. */
  def dealRiver(): Card = deckIterator.next() ???

}
