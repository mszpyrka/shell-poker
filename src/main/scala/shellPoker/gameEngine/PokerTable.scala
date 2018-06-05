// package shellPoker.gameEngine

// class PokerTable(val seatsAmount: Int){
//   val seats: List[TableSeat] = (for(_ <- 0 until seatsAmount) yield new TableSeat).toList

//   val seatsFree: Int = seatsAmount
//   val seatsTaken: Int = 0
//   val seatsActive: Int = 0

//   private var currentSeat: TableSeat = seats.head
//   private var currentTakenSeat: TableSeat = _
//   private var currentActiveSeat: TableSeat = _


//   def getNextSeat(): TableSeat = {

//     var returnSeat: TableSeat = currentSeat

//     val currentIndex = seats.indexOf(currentSeat)

//     if (seats.drop(currentIndex + 1) == Nil){
//       reset
//     }
//     else
//       currentSeat = seats.drop(currentIndex + 1).head

//     returnSeat
//   }

//   def reset(): Unit = currentSeat = seats.head

//   /* Searches for the first seat following some particular seat at the table. */
//   def getNextRelativeSeat(seat: TableSeat): TableSeat = {
//     val startIndex = seats.indexOf(seat)

//     if (seats.drop(startIndex + 1) == Nil)
//       seats.head
//     else
//       seats.drop(startIndex + 1).head
//   }

//   /* Same as getNextSeat but searches backwards. */
//   def getPreviousRelativeSeat(seat: TableSeat): TableSeat = {
//     val startIndex = seats.indexOf(seat)

//     if (seats.drop(startIndex - 1) == Nil)
//       seats.last
//     else
//       seats(startIndex - 1)
//   }


  // /* Searches for the first non-empty seat following some particular seat at the table. */
  // def getNextTakenSeat(startingSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

  //   val startIndex = seats.indexOf(startingSeat)
  //   val targetSeat: Option[TableSeat] = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).find(!_.isEmpty)

  //   targetSeat.orNull
  // }


  // /* Searches for the first seat that contains active player following some particular seat at the table. */
  // def getNextActiveSeat(startingSeat: TableSeat, seats: List[TableSeat]): TableSeat = {

  //   val startIndex = seats.indexOf(startingSeat)
  //   val potentialSeats = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).filter(!_.isEmpty)
  //   val targetSeat = potentialSeats.find(_.player.isActive)

  //   targetSeat.orNull
  // }


  // /* Counts non-empty seats in given list. */
  // def countTakenSeats(seats: List[TableSeat]): Int = seats.count(!_.isEmpty)


  // /* Counts active players present at the table. */
  // def countActivePlayers(seats: List[TableSeat]): Int = {
  //   val takenSeats = seats.filter(!_.isEmpty)
  //   takenSeats.count(_.player.isActive)
  // }


  // /* Slices seats list to get all the seats between border points (left margin inclusive, right margin exclusive). */
  // def getSeatsRange(start: TableSeat, end: TableSeat, seats: List[TableSeat]): List[TableSeat] = {

  //   val extendedSeats: List[TableSeat] = seats ++ seats

  //   val leftTrimmed: List[TableSeat] = extendedSeats.drop(extendedSeats.indexOf(start) + 1)

  //   val rightTrimmed = leftTrimmed.take(leftTrimmed.indexOf(end))

  //   start :: rightTrimmed
  // }


}