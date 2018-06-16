package shellPoker.gameEngine

/** Represents a single chip stack.
  * Responsible for adding and removing chips from
  * player's chip stack.
  */
class ChipStack(initialChipCount: Int){

  private var _chipCount = initialChipCount

  /* Returns current chip count. */
  def chipCount: Int = _chipCount

  /* Adds given amount of chips to the chip stack. */
  def addChips(amount: Int): Unit = _chipCount += amount

  /* Removes given amount of chips from the chip stack. */
  def removeChips(amount: Int): Unit = {

    if(_chipCount - amount < 0) throw new NegativeChipCountException

    _chipCount -= amount
  }

  /* Removes all of the player's chips */
  def removeAllChips(): Unit = _chipCount = 0
}