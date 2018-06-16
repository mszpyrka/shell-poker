package shellPoker.gameEngine

/** Represents the pot (chips that players are trying to win).
  * Responsible for storing chips amount present in the pot
  * as well as all players that are playing the pot.
  *
  * @param committedPlayers Players that have committed to the pot (may contain players that have folded).
  */
class Pot(val committedPlayers: List[Player]) {

  private var _size: Int = 0

  def size: Int = _size
  def addChips(amount: Int): Unit = {

    if (amount < 0)
      throw NegativeChipCountException("Cannot add negative chips amount to the pot.")

    _size += amount
  }

  /* Retrieves all players that are entitled to get the pot in case of winning the hand. */
  def entitledPlayers: List[Player] = committedPlayers.filter(!_.hasFolded)
}
