package shellPoker.gameEngine

/**Represent a single poker table seat.
  *Responsilbe for managing Player's presence on the seat
  */
class TableSeat{
  private var player: Player = _
  
  /*Checks if the seat is empty*/
  def isEmpty: Boolean = player == null
  
  /*Adds Player instance to the seat*/
  def addPlayer(player: Player): Unit = this.player = player

  /*Removes Players instance from the seat*/
  def removePlayer(): Unit = this.player = null
}