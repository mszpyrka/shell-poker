package shellPoker.gameEngine

import shellPoker.core.cards.{Card, CardDeck}

import scala.util.Random

/** Represents cards dealer.
  * Responsible for picking random cards from the deck and dealing hole cards,
  * as well as all streets during the game.
  */
class Dealer {

  private var deckIterator: Iterator[Card] = _

  /* Restores the deck. */
  def shuffleDeck(): Unit = {

    deckIterator = Random.shuffle(CardDeck.deck).iterator

  }

  /* Deals two cards representing single player's hole cards. */
  def dealHoleCards: (Card, Card) = (deckIterator.next(), deckIterator.next())

  /* Deals three cards representing flop. */
  def dealFlop: (Card, Card, Card) = (deckIterator.next(), deckIterator.next(), deckIterator.next())

  /* Deals one card representing turn. */
  def dealTurn: Card = deckIterator.next()

  /* Deals one card representing river. */
  def dealRiver: Card = deckIterator.next()

}
