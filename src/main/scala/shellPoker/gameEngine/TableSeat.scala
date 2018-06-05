package shellPoker.gameEngine

/**Represent a single poker table seat.
  *Responsilbe for managing Player's presence on the seat
  */
class TableSeat(val seatNumber: Int) {
  private var _player: Player = _

  def player: Player = _player
  
  /*Checks if the seat is empty*/
  def isEmpty: Boolean = _player == null
  
  /*Adds Player instance to the seat*/
  def addPlayer(player: Player): Unit = this._player = player

  /*Removes Players instance from the seat*/
  def removePlayer(): Unit = this._player = null
}