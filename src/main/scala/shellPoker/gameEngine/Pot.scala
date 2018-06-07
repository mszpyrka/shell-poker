package shellPoker.gameEngine

/** Represents the pot (chips that players are trying to win).
  * Responsible for storing chips amount present in the pot
  * as well as all players that are playing the pot.
  *
  * @param commitedPlayers Players that have committed to the pot (may contain players that have folded).
  */
class Pot(val commitedPlayers: List[Player]) {

  private var _size: Int = 0

  def size: Int = _size
  def addChips(amount: Int): Unit = _size += amount

  /* Retrieves all players that are entitled to get the pot in case of winning the hand. */
  def entitledPlayers: List[Player] = commitedPlayers.filter(!_.hasFolded)
}
