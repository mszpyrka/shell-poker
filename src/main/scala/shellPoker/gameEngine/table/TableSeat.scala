package shellPoker.gameEngine.table

import shellPoker.gameEngine.player.{ChipStack, Player}

/** Represent a single poker table seat.
  * Responsible for managing Player's presence on the seat
  */
class TableSeat(val seatNumber: Int) {

  private var _player: Player = _

  def player: Player = _player
  
  /* Checks if the seat is empty. */
  def isEmpty: Boolean = _player == null
  
  /* Adds Player instance to the seat. */
  def addPlayer(player: Player): Unit = {

    if (!this.isEmpty)
      throw InvalidSeatException("Seat is already taken.")

    this._player = player
  }


  /* Creates new Player instance and adds it to the seat. */
  def createAndAddPlayer(name: String, initialChipCount: Int): Unit = {

    if (!this.isEmpty)
      throw InvalidSeatException("Seat is already taken.")

    val newChipStack = new ChipStack(initialChipCount)
    val newPlayer = new Player(name, this, newChipStack)

    this._player = newPlayer
  }


  /* Removes Players instance from the seat. */
  def removePlayer(): Unit = {

    if(this.isEmpty)
      throw InvalidSeatException("Seat is already empty.")

    this._player = null
  }
}