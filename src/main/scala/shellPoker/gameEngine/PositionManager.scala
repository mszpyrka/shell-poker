package shellPoker.gameEngine

import scala.util.Random

private object PositionManager {

  /* Searches for the first non-empty seat following some particular seat at the table. */
  private def getNextTakenSeat(startingSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    val startingIndex = seats.indexOf(startingSeat)
    val targetSeat: Option[TableSeat] = (seats.drop(startingIndex) ++ seats.take(startingIndex - 1)).find(!_.isEmpty)

    if (targetSeat.isDefined)
      targetSeat

    null
  }

  private def countTakenSeats(seats: List[TableSeat]) = seats.count(!_.isEmpty)
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

    _bigBlind = PositionManager.getNextTakenSeat(_bigBlind, seats)

    if (takenSeatsNumber == 2) {

      _dealerButton = PositionManager.getNextTakenSeat(_bigBlind, seats)
      _smallBlind = _dealerButton
    }

    else {

      ???
    }

    ???
  }


}