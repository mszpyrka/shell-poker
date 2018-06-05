package shellPoker.gameEngine

import scala.util.Random

/** Responsible for keeping track of all special positions at the poker table during the game. */
class PositionManager(val table: PokerTable) {
  private var _bigBlind: TableSeat = _
  private var _smallBlind: TableSeat = _
  private var _dealerButton: TableSeat = _

  def bigBlind: TableSeat = _bigBlind
  def smallBlind: TableSeat = _smallBlind
  def dealerButton: TableSeat = _dealerButton


  /* Randomly chooses the position of dealer button and adjusts blinds accordingly. */
  def pickRandomPositions(seats: List[TableSeat]): Unit = {

    if (table.takenSeatsNumber <= 1)
      throw NotEnoughPlayersException()

    _dealerButton = Random.shuffle(table.freeSeats).head

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

      val smallBlindRange = PositionHelper.getSeatsRange(_bigBlind, _smallBlind, seats.reverse)
      val newSmallBlind = PositionHelper.getNextTakenSeat(_bigBlind, smallBlindRange)

      if(newSmallBlind == null)
        _smallBlind = PositionHelper.getPreviousSeat(_bigBlind, seats)

      else
        _smallBlind = newSmallBlind

      val dealerRange = PositionHelper.getSeatsRange(_smallBlind, _dealerButton, seats.reverse)
      val newDealer = PositionHelper.getNextTakenSeat(_smallBlind, dealerRange)

      if(newDealer == null)
        _dealerButton = PositionHelper.getPreviousSeat(_smallBlind, seats)

      else
        _dealerButton = newDealer
    }
  }
}