package shellPoker.gameEngine.table

import shellPoker.core.cards.Card
import shellPoker.gameEngine.player.Player

/** Represents a poker table with fixed seats number.
  * Provides some useful methods of finding certain seats relative to another seat.
  *
  * @param seatsAmount Amount of seats to be created at the table.
  */
class PokerTable(val seatsAmount: Int){

  val positionManager: PositionManager = new PositionManager(this)
  val potManager: PotManager = new PotManager(this)
  val seats: List[TableSeat] = (for(number <- 0 until seatsAmount) yield new TableSeat(number)).toList
  val dealer: Dealer = new Dealer(this)
  private var _communityCards: List[Card] = _



  // ===================================================================================================================
  // Getters for all non-direct values that should be easily accessible:
  // ===================================================================================================================

  def dealerButton: TableSeat = positionManager.dealerButton
  def smallBlind: TableSeat = positionManager.smallBlind
  def bigBlind: TableSeat = positionManager.bigBlind
  def communityCards: List[Card] = _communityCards

  /* Gets a list of all players currently seating at the table. */
  def players: List[Player] = seats.filter(!_.isEmpty).map(_.player)

  /* Gets a list of all players that are still in the hand. */
  def playersInHand: List[Player] = players.filter(!_.hasFolded)

  /* Gets a list of all empty seats at the table. */
  def emptySeats: List[TableSeat] = seats.filter(_.isEmpty)

  /* Gets a list of all taken seats at the table. */
  def takenSeats: List[TableSeat] = seats.filter(!_.isEmpty)

  /* Counts non-empty seats in given list. */
  def takenSeatsNumber: Int = seats.count(!_.isEmpty)

  /* Counts players that have not folded yet. */
  def playersInGameNumber: Int = players.count(!_.hasFolded)

  /* Counts active players present at the table. */
  def activePlayersNumber: Int = {
    val takenSeats = seats.filter(!_.isEmpty)
    takenSeats.count(_.player.isActive)
  }



  // ===================================================================================================================
  // Methods related to cooperation with the dealer:
  // ===================================================================================================================

  /** Clears all cards at the table (community and hole cards). */
  def resetCards(): Unit = dealer.clearAllCards()

  /** Clears all cards from the table. */
  def resetCommunityCards(): Unit = _communityCards = Nil

  /** Adds the first three cards to community cards. */
  def addFlop(c1: Card, c2: Card, c3: Card): Unit = _communityCards = List(c1, c2, c3)

  /** Adds the fourth card to community cards. */
  def addTurn(turn: Card): Unit = _communityCards = _communityCards ++ List(turn)

  /** Adds the fifth card to community cards. */
  def addRiver(river: Card): Unit = _communityCards = _communityCards ++ List(river)



  // ===================================================================================================================
  // Helper methods for easy navigation around the table:
  // ===================================================================================================================


  /* Finds next player at the table (searches clockwise). */
  def getNextPlayer(startingPlayer: Player): Player =
    getNextTakenSeat(startingPlayer.seat).player


  /* Finds next player that can still take some actions. */
  def getNextActivePlayer(startingPlayer: Player): Player =
    getNextActiveSeat(startingPlayer.seat).player


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


  /* Searches for the first seat that contains non-folded player following some particular seat at the table. */
  def getNextInGameSeat(startingSeat: TableSeat): TableSeat = {

    val startIndex = seats.indexOf(startingSeat)
    val potentialSeats = (seats.drop(startIndex + 1) ++ seats.take(startIndex)).filter(!_.isEmpty)
    val targetSeat = potentialSeats.find(!_.player.hasFolded)

    targetSeat.orNull
  }


  /* Slices seats list to get all the seats between border points (both margins exclusive). */
  def getSeatsRange(start: TableSeat, end: TableSeat): List[TableSeat] = {

    val extendedSeats: List[TableSeat] = seats ++ seats

    val leftTrimmed: List[TableSeat] = extendedSeats.drop(extendedSeats.indexOf(start) + 1)

    leftTrimmed.take(leftTrimmed.indexOf(end))
  }
}