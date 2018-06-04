package shellPoker.gameEngine

import scala.util.Random

private object PositionManager {

  /* Finds the first seat following given startingSeat in clockwise direction. */
  private def getNextSeat(startSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    val startIndex = seats.indexOf(startSeat)

    if (seats.drop(startIndex + 1) == Nil)
      seats.head
    else
      seats.drop(startIndex + 1).head
  }


  /* Same as getNextSeat but searches backwards. */
  private def getPreviousSeat(startSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    getNextSeat(startSeat, seats.reverse)
  }


  /* Searches for the first non-empty seat following some particular seat at the table. */
  private def getNextTakenSeat(startingSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    val startIndex = seats.indexOf(startingSeat)
    val targetSeat: Option[TableSeat] = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).find(!_.isEmpty)

    targetSeat.orNull
  }


  /* Counts non-empty seats in given list. */
  private def countTakenSeats(seats: List[TableSeat]) = seats.count(!_.isEmpty)


  /* Slices seats list to get all the seats between border points (left margin inclusive, right margin exclusive). */
  private def getSeatsRange(start: TableSeat, end: TableSeat, seats: List[TableSeat]): List[TableSeat] = {

    if (start == end)
      return seats

    val startIndex = seats.indexOf(start)

    val extendedSeats = seats.drop(startIndex) ++ seats

    val endIndex = extendedSeats.indexOf(end)

    extendedSeats.take(endIndex)
  }
}

/** Responsible for keeping track of all special positions at the poker table during the game. */
class PositionManager {
  private var _bigBlind: TableSeat = _
  private var _smallBlind: TableSeat = _
  private var _dealerButton: TableSeat = _

  def bigBlind: TableSeat = _bigBlind
  def smallBlind: TableSeat = _smallBlind
  def dealerButton: TableSeat = _dealerButton


  /* Randomly chooses the position of dealer button and adjusts blinds accordingly. */
  def pickRandomPositions(seats: List[TableSeat]): Unit = {

    val takenSeatsNumber = PositionManager.countTakenSeats(seats)

    if (takenSeatsNumber <= 1)
      throw NotEnoughPlayersException()

    _dealerButton = Random.shuffle(seats.filter(!_.isEmpty)).head

    if (takenSeatsNumber == 2)
      _smallBlind = _dealerButton

    else
      _smallBlind = PositionManager.getNextTakenSeat(_dealerButton, seats)

    _bigBlind = PositionManager.getNextTakenSeat(_smallBlind, seats)
  }

  /* Properly changes all special positions according to 'Dead button' rule. */
  def movePositions(seats: List[TableSeat]): Unit = {

    val takenSeatsNumber = PositionManager.countTakenSeats(seats)

    if (takenSeatsNumber <= 1)
      throw NotEnoughPlayersException()

    // Always moves BigBlind first.
    _bigBlind = PositionManager.getNextTakenSeat(_bigBlind, seats)

    if (takenSeatsNumber == 2) {

      _dealerButton = PositionManager.getNextTakenSeat(_bigBlind, seats)
      _smallBlind = _dealerButton
    }

    else {

      val smallBlindRange = PositionManager.getSeatsRange(_bigBlind, _smallBlind, seats.reverse)
      val newSmallBlind = PositionManager.getNextTakenSeat(_bigBlind, smallBlindRange)

      if(newSmallBlind == null)
        _smallBlind = PositionManager.getPreviousSeat(_bigBlind, seats)

      else
        _smallBlind = newSmallBlind

      val dealerRange = PositionManager.getSeatsRange(_smallBlind, _dealerButton, seats.reverse)
      val newDealer = PositionManager.getNextTakenSeat(_smallBlind, dealerRange)

      if(newDealer == null)
        _dealerButton = PositionManager.getPreviousSeat(_smallBlind, seats)

      else
        _dealerButton = newDealer
    }
  }


}