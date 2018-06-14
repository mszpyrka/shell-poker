package shellPoker.gameEngine

import shellPoker.core.cards.Card

/** Represents a poker table with fixed seats number.
  * Provides some useful methods of finding certain seats relative to another seat.
  *
  * @param seatsAmount Amount of seats to be created at the table.
  */
class PokerTable(val seatsAmount: Int){

  val positionManager: PositionManager = new PositionManager(this)
  val potManager: PotManager = new PotManager(this)
  val seats: List[TableSeat] = (for(number <- 0 until seatsAmount) yield new TableSeat(number)).toList
  private val dealer: Dealer = new Dealer
  private var _communityCards: List[Card] = _

  def dealerButton: TableSeat = positionManager.dealerButton
  def smallBlind: TableSeat = positionManager.smallBlind
  def bigBlind: TableSeat = positionManager.bigBlind


  /** Clears all cards from the table and resets dealer's state. */
  def resetCards(): Unit = {

    for(player <- players)
      player.resetHoleCards()

    _communityCards = Nil
    dealer.shuffleDeck()
  }


  /** Gives hole cards to every player at the table. */
  def dealAllHoleCards(): Unit = {

    for(player <- players) {
      val (c1, c2) = dealer.dealHoleCards()
      player.setHoleCards(c1, c2)
    }
  }


  def communityCards: List[Card] = _communityCards

  def dealFlop(): Unit = {

    val (c1, c2, c3) = dealer.dealFlop()
    _communityCards = c1 :: c2 :: c3 :: Nil
  }


  def dealTurn(): Unit = {

    _communityCards = _communityCards ++ List(dealer.dealTurn())
  }


  def dealRiver(): Unit = {

    _communityCards = _communityCards ++ List(dealer.dealRiver())
  }


  /* Gets a list of all players currently seating at the table. */
  def players: List[Player] = seats.filter(!_.isEmpty).map(_.player)


  /* Gets a list of all empty seats at the table. */
  def emptySeats: List[TableSeat] = seats.filter(_.isEmpty)


  /* Gets a list of all taken seats at the table. */
  def takenSeats: List[TableSeat] = seats.filter(!_.isEmpty)


  /* Searches for the first seat following some particular seat at the table. */
  def getNextSeat(startSeat: TableSeat): TableSeat = {

    val startIndex = seats.indexOf(startSeat)

    if (seats.drop(startIndex + 1) == Nil)
      return seats.head

    seats.drop(startIndex + 1).head
  }


  /* Same as getNextSeat but searches backwards. */
  def getPreviousSeat(startSeat: TableSeat): TableSeat = {

    val startIndex = seats.indexOf(startSeat)

    if (startIndex == 0)
      return seats.last

    seats.drop(startIndex - 1).head
  }


  /* Searches for the first non-empty seat following some particular seat at the table. */
  def getNextTakenSeat(startingSeat: TableSeat): TableSeat = {

    val startIndex = seats.indexOf(startingSeat)
    val targetSeat: Option[TableSeat] = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).find(!_.isEmpty)

    targetSeat.orNull
  }


  /* Searches for the first seat that contains active player following some particular seat at the table. */
  def getNextActiveSeat(startingSeat: TableSeat): TableSeat = {

    val startIndex = seats.indexOf(startingSeat)
    val potentialSeats = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).filter(!_.isEmpty)
    val targetSeat = potentialSeats.find(_.player.isActive)

    targetSeat.orNull
  }


  /* Counts non-empty seats in given list. */
  def takenSeatsNumber: Int = seats.count(!_.isEmpty)


  /* Counts active players present at the table. */
  def activePlayersNumber: Int = {
    val takenSeats = seats.filter(!_.isEmpty)
    takenSeats.count(_.player.isActive)
  }


  /* Slices seats list to get all the seats between border points (both margins exclusive). */
  def getSeatsRange(start: TableSeat, end: TableSeat): List[TableSeat] = {

    val extendedSeats: List[TableSeat] = seats ++ seats

    val leftTrimmed: List[TableSeat] = extendedSeats.drop(extendedSeats.indexOf(start) + 1)

    leftTrimmed.take(leftTrimmed.indexOf(end))
  }
}