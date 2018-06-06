package shellPoker.gameEngine

/** Represents the pot (chips that players are trying to win).
  * Responsible for storing chips amount present in the pot
  * as well as all players that are playing the pot.
  */
class Pot {

  private var _players: List[Player] = Nil  // Players that committed to the pot (may contain players that have folded).
  private var _size: Int = 0                // Current pot size.

  def size: Int = _size
  def addChips(amount: Int): Unit = _size += amount

  /* Retrieves all players that are entitled to get the pot in case of winning the hand. */
  def entitledPlayers: List[Player] = _players.filter(!_.hasFolded)
}
