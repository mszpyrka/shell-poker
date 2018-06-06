package shellPoker.gameEngine

import scala.util.Random

/** Responsible for keeping track of all special positions at the poker table during the game.
  *
  * @param table Poker table for which all position setting is performed.
  */
class PositionManager(val table: PokerTable) {

  private var _bigBlind: TableSeat = _
  private var _smallBlind: TableSeat = _
  private var _dealerButton: TableSeat = _


  def bigBlind: TableSeat = _bigBlind
  def smallBlind: TableSeat = _smallBlind
  def dealerButton: TableSeat = _dealerButton


  /* Randomly chooses the position of dealer button and adjusts blinds accordingly. */
  def pickRandomPositions(): Unit = {

    if (table.takenSeatsNumber <= 1)
      throw NotEnoughPlayersException()

    _dealerButton = Random.shuffle(table.getEmptySeats).head

    if (table.takenSeatsNumber == 2)
      _smallBlind = _dealerButton

    else
      _smallBlind = table.getNextTakenSeat(_dealerButton)

    _bigBlind = table.getNextTakenSeat(_smallBlind)
  }


  /* Properly changes all special positions according to 'Dead button' rule. */
  def movePositions(): Unit = {

    if (table.takenSeatsNumber <= 1)
      throw NotEnoughPlayersException()

    // Always moves BigBlind first.
    _bigBlind = table.getNextTakenSeat(_bigBlind)

    if (table.takenSeatsNumber == 2) {

      _dealerButton = table.getNextTakenSeat(_bigBlind)
      _smallBlind = _dealerButton
    }

    else {

      val smallBlindRange = table.getSeatsRange(_smallBlind, _bigBlind)
      val newSmallBlind = smallBlindRange.reverse.find(!_.isEmpty).orNull

      if(newSmallBlind == null)
        _smallBlind = table.getPreviousSeat(_bigBlind)

      else
        _smallBlind = newSmallBlind

      val dealerRange = table.getSeatsRange(_dealerButton, _smallBlind)
      val newDealer = dealerRange.reverse.find(!_.isEmpty).orNull

      if(newDealer == null)
        _dealerButton = table.getPreviousSeat(_smallBlind)

      else
        _dealerButton = newDealer
    }
  }
}