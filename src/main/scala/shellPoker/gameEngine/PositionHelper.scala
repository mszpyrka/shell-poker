package shellPoker.gameEngine


object PositionHelper{

  /* Searches for the first seat following some particular seat at the table. */
  def getNextSeat(startSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    val startIndex = seats.indexOf(startSeat)

    if (seats.drop(startIndex + 1) == Nil)
      seats.head
    else
      seats.drop(startIndex + 1).head
  }


  /* Same as getNextSeat but searches backwards. */
  def getPreviousSeat(startSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    getNextSeat(startSeat, seats.reverse)
  }


  /* Searches for the first non-empty seat following some particular seat at the table. */
  def getNextTakenSeat(startingSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    val startIndex = seats.indexOf(startingSeat)
    val targetSeat: Option[TableSeat] = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).find(!_.isEmpty)

    targetSeat.orNull
  }


  /* Searches for the first seat that contains active player following some particular seat at the table. */
  def getNextActiveSeat(startingSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

    val startIndex = seats.indexOf(startingSeat)
    val potentialSeats = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).filter(!_.isEmpty)
    val targetSeat = potentialSeats.find(_.player.isActive)

    targetSeat.orNull
  }


  /* Counts non-empty seats in given list. */
  def countTakenSeats(seats: List[TableSeat]): Int = seats.count(!_.isEmpty)


  /* Counts active players present at the table. */
  def countActivePlayers(seats: List[TableSeat]): Int = {
    val takenSeats = seats.filter(!_.isEmpty)
    takenSeats.count(_.player.isActive)
  }


  /* Slices seats list to get all the seats between border points (left margin inclusive, right margin exclusive). */
  def getSeatsRange(start: TableSeat, end: TableSeat, seats: List[TableSeat]): List[TableSeat] = {

    val extendedSeats: List[TableSeat] = seats ++ seats

    val leftTrimmed: List[TableSeat] = extendedSeats.drop(extendedSeats.indexOf(start) + 1)

    val rightTrimmed = leftTrimmed.take(leftTrimmed.indexOf(end))

    start :: rightTrimmed
  }
}