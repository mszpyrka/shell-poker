package shellPoker.gameEngine

/** Represents a single chip stack.
  * Responsible for adding and removing chips from
  * player's chip stack
  */
class ChipStack(var chipCount: Int){

  /*Adds given amount of chips to the chip stack*/
  def addChips(amount: Int): Unit = chipCount += amount

  /*Removes given amount of chips from the chip stack*/
  def removeChips(amount: Int): Unit = {
    if(chipCount - amount < 0) throw new NegativeChipCountException

    chipCount -= amount
  }

  def removeAllChips(): Unit = chipCount = 0;
}